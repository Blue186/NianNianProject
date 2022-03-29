package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Room;

import java.util.List;

public interface RoomService extends IService<Room> {
    List<Room> selectAll(Integer businessID);
    Room selectRoom(Integer businessID, Integer roomID);
    Integer createRoom(Room room);
    Integer deleteRoom(Integer businessID, Integer roomID);
    Integer updateRoom(Integer businessID, Integer roomID, String name, Integer peopleNums);
}
