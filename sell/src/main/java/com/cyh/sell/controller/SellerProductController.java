package com.cyh.sell.controller;

import com.cyh.sell.dataobject.ProductCategory;
import com.cyh.sell.dataobject.ProductInfo;
import com.cyh.sell.enums.ProductStatusEnums;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.form.ProductForm;
import com.cyh.sell.service.CategoryService;
import com.cyh.sell.service.ProductService;

import com.cyh.sell.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * Created by 廖师兄
 * 2017-07-23 15:12
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }
    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("/product/index" , map);
    }

    /**
     *
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        //如果有错误则返回
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();

        //id为空则说明为新增，id需要赋值
        if (!StringUtils.isEmpty(productForm.getProductId())){
            //如果id为错误id则抛出异常
            try {
                productInfo = productService.findOne(productForm.getProductId());
            }
            catch (RuntimeException e){
                map.put("msg" , ResultEnums.PRODUCT_NOT_EXIST.getMessage());
                map.put("url" , "/seller/category/index");
                return new ModelAndView("/common/error" , map);
            }
        }
        else {
            productForm.setProductId(KeyUtils.gen());
        }
        BeanUtils.copyProperties(productForm,productInfo);
        try{
            productService.save(productInfo);
        }
        catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("msg" , ResultEnums.SUCCESS.getMessage());
        map.put("url" , "/seller/product/list");
        map.put("productInfoPage" , productInfo);

        return new ModelAndView("common/success" , map);


    }

//    @PostMapping("/save")
//    public ModelAndView save(@Valid ProductForm form,
//                             BindingResult bindingResult,
//                             Map<String, Object> map) {
//        if (bindingResult.hasErrors()) {
//            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
//            map.put("url", "/sell/seller/product/index");
//            return new ModelAndView("common/error", map);
//        }
//
//        ProductInfo productInfo = new ProductInfo();
//        try {
//            //如果productId为空, 说明是新增
//            if (!StringUtils.isEmpty(form.getProductId())) {
//                productInfo = productService.findOne(form.getProductId());
//            } else {
//                form.setProductId(KeyUtil.genUniqueKey());
//            }
//            BeanUtils.copyProperties(form, productInfo);
//            productService.save(productInfo);
//        } catch (SellException e) {
//            map.put("msg", e.getMessage());
//            map.put("url", "/sell/seller/product/index");
//            return new ModelAndView("common/error", map);
//        }
//
//        map.put("url", "/sell/seller/product/list");
//        return new ModelAndView("common/success", map);
//    }
}
