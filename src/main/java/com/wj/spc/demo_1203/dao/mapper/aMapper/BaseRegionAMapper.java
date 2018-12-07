package com.wj.spc.demo_1203.dao.mapper.aMapper;

import com.wj.spc.demo_1203.domain.City;
import com.wj.spc.demo_1203.domain.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseRegionAMapper {
    List<Province> getProvince();

    List<City> getCity(@Param("provinceId") int provinceId);
}
