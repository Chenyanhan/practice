package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.OrderDetail;
import com.cyh.sell.dataobject.OrderMaster;
import com.cyh.sell.dataobject.ProductInfo;
import com.cyh.sell.dto.CartDTO;
import com.cyh.sell.dto.OrderDTO;
import com.cyh.sell.enums.OrderStatusEnums;
import com.cyh.sell.enums.PayStatusEnums;
import com.cyh.sell.enums.ProductStatusEnums;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.repository.OrderDetailRepository;
import com.cyh.sell.repository.OrderMaterRepository;
import com.cyh.sell.repository.ProductInfoRepository;
import com.cyh.sell.service.OrderService;
import com.cyh.sell.utils.RandomIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDTO orderDTO;
    @Resource
    private ProductServiceImpl productService;

    @Resource
    private OrderDetailRepository orderDetailRepository;

    @Resource
    private OrderMaterRepository orderMaterRepository;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = RandomIdUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);

        List<CartDTO> cartDTOList = new ArrayList<>();
        //1. 查询商品（数量，价格）
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails();

        //2. 计算订单总价
        for (OrderDetail orderDetail:orderDetails
             ) {
            ProductInfo productinfo;
            try {
                productinfo = productService.findOne(orderDetail.getProductId());

            }
            catch (RuntimeException e){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }

            orderAmount = productinfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            //设置订单详情id
            orderDetail.setDetailId(RandomIdUtil.getUniqueKey());
            //设置订单id
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productinfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());

            cartDTOList.add(cartDTO);

        }

        //3.写入数据库
        orderDTO.setOrderId(orderId);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        //设置订单状态
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        //设置支付状态
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMaster.setOrderAmount(orderAmount);

        orderMaterRepository.save(orderMaster);
        //4.扣库存

        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = null;
        try{
            Optional<OrderMaster> byId = orderMaterRepository.findById(orderId);

            orderMaster = byId.get();

        }
        catch (Exception e){
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        //判断集合是否为空
        if (CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultEnums.ORDERDETAIL_NOT_EXIST);
        }

        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetails(orderDetails);

        return orderDTO;

    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> byBuyerOpenid = orderMaterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMaster> content = byBuyerOpenid.getContent();
        List<OrderDTO> list = new ArrayList<>();
        for (OrderMaster orderMaster:content
             ) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster,orderDTO);
            list.add(orderDTO);
        }
        Page<OrderDTO> page = new PageImpl<OrderDTO>(list);
        return page;
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(OrderDTO orderDTO) {
        //判断订单状态

        //只有新下单的状态才能取消
        if (orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            try{
                Optional<OrderMaster> byId = orderMaterRepository.findById(orderDTO.getOrderId());
                OrderMaster orderMaster = byId.get();

                //修改订单状态
                orderMaster.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
                try {
                    OrderMaster save = orderMaterRepository.save(orderMaster);
                }
                catch (Exception e){
                    throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
                }


                //返回库存
                List<CartDTO> cartDTOList = new ArrayList<>();

                List<OrderDetail> byOrderId = orderDetailRepository.findByOrderId(orderMaster.getOrderId());

                for (OrderDetail orderDetail:byOrderId
                     ) {
                    CartDTO cartDTO = new CartDTO();
                    cartDTO.setProductId(orderDetail.getProductId());
                    cartDTO.setProductQuantity(orderDetail.getProductQuantity());

                    cartDTOList.add(cartDTO);
                }
                
                productService.increaseStock(cartDTOList);

                return orderDTO;

            }
            catch (Exception e){

                throw new SellException(ResultEnums.ORDER_NOT_EXIST);
            }
        }

        //退还金额
        if (orderDTO.getOrderStatus().equals(PayStatusEnums.SUCCESS.getCode())){
                //TODO
        }
        return null;
    }

    @Override
    public OrderDTO finished(OrderDTO orderDTO) {
        //判断订单状态
        if(orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            //修改订单状态
            OrderMaster orderMaster = new OrderMaster();
            orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());

            BeanUtils.copyProperties(orderDTO,orderMaster);

            try {
                System.out.println(
                        orderMaster
                );
                OrderMaster save = orderMaterRepository.save(orderMaster);
            }
            catch (Exception e){
                throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
            }
            return orderDTO;

        }

        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态 如果订单是完结状态则不能取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            throw new SellException(ResultEnums.ORDER_STATUS_ERRO);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            throw new SellException(ResultEnums.ORDER_PAY_FAIL);
            }
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        try{
            OrderMaster save = orderMaterRepository.save(orderMaster);
        }
        catch (Exception e){
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }


    @Override
    public Page<OrderDTO> findList(Pageable pageable) {

        Page<OrderMaster> all = orderMaterRepository.findAll(pageable);
        List<OrderMaster> content = all.getContent();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (OrderMaster orderMaster: content
             ) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTOS.add(orderDTO);
        }

        return new PageImpl<OrderDTO>(orderDTOS,pageable,all.getTotalElements());
    }
}
