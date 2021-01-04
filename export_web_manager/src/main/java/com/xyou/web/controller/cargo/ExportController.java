package com.xyou.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.*;
import com.xyou.service.cargo.ContractService;
import com.xyou.service.cargo.ExportProductService;
import com.xyou.service.cargo.ExportService;
import com.xyou.web.controller.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//合同管理
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    /*
        作用： 进入合同列表页面
        url： /cargo/export/contractList.do
        参数： 无
        返回值： cargo/export/export-contractList
    */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

        ContractExample contractExample = new ContractExample();
        //添加条件
        contractExample.createCriteria().andStateEqualTo(1).andCompanyIdEqualTo(getLoginCompanyId());
        //根据创建的时间排序
        contractExample.setOrderByClause("create_time desc");
        //分页
        PageInfo<Contract> pageInfo = contractService.findByPage(contractExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/export/export-contractList";
    }


    /*
        作用： 进入报运单列表页面
        url： /cargo/export/list.do
        参数： 无
        返回值： cargo/export/export-list
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

        //找到所有报运单
        ExportExample exportExample = new ExportExample();
        //根据创建的时间排序
        exportExample.setOrderByClause("create_time desc");
        //添加条件
        exportExample.createCriteria().andCompanyIdEqualTo(getLoginCompanyId());
        //分页
        PageInfo<Export> pageInfo = exportService.findByPage(exportExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/export/export-list";
    }


    /*
        作用： 进入生成报运单的页面
        url： /cargo/export/toExport.do
        参数： 选中的购销合同的id
        返回值： cargo/export/export-toExport
    */
    @RequestMapping("/toExport")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }


    /*
     作用： 保存添加与修改报运单
     路径： /cargo/export/edit.do
     参数： 报运单
     返回值：报运单列表
    */
    @RequestMapping("/edit")
    public String edit(Export export) {
        //1 给 报运单不吃葱企业的id与企业的名称
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        //报运单的创建人与其部门
        export.setCreateBy(getLoginUser().getId());
        export.setCreateDept(getLoginUser().getDeptId());
        //2 根据报运单的id判断是否是添加还是修改
        if (StringUtils.isEmpty(export.getId())) {
            exportService.save(export);
        } else {
            exportService.update(export);
        }
        //返回报运单的列页面
        return "redirect:/cargo/export/list.do";
    }


    /*
        作用： 进入生成报运单的页面
        url： /cargo/export/toUpdate.do?id=c936b0e0-d293-47eb-a882-eddc04c317b3
        参数： 选中的购销合同的id
        返回值： cargo/export/export-toExport
    */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        Export export = exportService.findById(id);
        //1 存储到域中
        request.setAttribute("export", export);
        //2 查询报运商品的数据
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        //3 存储到域中
        request.setAttribute("eps", exportProductList);

        return "cargo/export/export-update";
    }
}
