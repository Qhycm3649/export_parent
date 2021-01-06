package com.xyou.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {

    //得到厂家的销售数据
    List<Map<String,Object>> getFactoryData(String companyId);

    //产品销售排行榜
    List<Map<String,Object>> getSellData(String companyId);

    //得到系统访问压力图
    List<Map<String,Object>> getOnLineData();
}
