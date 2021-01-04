package com.xyou.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.ExtCproduct;
import com.xyou.domain.cargo.ExtCproductExample;
import com.xyou.domain.cargo.Factory;
import com.xyou.domain.cargo.FactoryExample;
import com.xyou.service.cargo.ExtCproductService;
import com.xyou.service.cargo.FactoryService;
import com.xyou.web.controller.system.BaseController;
import com.xyou.web.controller.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//附件
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;
    /*
        作用： 进入购销合同的附件列表中
        url:/cargo/extCproduct/list.do?contractId=728edde9-2d34-4379-a208-3f7f339f3907&contractProductId=2625a9af-002e-46a2-870f-f4540ad00c8c
        参数：contractId:购销合同的id ，contractProductId：货物id
        返回： 附件的列表
     */
    @RequestMapping("/list")
    public String list(String contractId,String contractProductId,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

        //1。 查出该货物的附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        /*extCproductExample.setOrderByClause("create_time desc");*/
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        PageInfo<ExtCproduct> pageInfo = extCproductService.findByPage(extCproductExample, pageNum, pageSize);
        //存到请求域中
        request.setAttribute("pageInfo",pageInfo);

        //查询附件的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //3.把购销合同的id和货物的id存储到域
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "/cargo/extc/extc-list";

    }


    /*
       作用：进入修改页面
       路径： /cargo/extCproduct/toUpdate.do?id=fec7eeb1-b5fd-4533-a1ff-e9caebc975e0&contractId=728edde9-2d34-4379-a208-3f7f339f3907&contractProductId=2625a9af-002e-46a2-870f-f4540ad00c8c
       参数： id：附件的id , contractId： 购销合同的id ， contractProductId:货物的id
       返回值：cargo/extc/extc-update
    */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,String contractId,String contractProductId) {
        //1 根据id查询当前附件
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct",extCproduct);
        //2 生成附件的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //3. 购销合同的id，货物的id
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        //2. 返回更新页面
        return "cargo/extc/extc-update";
    }



    /*
        作用： 保存添加与修改附件
        路径： /cargo/extCproduct/edit.do
        参数： 附件
        返回值：附件列表
    */
    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct, MultipartFile productPhoto) throws Exception {

        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);
        //附件的创建人
        extCproduct.setCreateBy(getLoginUser().getId());
        //附件创建人所属部门
        extCproduct.setCreateDept(getLoginUser().getDeptId());

        if(productPhoto.getSize()>0){
            //如果大小大于0则是有上传,把照片保存七牛云上
            String url = fileUploadUtil.upload(productPhoto);
            //把图片的url保存到附件中
            extCproduct.setProductImage("http://"+url);
        }

        //2.根据附件的id判断是否是增加还是修改
        if(StringUtils.isEmpty(extCproduct.getId())){
            //添加
            extCproductService.save(extCproduct);
        }else{
            //修改
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do";
    }


        /*
         作用：删除附件
         路径：/cargo/extCproduct/delete.do?id=${o.id}&contractId=${contractId}&contractProductId=${o.contractProductId}
         参数： 附件的id,购销合同的id，货物的id
         返回值：附件列表
        */
    @RequestMapping("/delete")
    public String delete(String id,String  contractId,String contractProductId){
        //1. 删除附件
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }

}
