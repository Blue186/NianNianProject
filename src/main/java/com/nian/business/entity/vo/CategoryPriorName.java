package com.nian.business.entity.vo;

import lombok.Data;


import java.io.Serializable;

@Data
public class CategoryPriorName implements Serializable {
    private String name;
    private Integer priority;
}
