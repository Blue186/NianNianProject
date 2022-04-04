package com.nian.business.entity.vo.food;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FoodMenuItem {
    Integer id;
    String name;
    String image;
    String introduce;
    Float price;
    Integer status;
    Integer categoryId;
}
