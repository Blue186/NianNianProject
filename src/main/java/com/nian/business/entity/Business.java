package com.nian.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("business")
@Data
public class Business implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String shopName;
    private String phone;
    private String address;
    private String openid;
    private String password;
    private String image;
    private String bossName;
}
