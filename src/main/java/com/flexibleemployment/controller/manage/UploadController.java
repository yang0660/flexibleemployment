package com.flexibleemployment.controller.manage;

import com.flexibleemployment.shiro.AuthIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@Slf4j
@RequestMapping("/manage/file-upload")
@Api(tags = "文件上传", description = "/manage/file-upload")
@AuthIgnore
public class UploadController {

    /**
     * 上传附件
     *
     * @param
     * @return
     */
    @PostMapping(value = "/uploadAttachment")
    @ApiOperation("上传附件")
    public String upload(@RequestParam("file") CommonsMultipartFile file) throws IOException {
        String path="E:/"+file.getOriginalFilename();
        File newFile=new File(path);
        file.transferTo(newFile);
        return "/success";
    }




}
