package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.SalaryRecordMapper;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.model.SalaryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalaryRecordService {

    @Autowired
    private SalaryRecordMapper salaryRecordMapper;

    public RespPageBean getSalaryRecordByPage(Integer page, Integer size, Date[] beginDateScope){
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<SalaryRecord> data = salaryRecordMapper.getSalaryRecordByPage(page, size, beginDateScope);
        Long total = salaryRecordMapper.getTotal(beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addSalaryRecord(SalaryRecord record){
        return salaryRecordMapper.insert(record);
    }

    public SalaryRecord getById(Integer id){
        if(null == id || 0 == id){
            return null;
        }
        return salaryRecordMapper.selectByPrimaryKey(id);
    }
}
