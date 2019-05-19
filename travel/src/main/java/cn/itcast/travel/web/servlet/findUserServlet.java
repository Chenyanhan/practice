package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class findUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从ssesion中获得用户名
        User user = (User) request.getSession().getAttribute("user");
        String username = null;
        if (user!=null){
            username = user.getUsername();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        //将对象转为json对象
        String json = objectMapper.writeValueAsString(username);

        response.setContentType("application/json;charset=utf-8");
        //将json对象返回给前端

        response.getWriter().write(json);
    }
}
