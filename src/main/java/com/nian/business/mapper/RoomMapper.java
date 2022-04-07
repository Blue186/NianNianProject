package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nian.business.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    @Select("update room set is_delete=now() where id=#{roomID} and business_id=#{businessID} and is_delete=0")
    void softDeleteRoom(@Param("businessID") Integer businessID, @Param("roomID") Integer roomID);
}
