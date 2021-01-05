package com.xyou.dao.cargo;

import com.xyou.domain.cargo.ExtEproduct;
import com.xyou.domain.cargo.ExtEproductExample;

import java.util.List;

public interface ExtEproductDao {
	/**
	 * 根据id删除
	 */
    void deleteByPrimaryKey(String id);

	/**
	 * 保存
	 */
    void insertSelective(ExtEproduct record);

	/**
	 * 条件查询
	 */
    List<ExtEproduct> selectByExample(ExtEproductExample example);

	/**
	 * 根据id查询
	 */
    ExtEproduct selectByPrimaryKey(String id);

	/**
	 * 更新
	 */
    void updateByPrimaryKeySelective(ExtEproduct record);
}