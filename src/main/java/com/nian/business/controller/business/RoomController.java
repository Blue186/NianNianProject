package com.nian.business.controller.business;

import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;
import com.nian.business.entity.Room;
import com.nian.business.entity.vo.room.RoomIDNamePeopleNum;
import com.nian.business.entity.vo.room.RoomNamePeopleNum;
import com.nian.business.service.RoomService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/business/room")
public class RoomController {
    @Resource
    private RoomService roomService;

    @GetMapping
    public R<?> getRooms(HttpServletRequest request) {
        Business business = (Business) request.getAttribute("business");

        List<Room> rooms = roomService.selectAll(business.getId());

        List<RoomIDNamePeopleNum> roomsInformation = new ArrayList<>();
        for (Room room : rooms) {
            RoomIDNamePeopleNum roomInformation = new RoomIDNamePeopleNum();
            roomInformation.setId(room.getId());
            roomInformation.setName(room.getName());
            roomInformation.setPeopleNums(room.getPeopleNums());
            roomsInformation.add(roomInformation);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.set("rooms", roomsInformation);

        return R.ok().message("成功").detail(jsonObject);
    }

    @PostMapping
    public R<?> createRoom(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody RoomNamePeopleNum rJson
    ){
        Business business = (Business) request.getAttribute("business");

        Room room = new Room();
        room.setBusinessId(business.getId());
        room.setName(rJson.getName());
        room.setPeopleNums(rJson.getPeopleNums());

        Integer ret = roomService.createRoom(room);
        if (ret != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("创建失败");
        }

        return R.ok().message("success").detail(null);
    }

    @DeleteMapping("/{roomID}")
    public R<?> deleteRoom(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer roomID
    ){
        Business business = (Business) request.getAttribute("business");

        Integer ret = roomService.deleteRoom(business.getId(), roomID);
        if (ret != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("删除失败");
        }

        return R.ok().status("success").message("delete room success");
    }

    @PutMapping("/{roomID}")
    public R<?> updateTable(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer roomID,
            @RequestBody RoomNamePeopleNum rJson
    ){
        Business business = (Business) request.getAttribute("business");

        Integer ret = roomService.updateRoom(business.getId(), roomID, rJson.getName(), rJson.getPeopleNums());
        if (ret != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("更新失败");
        }

        return R.ok().message("update room success");
    }
}
