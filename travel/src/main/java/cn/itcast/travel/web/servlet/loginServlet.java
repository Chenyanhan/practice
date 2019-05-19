package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用于返回给前端的数据对象
        ResultInfo resultInfo = new ResultInfo();
        //验证码校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if (check==null || !check.equalsIgnoreCase(checkcode_server)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            response.setContentType("application/json;charset=utf-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            response.getWriter().write(json);
            return;
        }
        //获取数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //调用service
        UserServiceImpl userService = new UserServiceImpl();

        User user = userService.login(username,password);


        //验证成功则将姓名存入ssesion中，用于显示用户名
        if (user!=null && user.getStatus().equals("Y")){
                request.getSession().setAttribute("user",user);
                resultInfo.setFlag(true);
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(resultInfo);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(json);
        }
        if (user !=null && !"Y".equals(user.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您尚未激活，请激活");
        }
        if (user == null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
