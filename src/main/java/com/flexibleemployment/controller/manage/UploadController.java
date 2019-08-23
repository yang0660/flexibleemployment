package com.flexibleemployment.controller.manage;

import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.utils.file.FileUploadHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@Slf4j
@RequestMapping("/manage/upload")
@Api(tags = "文件上传", description = "/manage/upload")
@AuthIgnore
public class UploadController {

    @Autowired
    FileUploadHandle fileUploadHandle;

    /**
     * 上传附件
     *
     * @param
     * @return
     */
    @PostMapping(value = "/uploadAttachment")
    @ApiOperation("上传附件")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        try {
            return fileUploadHandle.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return "上传失败";
        }
    }




}
