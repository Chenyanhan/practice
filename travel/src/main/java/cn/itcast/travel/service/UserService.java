package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    boolean regist(User user);

    boolean activa(String code);

    User login(String name,String pwd);
}
