package com.xyou.service.stat;

import java.util.List;
import java.util.Map;

//厂家销售统计接口
public interface StatService {

    //获取厂家销售数据
    List<Map<String,Object>> getFactoryData(String companyId);
}
