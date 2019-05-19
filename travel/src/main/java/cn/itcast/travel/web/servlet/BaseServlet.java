package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //请求会被子类Servlet接收，执行父类的Service方法，子类Servlet调用父类方法所以this指的是子类对象
        //获得请求路径
        String requestURI = req.getRequestURI();

        //获得方法名
        String methodname = requestURI.substring(requestURI.lastIndexOf("/") + 1);


        //执行方法
        try {
            Method method = this.getClass().getMethod(methodname, HttpServletRequest.class, HttpServletResponse.class);

            method.invoke(this,req,resp);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String serialization(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }



}
