package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nian.business.entity.Business;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessMapper extends BaseMapper<Business> {
//    这里可以封装对数据库的操作，同时对应一个mapper.xml文件，在resource路径下
}
