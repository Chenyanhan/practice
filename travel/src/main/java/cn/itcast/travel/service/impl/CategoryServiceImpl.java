package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();

        //查询jedis数据库是否有数据
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);


        List<Category> list=null;
        //判断
        if (category==null || category.size()==0){
            list = categoryDao.findAll();
            //将数据存入jedis
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }

        }
        else {
            //Arraylist是有序的

            list = new ArrayList<>();
            for (Tuple item:category
                 ) {
                Category c = new Category();
                c.setCname(item.getElement());
                c.setCid((int) item.getScore());
                list.add(c);
            }
        }
        return list;

    }
}
