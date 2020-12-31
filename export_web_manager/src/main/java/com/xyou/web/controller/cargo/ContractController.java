package com.xyou.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.Contract;
import com.xyou.domain.cargo.ContractExample;
import com.xyou.service.cargo.ContractService;
import com.xyou.web.controller.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//购销合同
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    //分页查询
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andCompanyIdEqualTo(getLoginCompanyId());
        contractExample.setOrderByClause("create_time desc");

        PageInfo<Contract> pageInfo = contractService.findByPage(contractExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "/cargo/contract/contract-list";

    }


    /**
     * 作用：进入添加页面
     * 路径：/cargo/contract/toAdd.do
     * 参数：Contract
     * 返回："cargo/contract/contract-add"
     */
    @RequestMapping("/toAdd")
    public String toAdd() {

        return "cargo/contract/contract-add";
    }


    /**
     * 作用：进入修改页面
     * 路径：/cargo/contract/toUpdate.do
     * 参数：Contract
     * 返回："cargo/contract/contract-update"
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "cargo/contract/contract-update";
    }



    /**
     * 作用：保存或修改部门信息
     * 路径：/cargo/contract/toUpdate.do
     * 参数：Contract
     * 返回："redirect:/cargo/contract/list.do"
     */
    @RequestMapping("/edit")
    public String edit(Contract contract) {
        //1. 给购销合同补充企业的id与企业名称
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        //购销合同的创建人
        contract.setCreateBy(getLoginUser().getId());
        //购销合同创建人所属部门
        contract.setCreateDept(getLoginUser().getDeptId());

        //2.根据购销合同的id判断是否是增加还是修改
        if(StringUtils.isEmpty(contract.getId())){
            //添加
            contractService.save(contract);
        }else{
            //修改
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 作用：删除部门信息
     * 路径：/cargo/contract/delete.do?id=4?
     * 参数：id
     */
    @RequestMapping("/delete")
    public String delete(String id){
        //1. 删除购销合同
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 作用：购销合同查看
     * 路劲：/cargo/contract/toView.do?id=dd63eb3c-6d4e-4a85-9c37-fcfda1998c1d
     * 参数：id
     */
    @RequestMapping("/toView")
    public String toView(String id) {

        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "/cargo/contract/contract-view";

    }

    /**
     * 作用：购销合同提交
     * 路劲：/cargo/contract/submit.do?id=dd63eb3c-6d4e-4a85-9c37-fcfda1998c1d
     * 参数：id
     */
    @RequestMapping("/submit")
    public String submit(String id) {

        Contract contract = contractService.findById(id);
        request.setAttribute("contractSubmit",contract);

        contract.setState(1);
        contractService.update(contract);

        return "redirect:/cargo/contract/list.do";

    }


    /*
      作用 ： 购销合同取消
      url :  /cargo/contract/submit.do?id=dd63eb3c-6d4e-4a85-9c37-fcfda1998c1d
     参数 : 购销合同id
     返回值 :购销合同列表页面
   */
    @RequestMapping("/cancel")
    public String cancel(String id){
        //1. 根据购销合同的id查找购销合同
        Contract contract =  contractService.findById(id);
        //2. 修改购销合同的状态为0,更新购销合同
        contract.setState(0);
        contractService.update(contract);
        //3. 返回购销合同列表页面
        return "redirect:/cargo/contract/list.do";
    }
}
