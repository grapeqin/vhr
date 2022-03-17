package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.model.SalaryRecord;

import java.util.Date;
import java.util.List;

public interface SalaryRecordMapper {

    int insert(SalaryRecord record);

    SalaryRecord selectByPrimaryKey(Integer id);

    List<SalaryRecord> getSalaryRecordByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("beginDateScope") Date[] beginDateScope);

    Long getTotal(@Param("beginDateScope") Date[] beginDateScope);

}