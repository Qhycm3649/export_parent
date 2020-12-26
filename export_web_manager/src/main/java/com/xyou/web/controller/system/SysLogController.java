package com.xyou.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.SysLog;
import com.xyou.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作用：进入系统管理页面
 * 路径：/system/sysLog/list.do
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController{


    //实例业务层
    @Autowired
    private SysLogService sysLogService;


    /**
     * 作用：进入系统管理页面
     * 路径：/system/sysLog/list.do
     * 参数：没有
     * 返回："system/sysLog/sysLog-list"
     */
    //使用分页功能
    @RequestMapping("/list")
    public String findAllBySysLog(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest request) {

        //自定一个企业id
        String companyId =getLoginCompanyId();
        //调用业务进行查询所有日志
        PageInfo<SysLog> pageInfo = sysLogService.findAllBySysLog(pageNum, pageSize, companyId);
        //将查询结果放入请求域中
        request.setAttribute("pageInfo", pageInfo);
        //返回："/sysLog/sysLog-list.jsp"
        return "system/log/log-list";
    }
}
