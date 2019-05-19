package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User FindByName(String name);

    void save(User user);

    User FindBycode(String code);

    void updateStatus(User user);

    User findUser(String name,String pwd);
}
