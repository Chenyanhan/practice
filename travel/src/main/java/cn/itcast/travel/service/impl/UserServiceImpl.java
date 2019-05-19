package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        
        //查询用户是否存在
        User u = userDao.FindByName(user.getUsername());
        //存在则直接返回，不存在则保存
        if (u!=null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);

        //保存之后开始发送邮件到指定邮箱
        String content = "<a href=http://localhost/travel/active?code="+user.getCode()+"'>"+user.getCode()+"点击激活</a>";

        System.out.println(content);

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean activa(String code) {
        User user = userDao.FindBycode(code);

        if (user!=null){
            userDao.updateStatus(user);
            return  true;
        }
        return false;
    }

    @Override
    public User login(String name, String pwd) {

        return userDao.findUser(name,pwd);
    }
}
