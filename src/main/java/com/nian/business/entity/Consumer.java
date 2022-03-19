package com.nian.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@TableName("consumer")
@Data
public class Consumer implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String openid;
}
