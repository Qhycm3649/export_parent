package com.xyou.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {

    //得到厂家的销售数据
    List<Map<String,Object>> getFactoryData(String companyId);
    //
}
