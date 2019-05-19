package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.CollectionService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.CollectionServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private CollectionService service = new CollectionServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //分类id
        String category_id = request.getParameter("cid");

        //当前页
        String currentPage = request.getParameter("currentPage");

        //显示条数
        String pageSize = request.getParameter("pageSize");

        String rname = request.getParameter("rname");

        //转化类型  String--->int
        int c_id = 0;
        if (category_id!=null && category_id.length()>0 && !category_id.equals("null")){
            c_id = Integer.parseInt(category_id);
        }


        int currentpage = 0;
        if (currentPage!=null && currentPage.length()>0){
            currentpage = Integer.parseInt(currentPage);
        }
        else {
            currentpage = 1;
        }

        int pagesize = 0;
        if (pageSize!=null && pageSize.length()>0){
            pagesize = Integer.parseInt(pageSize);
        }
        else {
            pagesize = 10;
        }



        PageBean<Route> pageBean = routeService.pageQuery(currentpage, c_id, pagesize,rname);
        response.setContentType("application/json;charset=utf-8");
        String serialization = super.serialization(pageBean);

        response.getWriter().write(serialization);

    }

    /**
     * 根据id查询一个详情页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收id
        int rid = Integer.parseInt(request.getParameter("rid"));

        //调用service
        Route route = routeService.detaiInfo(rid);
        //序列化传给前端
        String serialization = super.serialization(route);

        response.getWriter().write(serialization);
    }



    public void isCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rid = Integer.parseInt(request.getParameter("rid"));

        User user = (User) request.getSession().getAttribute("user");



        int uid = 0;

        if (user!=null){
            uid = user.getUid();
        }

        boolean flag = service.isCollectionService(rid, uid);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(flag);

        response.getWriter().write(json);
    }

    public void addCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rid = Integer.parseInt(request.getParameter("rid"));

        User user = (User) request.getSession().getAttribute("user");

        int uid = 0;
        if (user!=null){
            uid = user.getUid();
        }
        else return;

        service.addCollect(rid,uid);

    }

    public void Ranking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        int id = 0;
        if (cid!=null){
            id=Integer.parseInt(cid);
        }
        List<Route> ranking = routeService.ranking(id);
        String serialization = BaseServlet.serialization(ranking);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(serialization);


    }
}
