package com.nian.business.controller.business;

import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;
import com.nian.business.entity.Table;
import com.nian.business.entity.vo.table.TableIDNamePeopleNum;
import com.nian.business.entity.vo.table.TableNamePeopleNum;
import com.nian.business.service.TableService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/business/table")
public class TableController {
    @Resource
    private TableService tableService;

    @GetMapping
    public R<?> getTables(HttpServletRequest request) {
        Business business = (Business) request.getAttribute("business");

        List<Table> tables = tableService.selectAll(business.getId());

        List<TableIDNamePeopleNum> tablesInformation = new ArrayList<>();
        for (Table table : tables) {
            TableIDNamePeopleNum tableInformation = new TableIDNamePeopleNum();
            tableInformation.setId(table.getId());
            tableInformation.setName(table.getName());
            tableInformation.setPeopleNums(table.getPeopleNums());
            tablesInformation.add(tableInformation);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.set("tables", tablesInformation);

        return R.ok().message("成功").detail(jsonObject);
    }


    @PostMapping
    public R<?> createTable(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody TableNamePeopleNum rJson
    ){
        Business business = (Business) request.getAttribute("business");

        Table table = new Table();
        table.setBusinessId(business.getId());
        table.setName(rJson.getName());
        table.setPeopleNums(rJson.getPeopleNums());

        Integer ret = tableService.createTable(table);
        if (ret != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("创建失败");
        }

        return R.ok().message("success").detail(null);
    }

    @DeleteMapping("/{tableID}")
    public R<?> deleteTable(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer tableID
    ){
        Business business = (Business) request.getAttribute("business");

        Integer ret = tableService.deleteTable(business.getId(), tableID);
        if (ret != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("删除失败");
        }

        return R.ok().status("success").message("delete table success");
    }

    @PutMapping("/{tableID}")
    public R<?> updateTable(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer tableID,
            @RequestBody TableNamePeopleNum rJson
    ){
        Business business = (Business) request.getAttribute("business");

        Integer ret = tableService.updateTable(business.getId(), tableID, rJson.getName(), rJson.getPeopleNums());
        if (ret != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("更新失败");
        }

        return R.ok().message("update table success");
    }
}
