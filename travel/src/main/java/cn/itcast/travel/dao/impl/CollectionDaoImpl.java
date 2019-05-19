package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CollectionDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class CollectionDaoImpl implements CollectionDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite isCollect(int uid, int rid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where uid=? and rid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        }
        catch (Exception e){};
        return favorite;
    }

    @Override
    public void addCollect(int uid, int rid) {
        String sql = "insert into tab_favorite values (?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
