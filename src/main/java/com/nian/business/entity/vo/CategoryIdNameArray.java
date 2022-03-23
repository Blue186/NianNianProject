package com.nian.business.entity.vo;
import com.nian.business.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryIdNameArray {
    private List<CategoryIdName> categories;
}
