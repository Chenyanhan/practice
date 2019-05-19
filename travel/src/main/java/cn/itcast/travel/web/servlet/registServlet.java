package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Map;

@WebServlet("/registServlet")
public class registServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

            if (check==null || !check.equalsIgnoreCase(checkcode_server)){
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("验证码错误");
                response.setContentType("application/json;charset=utf-8");

                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(resultInfo);

                response.getWriter().write(json);

                return;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //创建Service
        UserService userService = new UserServiceImpl();
        boolean flag =  userService.regist(user);

        //创建Info对象封装查询后所需要返回的数据
        ResultInfo resultInfo = new ResultInfo();

        //如果flag为true说明注册成功,将标记设置为true，返给前端，前端根据标记真假做相应的操作
        if (flag){
            resultInfo.setFlag(true);

        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名已存在，注册失败");
        }

        //将resultInfo对象封装成json对象返回给前端
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
