package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Room;
import com.nian.business.mapper.RoomMapper;
import com.nian.business.service.RoomService;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    public List<Room> selectAll(Integer businessID) {
        return baseMapper.selectList(new QueryWrapper<Room>().eq("business_id", businessID));
    }

    @Override
    public Room selectRoom(Integer businessID, Integer roomID) {
        var wrapper = new QueryWrapper<Room>();
        wrapper.eq("business_id", businessID);
        wrapper.eq("id", roomID);

        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer createRoom(Room room) {
        return baseMapper.insert(room);
    }

    @Override
    public Integer deleteRoom(Integer businessID, Integer roomID) {
        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessID);
        map.put("id" , roomID);

        return baseMapper.delete(new QueryWrapper<Room>().allEq(map));
    }

    @Override
    public Integer updateRoom(Integer businessID, Integer roomID, String name, Integer peopleNums) {
        Room room = new Room();
        room.setName(name);
        room.setPeopleNums(peopleNums);

        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessID);
        map.put("id" , roomID);

        return baseMapper.update(room, new QueryWrapper<Room>().allEq(map));
    }

    @Override
    public Integer updateRoomQrcode(Integer businessID, Integer roomID, String qrcodeUrl) {
        var newRoom = new Room();
        newRoom.setQrcodeUrl(qrcodeUrl);

        var wrapper = new QueryWrapper<Room>();
        wrapper.eq("business_id", businessID);
        wrapper.eq("id", roomID);

        return baseMapper.update(newRoom, wrapper);
    }
}
