package com.xyou.service.cargo;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.cargo.Export;
import com.xyou.domain.cargo.ExportExample;
import com.xyou.vo.ExportResult;

import java.util.List;


public interface ExportService {

    Export findById(String id);

    List<Export> findAll(ExportExample example);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo<Export> findByPage(ExportExample example, int pageNum, int pageSize);

	//根据海关的审核结果更新报运单的信息
    void updateState(ExportResult exportResult);
}
