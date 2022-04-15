package com.nian.business.entity.vo.food;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FoodNoId {
    @NotNull
    @NotBlank
    @Length(min = 1,max = 10,message = "1<=length<=10")
    private String name;
    @Pattern(regexp = "(https?:[^:<>\"]*\\/)([^:<>\"]*)(\\.((png!thumbnail)|(png)|(jpg)|(webp)))")
    @NotNull
    private String image;
    private String introduce;
    @NotNull
    private Integer categoryId;
    @NotNull
    @Min(value = 0)
    @Digits(integer = 4,fraction = 2)
    private Float price;
//    @Range(min = 0,max = 1,message = "status = 0或1")
//    @Digits(integer = 3, fraction = 0)
    @NotNull
    @Range(min = 0,max = 1)

    private Integer status;
}
