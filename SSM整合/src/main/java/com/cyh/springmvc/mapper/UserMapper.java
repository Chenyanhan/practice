package com.cyh.springmvc.mapper;

import com.cyh.springmvc.entity.User;
import org.springframework.stereotype.Repository;


public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(int id);
}