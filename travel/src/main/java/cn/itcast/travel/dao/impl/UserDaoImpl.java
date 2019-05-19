package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {


    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    public User FindByName(String name) {
        User user = null;

        String sql = "select * from tab_user where username = ?";

        //如果数据不存在则封装失败报错
        try {
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),name);
        }
        catch (Exception e){};

        return user;

    }

    @Override
    public void save(User user) {

        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
                            );

    }

    @Override
    public User FindBycode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class), code);
        }
        catch (Exception e){};
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status='Y' where uid=?";
        template.update(sql,user.getUid());
    }

    @Override
    public User findUser(String name, String pwd) {
        User user = null;
        String sql = "select * from tab_user where username=? and password=?";
        try {
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),name,pwd);
        }
        catch (Exception e){}
        return user;
    }


}
