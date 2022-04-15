package com.nian.business.entity.vo.food;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FoodNoStatus {
    @Length(min = 1,max = 10,message = "1<=length<=10")
    @NotNull
    private String name;
    @Pattern(regexp = "(^//.|^/|^[a-zA-Z])?:?/.+(/$)?")
    @NotNull
    private String image;
    private String introduce;
    @NotNull
    private Integer categoryId;
    @NotNull
    @Min(value = 0)
    @Digits(integer = 4,fraction = 2)
    private Float price;
}
