package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    //根据类别id查询总记录数

    public int getTotalcount(int cid,String ranme);


    public List<Route> findByPage(int id,int subindex,int pagesize,String ranme);

    public Route detailInfo(int rid);

    public List<RouteImg> imgList(int rid);

    public Seller businessInfo(int sid);

    public int queryCount(int rid);

    public List<Route> queryTop(int id);

}
