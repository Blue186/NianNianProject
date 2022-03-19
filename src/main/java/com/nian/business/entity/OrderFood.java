package com.nian.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("order_food")
@Data
public class OrderFood implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer foodId;
    private Integer orderId;
    private Integer foodNums;
    private String remark;
}
