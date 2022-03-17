package org.javaboy.vhr.controller.salary;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class FileUtils {

    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 根据用户上传的文件名生成新的文件名
     * @param fileName
     * @return
     */
    public static Path getFileName(String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath().resolve("files"+File.separator+fileName);
        File newFile = new File(path.toString());
        if(!newFile.exists()){
            newFile.createNewFile();
        }
        return path;
    }

    public static String getRelativePath(String fileName){
        StringBuilder builder = new StringBuilder(dtf.format(LocalDateTime.now())).append("-").append(fileName);
        return builder.toString();
    }
}
