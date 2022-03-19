package com.nian.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("order")
@Data
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createTime;
    private Integer creatorId;
    private Integer status;
    private Date updateTime;
    private Integer tableId;
    private String key;
    private Integer peopleNums;
    private Date submitTime;
}
