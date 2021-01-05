package com.xyou.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.*;
import com.xyou.service.cargo.ContractService;
import com.xyou.service.cargo.ExportProductService;
import com.xyou.service.cargo.ExportService;
import com.xyou.vo.ExportProductVo;
import com.xyou.vo.ExportResult;
import com.xyou.vo.ExportVo;
import com.xyou.web.controller.system.BaseController;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
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


    /*
   作用： 查看报运单
   路径：/cargo/export/toView.do?id=26121e37-9e05-4167-8be7-f8675f8dcab6
   参数： 报运单id
   返回值：cargo/export/export-view
  */
    @RequestMapping("/toView")
    public String toView(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        return "cargo/export/export-view";
    }

    /*
        作用： 提交报运单
        路径：/cargo/export/submit.do?id=26121e37-9e05-4167-8be7-f8675f8dcab6
        参数： 报运单id
        返回值：报运单列表
    */
    @RequestMapping("/submit")
    public String submit(String id) {
        Export export = exportService.findById(id);
        export.setState(1);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    /*
      作用： 取消报运单
      路径：/cargo/export/cancel.do?id=26121e37-9e05-4167-8be7-f8675f8dcab6
      参数： 报运单id
      返回值：报运单列表
  */
    @RequestMapping("/cancel")
    public String cancel(String id) {
        Export export = exportService.findById(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    /*
        作用： 电子报运
        路径：/cargo/export/exportE.do?id=26121e37-9e05-4167-8be7-f8675f8dcab6
        参数： 报运单id
        返回值：报运单列表
    */
    @RequestMapping("/exportE")
    public String exportE(String id) {
        //1 根据id找到对应的报运单 Export对象
        Export export = exportService.findById(id);

        //2 创建ExportVo对象，目标是把Export的数据复制到ExportVo对象，因为需要与海关实体类一致
        ExportVo exportVo = new ExportVo();
            //属性拷贝
        BeanUtils.copyProperties(export, exportVo);
            //补充数据
        exportVo.setExportId(export.getId());

        //3 找到报运单对应的商品的数据
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> productList = exportProductService.findAll(exportProductExample);

        //4 需要把商品的数据转换为ExportProductVo对象
        for (ExportProduct exportProduct : productList) {
            //每一个报运单的商品就对应一个ExportProductVo
            ExportProductVo exportProductVo = new ExportProductVo();
            //拷贝属性
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            //补充数据
            exportProductVo.setExportId(export.getId());
            //商品的id
            exportProductVo.setExportProductId(exportProduct.getId());
            //把exportProductVo对象添加到ExportVo对象
            exportVo.getProducts().add(exportProductVo);
        }

        //5 把ExprotVo对象通过WebService提交给海关
        WebClient.create("http://localhost:9091/ws/export/user").post(exportVo);

        //6 查看报运单的审核结果
        ExportResult exportResult = WebClient.create("http://localhost:9091/ws/export/user/" + id).get(ExportResult.class);

        //7 根据海关的审核结果更新报运单的信息
        exportService.updateState(exportResult);
            //返回
        return "redirect:/cargo/export/list.do";
    }
}
