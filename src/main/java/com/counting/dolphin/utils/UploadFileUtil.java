package com.counting.dolphin.utils;

import com.counting.dolphin.exception.BusinessException;
import com.counting.dolphin.common.file.FileStorageData;
import com.counting.dolphin.config.CountingDolphinConfig;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadFileUtil {

    /**
     * @param file
     * @return
     * @Title: upload
     * @Description: (将文件保存到指定的路径下)
     * @author 侯志
     */
    public static FileStorageData upload(MultipartFile file) {
        FileStorageData data = new FileStorageData();
        try {
            String extName = file.getOriginalFilename();
            data.setOriginalName(extName);
            // 获取文件后缀
            if (extName.lastIndexOf(".") <= 0) {
                throw new BusinessException("不支持该文件类型");
            }
            extName = extName.substring(extName.lastIndexOf("."));
            String fileName = getFileName();
            // 获取文件名字
            fileName = getFileName() + extName;
            // 获取文件地址
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(new Date()); //2022-02-16 日期目录
            String sysPath = CountingDolphinConfig.getUploadPath();
            File file2 = new File(sysPath, format);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            file.transferTo(new File(sysPath + format + "/" + fileName));
            data.setFileUrl(sysPath + format + "/" + fileName);
            data.setFileName(fileName);
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param file
     * @return
     * @Title: upload
     * @Description: (将文件保存到指定的路径下)
     * @author 侯志
     */
    public static FileStorageData upload(MultipartFile file, String specifiedPath) {
        FileStorageData data = new FileStorageData();
        try {
            String extName = file.getOriginalFilename();
            // 获取文件后缀
            if (extName.lastIndexOf(".") <= 0) {
                throw new BusinessException("不支持该文件类型");
            }
            extName = extName.substring(extName.lastIndexOf("."));
            String fileName = getFileName();
            // 获取文件名字
            fileName = getFileName() + extName;
            File file2 = new File(specifiedPath);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            file.transferTo(new File(specifiedPath + File.separator + fileName));
            data.setFileName(fileName);
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传base64
     *
     * @param image
     * @param originalName
     * @return FileStorageData
     * @throws IOException
     */
    public static FileStorageData uploadBase64(String image, String originalName) throws IOException {
        FileStorageData fileStorageData = new FileStorageData();
        final String[] base64Array = image.split(",");
        String dataUir = base64Array[0];
        String data = base64Array[1];
        String fileName = getFileName();
        fileStorageData.setOriginalName(originalName);
        // 获取文件后缀
        if (originalName.lastIndexOf(".") <= 0) {
            throw new BusinessException("不支持该文件类型");
        }
        originalName = originalName.substring(originalName.lastIndexOf("."));
        MultipartFile multipartFile = new Base64ToMultipartFile(data, dataUir, fileName + originalName);
        fileStorageData.setFileName(fileName + originalName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date()); //2022-02-16 日期目录
        String sysPath = CountingDolphinConfig.getUploadPath();
        File file = new File(sysPath, format);
        if (!file.exists()) {
            file.mkdirs();
        }
        multipartFile.transferTo(new File(sysPath + format + "/" + fileName + originalName));
        fileStorageData.setFileUrl(sysPath + format + "/" + fileName + originalName);
        return fileStorageData;
    }

    /**
     * 获取文件名
     *
     * @return
     */
    public static String getFileName() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid.toLowerCase();
    }

    public static String getWebUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getServletContext().getRealPath("/img");
    }

    public static String getWebProUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
