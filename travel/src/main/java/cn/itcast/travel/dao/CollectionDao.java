package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface CollectionDao {
    Favorite isCollect(int uid,int rid);
    void addCollect(int uid,int rid);
}
