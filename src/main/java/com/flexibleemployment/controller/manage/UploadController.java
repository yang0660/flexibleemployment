package com.flexibleemployment.controller.manage;

import com.flexibleemployment.configuration.properties.FileUploadProperties;
import com.flexibleemployment.service.OrderAttachmentService;
import com.flexibleemployment.service.TaskAttachmentService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.FileUploadReqVo;
import com.flexibleemployment.vo.request.OrderAttachmentReqVO;
import com.flexibleemployment.vo.request.TaskAttachmentReqVO;
import com.flexibleemployment.vo.response.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.attribute.FileStoreAttributeView;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/manage/file-upload")
@Api(tags = "文件上传", description = "/manage/file-upload")
@AuthIgnore
public class UploadController {

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private TaskAttachmentService taskAttachmentService;

    @Autowired
    private OrderAttachmentService orderAttachmentService;

    /**
     * 上传附件
     *
     * @param
     * @return
     */
    @PostMapping(value = "/taskUploadAttachment")
    @ApiOperation("上传附件")
    //@RequestParam("file") CommonsMultipartFile file,@RequestParam("taskId") Long taskId
    public ResultVO<String> taskUploadAttachment(FileUploadReqVo fileUploadReqVo) throws IOException {
        String fileName = fileUploadReqVo.getFile().getFileItem().getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        String localName =UUID.randomUUID()+"."+fileType;
        String path=fileUploadProperties.getBasePath()+localName;
        File newFile=new File(path);
        fileUploadReqVo.getFile().transferTo(newFile);
        String remoteUrl = fileUploadProperties.getBaseUrl()+localName;
        log.info("上传附件 remoteUrl:"+remoteUrl);
        //关联任务与附件对应关系
        try{
            //先删除附件,如果有的话
            taskAttachmentService.delete(fileUploadReqVo.getId());
            TaskAttachmentReqVO taskAttachmentReqVO = new  TaskAttachmentReqVO();
            taskAttachmentReqVO.setTaskId(fileUploadReqVo.getId());
            taskAttachmentReqVO.setAttachmentUrl(remoteUrl);
            taskAttachmentService.add(taskAttachmentReqVO);

            log.info("任务附件---上传附件 插入数据成功:");
        }catch (Exception ex){
            ex.printStackTrace();
            return ResultVO.validError("附件上传失败");
        }

        return ResultVO.success(remoteUrl);
    }

    /**
     * 上传附件
     *
     * @param
     * @return
     */
    @PostMapping(value = "/orderUploadAttachment")
    @ApiOperation("上传附件")
    //@RequestParam("file") CommonsMultipartFile file,@RequestParam("taskId") Long taskId
    public ResultVO<String> orderUploadAttachment(FileUploadReqVo fileUploadReqVo) throws IOException {
        String fileName = fileUploadReqVo.getFile().getFileItem().getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        String localName =UUID.randomUUID()+"."+fileType;
        String path=fileUploadProperties.getBasePath()+localName;
        File newFile=new File(path);
        fileUploadReqVo.getFile().transferTo(newFile);

        String remoteUrl = fileUploadProperties.getBaseUrl()+localName;
        log.info("上传附件 remoteUrl:"+remoteUrl);
        //关联任务与附件对应关系
        try{
            //先删除附件,如果有的话
            orderAttachmentService.deleteByOrderId(fileUploadReqVo.getId());
            OrderAttachmentReqVO orderAttachmentReqVO = new  OrderAttachmentReqVO();
            orderAttachmentReqVO.setOrderId(fileUploadReqVo.getId());
            orderAttachmentReqVO.setAttachmentUrl(remoteUrl);
            orderAttachmentService.add(orderAttachmentReqVO);

            log.info("项目附件---上传附件 插入数据成功:");
        }catch (Exception ex){
            ex.printStackTrace();
            return ResultVO.validError("附件上传失败");
        }
        return ResultVO.success(remoteUrl);
    }

    //实现Spring Boot 的文件下载功能，映射网址为/download
    @GetMapping("/download")
    @ApiOperation("下载附件")
    @AuthIgnore
    public String downloadFile(@RequestParam("name") String fileName, HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = fileUploadProperties.getBasePath()+fileName;
            File file = new File(realPath);

            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }




}
