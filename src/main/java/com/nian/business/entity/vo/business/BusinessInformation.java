package com.nian.business.entity.vo.business;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BusinessInformation {
    String shopName;
    String bossName;
    String Address;
    String image;
    String phone;
}
