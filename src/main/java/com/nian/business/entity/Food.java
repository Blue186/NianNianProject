package com.nian.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("food")
@Data
public class Food implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String image;
    private String introduce;
    private Integer categoryId;
    private Float price;
    private Integer status;
}
