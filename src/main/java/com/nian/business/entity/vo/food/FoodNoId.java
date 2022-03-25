package com.nian.business.entity.vo.food;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FoodNoId {
    private String name;
    private String image;
    private String introduce;
    private Integer categoryId;
    private Float price;
    private Integer status;
}
