package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class activeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code!=null){
            UserServiceImpl userService = new UserServiceImpl();

            boolean flag = userService.activa(code);

            String msg = null;
            if (flag){
                //激活成功
                response.sendRedirect("login.html");
            }
            else {
                //激活失败
                response.sendRedirect("register.html");
            }

            response.setContentType("text/html;charset =utf-8");
            if (msg!=null){
                response.getWriter().write(msg);
            }

        }
    }
}
