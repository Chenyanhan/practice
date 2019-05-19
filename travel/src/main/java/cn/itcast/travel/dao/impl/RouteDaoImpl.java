package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int getTotalcount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid=?";
        String sql = "select count(*) from tab_route where 1=1 ";

        StringBuilder sb = new StringBuilder(sql);

        List parameter_list = new ArrayList();
        if (cid!=0){
            sb.append(" and cid=? ");
            parameter_list.add(cid);
        }
        if (rname!=null && rname.length()>0 && !rname.equals("null")){
            sb.append(" and rname like ? ");
            parameter_list.add("%"+rname+"%");
        }

        sql = sb.toString();

        return jdbcTemplate.queryForObject(sql, Integer.class,parameter_list.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int subindex, int pagesize,String rname) {

        //String sql = "select * from tab_route where cid=? limit  ? , ?";
        String sql = " select * from tab_route where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);

        List parameter_list = new ArrayList();

        if (cid!=0){
            sb.append( " and cid = ? ");
            parameter_list.add(cid);
        }
        if (rname!=null && rname.length()>0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            parameter_list.add("%"+rname+"%");
        }

        sb.append(" limit ? , ? ");
        sql = sb.toString();

        parameter_list.add(subindex);
        parameter_list.add(pagesize);


        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),parameter_list.toArray());

    }

    @Override
    public Route detailInfo(int rid) {
        String sql ="select * from tab_route where rid = ?";

        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);

    }

    @Override
    public List<RouteImg> imgList(int rid) {
        RouteImg routeImg = new RouteImg();
        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);

    }

    @Override
    public Seller businessInfo(int sid) {
        String sql=null;
        try {
            Seller seller = new Seller();
            sql = "select * from tab_seller where sid= ?";
        }
        catch (Exception e){}
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),sid);
    }

    @Override
    public int queryCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";


        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public List<Route> queryTop(int id) {
        List<Route> query=null;
        String sql = "select * from tab_route where cid=? limit 1,5";
        try{
            query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), id);
        }
        catch (Exception e){}

        return query;
    }


}
