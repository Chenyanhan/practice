package com.cyh.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Administrator on 2017/11/2 0002.
 */
@Data
public class CategoryForm {

    /**类目id.*/
    private Integer categoryId;
    /**
     * 类目名称
     */
    @NotEmpty(message = "类目名称不能为空")
    private String categoryName;

    /**
     * 类目类别
     */
    private Integer categoryType;
}
