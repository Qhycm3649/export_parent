package com.xyou.service.stat;

import java.util.List;
import java.util.Map;

//厂家销售统计接口
public interface StatService {

    //获取厂家销售数据
    List<Map<String,Object>> getFactoryData(String companyId);

    //产品销售排行榜
    List<Map<String,Object>> getSellData(String companyId);

    //得到系统访问压力图
    List<Map<String,Object>> getOnLineData();
}
