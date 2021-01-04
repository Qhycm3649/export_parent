package com.xyou.service.cargo;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.ContractProduct;
import com.xyou.domain.cargo.ContractProductExample;
import com.xyou.vo.ContractProductVo;

import java.util.List;

/**
 * 购销合同货物模块
 */
public interface ContractProductService {

    /**
     * 分页查询
     * @param ContractProductExample 分页查询的参数
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @return
     */
    PageInfo<ContractProduct> findByPage(
            ContractProductExample ContractProductExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<ContractProduct> findAll(ContractProductExample ContractProductExample);

    /**
     * 根据id查询
     */
    ContractProduct findById(String id);

    /**
     * 新增
     */
    void save(ContractProduct contractProduct);

    /**
     * 修改
     */
    void update(ContractProduct contractProduct);

    /**
     * 删除部门
     */
    void delete(String id);

    //根据船期查询出货表
    List<ContractProductVo> findByShipTime(String shipTime, String companyId);

}











