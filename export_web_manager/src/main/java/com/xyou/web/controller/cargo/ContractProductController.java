package com.xyou.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.ContractProduct;
import com.xyou.domain.cargo.ContractProductExample;
import com.xyou.domain.cargo.Factory;
import com.xyou.domain.cargo.FactoryExample;
import com.xyou.service.cargo.ContractProductService;
import com.xyou.service.cargo.FactoryService;
import com.xyou.web.controller.system.BaseController;
import com.xyou.web.controller.utils.FileUploadUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//购销合同的货物功能
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    //分页查询
    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

        /*
        作用：进入货物列表页面
        url: /cargo/contractProduct/list.do?contractId=dd63eb3c-6d4e-4a85-9c37-fcfda1998c1d
        参数：购销合同id
        返回值 : 货物列表页面
     */
        //1. 查询当前购销合同下的货物数据,得到pageInfo
        ContractProductExample contractProductExample = new ContractProductExample();
        //由于我们只需要查询当前购销合同的货物
        contractProductExample.createCriteria().andContractIdEqualTo(contractId);

        /*contractProductExample.setOrderByClause("create_time desc");*/
        PageInfo<ContractProduct> pageInfo = contractProductService.findByPage(contractProductExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);

        // 2.查出生成货物的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        //把购销合同的id存到域中
        request.setAttribute("contractId", contractId);
        //返回到货物添加界面和列表展示界面
        return "/cargo/product/product-list";

    }


    /**
     * 作用：进入修改页面
     * 路径：/cargo/contractProduct/toUpdate.do?id=014f9637-5a8a-4ad4-a2d1-9437fbecbb7d
     * 参数：id
     * 返回："cargo/product/product-update"
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        //1. 根据id查询当前货物
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);

        //2. 生成货物的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);
        //2. 返回更新页面
        return "cargo/product/product-update";
    }


    /**
     * 作用：保存或修改货物信息
     * 路径： /cargo/contractProduct/edit.do
     * 参数： 货物
     * 返回值：货物列表
     */
    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        //1. 给购销合同补充企业的id与企业名称
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);
        //购销合同的创建人
        contractProduct.setCreateBy(getLoginUser().getId());
        //购销合同创建人所属部门
        contractProduct.setCreateDept(getLoginUser().getDeptId());

        if (productPhoto.getSize() > 0) {
            //如果大小大于0则是有上传,把照片保存七牛云上
            String url = fileUploadUtil.upload(productPhoto);
            //把图片的url保存到货物中
            contractProduct.setProductImage("http://" + url);
        }

        //2.根据购销合同的id判断是否是增加还是修改
        if (StringUtils.isEmpty(contractProduct.getId())) {
            //添加
            contractProductService.save(contractProduct);
        } else {
            //修改
            contractProductService.update(contractProduct);
        }
        //3. 返回货物的列表页面
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }


    /*
          作用：删除货物
          路径： /cargo/contractProduct/delete.do?id=${o.id}&contractId=${o.contractId}"
          参数： 货物的id,购销合同的id
          返回值：货物列表
      */
    @RequestMapping("/delete")
    public String delete(String id, String contractId) {
        //1. 删除货物
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }


    /*
        作用：进入导入货物的页面
        url:/cargo/contractProduct/toImport.do?contractId=2e25cd5d-86a2-4191-8232-c4f1ee2dd96f
        参数：购销合同的id
        返回值：cargo/product/product-import
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId) {
        //把购销合同的id存储到域中
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }


    /*
      作用：保存excel上传货物数据
      路径： /cargo/contractProduct/import.do
      参数：contractId:  购销合同的id, file: 上传的excel文件
      返回值：货物列表
    */
    @RequestMapping("/import")
    public String importExcel(String contractId,MultipartFile file) throws IOException {
        //1 创建工作簿，并且传入上传文件的输入流
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        //2 得到工作单
        Sheet sheet = workbook.getSheetAt(0);
        //3 得到行数
        int rows = sheet.getPhysicalNumberOfRows();
        //遍历所有的行
        for (int i = 1; i < rows; i++) {
            Row row = sheet.getRow(i);
            //每一行对应一个货物的对象
            ContractProduct contractProduct = new ContractProduct();
            //厂家名字
            String factoryName = row.getCell(1).getStringCellValue();
            if (factoryName != null) {
                //根据厂家的名字查询厂家
                Factory factory=factoryService.findByFactoryName(factoryName);
                contractProduct.setFactoryName(factoryName);
                //补充厂家的id
                contractProduct.setFactoryId(factory.getId());
            }

            //货号
            String productNo = row.getCell(2).getStringCellValue();
            if (productNo != null) {
                contractProduct.setProductNo(productNo);
            }

            //数量
            Double cnumber = row.getCell(3).getNumericCellValue();
            if (cnumber!=null) {
                contractProduct.setCnumber(cnumber.intValue());
            }

            //包装单位
            String packingUnit = row.getCell(4).getStringCellValue();
            if (packingUnit != null) {
                contractProduct.setPackingUnit(packingUnit);
            }

            //装率
            Double loadRate = row.getCell(5).getNumericCellValue();
            if (loadRate != null) {
                contractProduct.setLoadingRate(loadRate+"");
            }

            //箱数
            Double boxNum = row.getCell(6).getNumericCellValue();
            if (boxNum != null) {
                contractProduct.setBoxNum(boxNum.intValue());
            }

            //单价
            Double price = row.getCell(7).getNumericCellValue();
            if (price != null) {
                contractProduct.setPrice(price);
            }

            //包装单位
            String desc = row.getCell(8).getStringCellValue();
            if (desc!=null) {
                contractProduct.setProductDesc(desc);
            }

            //补充数据，，货物所属的购销合同
            contractProduct.setContractId(contractId);
            //添加货物的货物
            contractProductService.save(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }
}
