package com.nian.business.entity.vo.category;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CategoryPriorName implements Serializable {
    @NotNull
    @Length(min = 1, max = 12)
    private String name;
    @NotNull
    @Range(min = 1,max = 1000,message = "1<=priority<=1000")
    private Integer priority;
}
