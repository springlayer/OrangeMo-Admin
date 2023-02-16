package com.orange.mo.common.file;


import com.alibaba.fastjson.JSON;
import com.orange.mo.common.api.R;
import com.orange.mo.utils.UploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/file/common")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 通用上传请求（单个）
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public R uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            FileStorageData upload = UploadFileUtil.upload(file);
            log.info("文件上传成功: ", JSON.toJSONString(upload));
            return R.data(upload);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping(value = "/upload/base64")
    @ResponseBody
    public R uploadFile(@RequestBody FileStorageData fileStorageData) throws Exception {
        try {
            FileStorageData upload = UploadFileUtil.uploadBase64(fileStorageData.getBase64(),fileStorageData.getOriginalName());
            log.info("文件上传成功: ", JSON.toJSONString(upload));
            return R.data(upload);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
