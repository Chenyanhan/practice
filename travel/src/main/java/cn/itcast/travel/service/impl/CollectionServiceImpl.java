package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CollectionDao;
import cn.itcast.travel.dao.impl.CollectionDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.CollectionService;

import javax.servlet.annotation.WebServlet;

public class CollectionServiceImpl implements CollectionService {
    CollectionDao collectionDao = new CollectionDaoImpl();
    @Override
    public boolean isCollectionService(int rid, int uid) {
        Favorite collect = collectionDao.isCollect(uid, rid);
        if (collect!=null){
            return true;
        }
        return false;
    }

    @Override
    public void addCollect(int rid, int uid) {
        collectionDao.addCollect(uid,rid);
    }
}
