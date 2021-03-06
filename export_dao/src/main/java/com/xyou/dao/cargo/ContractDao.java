package com.xyou.dao.cargo;

import com.xyou.domain.cargo.Contract;
import com.xyou.domain.cargo.ContractExample;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    //大区经理查看购销合同
    List<Contract> findByDeptId(String deptId);
}