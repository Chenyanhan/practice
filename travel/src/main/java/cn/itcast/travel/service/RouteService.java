package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    public PageBean<Route> pageQuery(int currentpage,int cid,int pagesize,String rname);

    public Route detaiInfo(int rid);

    public List<Route> ranking(int id);
}
