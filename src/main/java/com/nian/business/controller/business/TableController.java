package com.nian.business.controller.business;

import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;
import com.nian.business.entity.Table;
import com.nian.business.entity.vo.table.TableIDNamePeopleNum;
import com.nian.business.service.TableService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

}
