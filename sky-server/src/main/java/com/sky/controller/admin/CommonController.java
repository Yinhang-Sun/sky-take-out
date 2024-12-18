package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


/**
 * Common APIs
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common API")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * File upload
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("File upload")
    public Result<String> upload(MultipartFile file) {
        log.info("File upload: {}", file);

        try {
            //Original filename
            String originalFilename = file.getOriginalFilename();
            //Intercept the suffix of the original file name, file.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //Construct new filename
            String objectName = UUID.randomUUID().toString() + extension;

            //File's request path
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("File upload failed: {}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
