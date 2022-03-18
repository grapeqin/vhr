package org.javaboy.vhr.controller.salary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.model.SalaryRecord;
import org.javaboy.vhr.service.SalaryRecordService;
import org.javaboy.vhr.utils.HrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@RestController
@RequestMapping("/salary/record")
public class SalaryRecordController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(SalaryRecordController.class);

    @Autowired
    private SalaryRecordService salaryRecordService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Date[] beginDateScope) {
        return salaryRecordService.getSalaryRecordByPage(page,size,beginDateScope);
    }

    @PostMapping("/import")
    public RespBean importSalaryRecord(MultipartFile file) throws IOException {
        LOGGER.info("工资表上传文件: " + file.getOriginalFilename());
        String relativePath = FileUtils.getRelativePath(file.getOriginalFilename());
        Path path = FileUtils.getFileName(relativePath);
        LOGGER.info("工资表保存路径 :" + path);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        SalaryRecord record = new SalaryRecord();
        record.setFileLoc(relativePath);
        record.setCreateUser(HrUtils.getCurrentHr().getId());
        salaryRecordService.addSalaryRecord(record);
        return RespBean.ok("上传成功");
    }

    @GetMapping("/download")
    public void download(@RequestParam(defaultValue = "0") Integer id,HttpServletResponse response) throws IOException {
        SalaryRecord salaryRecord = salaryRecordService.getById(id);
        if(null == salaryRecord){
            failure(response,RespBean.error("文件不存在!"));
            return;
        }
        String fileLoc = salaryRecord.getFileLoc();
        Path path = FileUtils.getFileName(fileLoc);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(fileLoc, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        Files.copy(path,response.getOutputStream());
    }

    void failure(HttpServletResponse response,RespBean respBean) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(respBean);
        out.write(s);
        out.flush();
        out.close();
    }
}
