package com.nian.business.entity.vo.food;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class FoodStatus{
    @NotNull
    @Range(min = 0,max = 1)
    private Integer status;
}
