package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int currentpage, int cid, int pagesize,String rname) {


        //查询总数
        int totalcount = routeDao.getTotalcount(cid,rname);

        //计算总页数
        int totalPage = totalcount%pagesize == 0 ? totalcount/pagesize : totalcount/pagesize +1;

        //应该显示的记录数
        List<Route> byPage = routeDao.findByPage(cid, (currentpage - 1) * pagesize, pagesize,rname);

        int minPage = 0;
        int maxPage = totalPage;
        int showPage = 10;

        //总页码数小于显示页码数
        if (totalPage<showPage){
            minPage = 1;
            maxPage = totalPage;
        }
        else {
            if (currentpage<showPage/2){
                minPage = 1;
                maxPage = showPage;
            }

            else if (currentpage+showPage>totalPage){
                minPage = totalPage -showPage;
                maxPage = totalPage;
            }
            else {
                minPage = currentpage-5;
                maxPage  = currentpage+4;
            }
        }


        //封装数据
        PageBean<Route> pageBean = new PageBean<Route>();

        pageBean.setMinPage(minPage);           //最小页码
        pageBean.setMaxPage(maxPage);           //最大页码
        pageBean.setCurrentPage(currentpage);   //当前页
        pageBean.setPageSize(pagesize);         //每页显示条数
        pageBean.setPageList(byPage);           //封装了所查询的所有数据
        pageBean.setTotalPage(totalPage);       //总页数
        pageBean.setTotalCount(totalcount);     //总记录数

        return pageBean;
    }

    @Override
    public Route detaiInfo(int rid) {
        //根据id查询route的详情信息
        Route route = routeDao.detailInfo(rid);

        //根据route的id 查询图片信息列表
        List<RouteImg> routeImgs = routeDao.imgList(rid);

        //获得商家id，用id去查询对应的商家
        Seller seller = routeDao.businessInfo(route.getSid());
        //查询收藏次数
        int count = routeDao.queryCount(rid);
        route.setCount(count);
        route.setRouteImgList(routeImgs);
        route.setSeller(seller);
        return route;
    }

    @Override
    public List<Route> ranking(int id) {
        return routeDao.queryTop(id);
    }





}
