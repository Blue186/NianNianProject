package com.nian.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("order_foods")
@Data
public class OrderFood implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer foodNums;
    private String remark;

    // 食物区
    private Integer foodId;
    private String name;
    private String image;
    private String introduce;
    private Float price;

    // 标记位
    private Boolean finish;
    private Boolean cancel;
    private Date submitTime;
}
