package com.cyh.sell.controller;

import com.cyh.sell.dataobject.ProductCategory;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.form.CategoryForm;
import com.cyh.sell.form.ExceptionCodeEnums;
import com.cyh.sell.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam  Map<String,Object> map){
        List<ProductCategory> all = categoryService.findAll();
        map.put("categoryList",all);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId" , required = false ) Integer categoryId ,
                              Map<String,Object> map) {
        if(!StringUtils.isEmpty(categoryId)) {
            //1.查找商品
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category" , productCategory);
        }

        return new ModelAndView("/category/index" , map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map) {

        if(bindingResult.hasErrors()) {
            //1.前台数据验证不通过
            map.put("msg" , bindingResult.getFieldError().getDefaultMessage());
            map.put("url" , "/seller/category/index");
            return new ModelAndView("/common/error" , map);
        }

        ProductCategory category = new ProductCategory();

        if(!StringUtils.isEmpty(categoryForm.getCategoryId())) {
            category = categoryService.findOne(categoryForm.getCategoryId());
            if(null == category) {
                log.error("【卖家新增类目】类目不存在，categoryId={}" , categoryForm.getCategoryId());
                map.put("msg" , ExceptionCodeEnums.CATEGORY_NOT_FOUND.getMsg());
                map.put("url" , "/sell/seller/category/index");
                return new ModelAndView("/common/error" , map);
            }
        }

        BeanUtils.copyProperties(categoryForm , category);

        try {
            categoryService.save(category);
        }catch (SellException e) {
            log.error("【卖家新增类目】发生错误，e={}" , e);
            map.put("msg" , ExceptionCodeEnums.CATEGORY_UPDATE_FAIL.getMsg());
            map.put("url" , "=/seller/category/index");
            return new ModelAndView("/common/error" , map);
        }

        map.put("msg" , ExceptionCodeEnums.CATEGORY_UPDATE_SUCCESS.getMsg());
        map.put("url" , "/seller/category/list");

        return new ModelAndView("common/success" , map);
    }


}
