package com.nian.business.entity.vo.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomIDNamePeopleNum {
    private Integer id;
    private String name;
    private Integer people_nums;
}
