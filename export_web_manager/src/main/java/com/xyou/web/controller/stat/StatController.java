package com.xyou.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xyou.service.stat.StatService;
import com.xyou.web.controller.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Reference
    private StatService statService;
/*
    作用：进入对应的统计的页面
    url:/stat/toCharts.do?chartsType=factory
    参数：进入的页面类型
     */

    @RequestMapping("/toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }

    /*
    作用：提供厂家的销售额数据
    url:  /stat/getFactoryData.do
    参数：无
    返回值： 厂家的销售额json数据
    */
    @RequestMapping("/getFactoryData")
    @ResponseBody
    public   List<Map<String, Object>> getFactoryData(String chartsType){
        List<Map<String, Object>> list = statService.getFactoryData(getLoginCompanyId());
        return list;
    }

    /*
        作用：提供厂家的销售排行数据
        url:  /stat/getFactoryData.do
        参数：无
        返回值： 厂家的销售排行json数据
    */
    @RequestMapping("/getSellData")
    @ResponseBody
    public   List<Map<String, Object>> getSellData(String chartsType){
        List<Map<String, Object>> list = statService.getSellData(getLoginCompanyId());
        return list;
    }

    /*
        作用：统计每小时访问人数
        url: /stat/getOnlineData.do
        参数：无
        返回数据： 统计每小时访问人数
    */
    //前端同步请求，后端需要保存到域中，然后返回给前端查询
    //异步加载的话则不需要保存到域中
    @RequestMapping("/getOnLineData")
    @ResponseBody
    public   List<Map<String, Object>> getOnLineData(String chartsType){
        List<Map<String, Object>> list = statService.getOnLineData();
        return list;
    }
}
