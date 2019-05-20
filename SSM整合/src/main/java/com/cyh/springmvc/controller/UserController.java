package com.cyh.springmvc.controller;


import com.cyh.springmvc.entity.User;
import com.cyh.springmvc.service.UserService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;


@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    private static Logger log=LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public @ResponseBody User getUserInJson(@PathVariable String id, Map<String, Object> model){
        int userId = Integer.parseInt(id);
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.info(user.toString());
        return user;
    }


    @RequestMapping(value="/jsontype/{id}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User>  getUserInJson2(@PathVariable String id,Map<String, Object> model){
        int userId = Integer.parseInt(id);
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);
        log.info(user.toString());
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }


    @RequestMapping(value="/upload")
    public String showUploadPage(){
        return "user_admin/file";
    }

    @RequestMapping(value="/doUpload",method=RequestMethod.POST)
    public String doUploadFile(@RequestParam("file")MultipartFile file) throws IOException{
        if (!file.isEmpty()) {
            log.info("Process file:{}",file.getOriginalFilename());
        }
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File("E:\\",System.currentTimeMillis()+file.getOriginalFilename()));
        return "succes";
    }



}
