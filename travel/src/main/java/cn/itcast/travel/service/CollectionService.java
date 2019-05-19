package cn.itcast.travel.service;

public interface CollectionService {
    boolean isCollectionService(int rid,int uid);

    void addCollect(int rid,int uid);
}
