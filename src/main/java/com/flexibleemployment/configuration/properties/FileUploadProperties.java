package com.flexibleemployment.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @Description: 上传文件配置信息
 */
@Setter
@Getter
@ConfigurationProperties("file-upload")
public class FileUploadProperties {
    private String fileFolder = "file-upload";
    private String baseUrl; // 文件服务器映射地址
    private String basePath; // 上传文件的服务器的目录
    @NestedConfigurationProperty
    private ImageUploadProperties image; // 上传图片配置信息
    @NestedConfigurationProperty
    private AttachmentUploadProperties attachment; //上传附件的信息
    private String docExtNames; // 上传文档所允许的扩展名
    private String videoExtNames; // 上传视频所允许的扩展名
    private String voiceExtNames; // 上传音频所允许的扩展名

    @Setter
    @Getter
    public static class ImageUploadProperties {
        private List<String> extNames; // 上传图片所允许的扩展名
        private String maxWidth; // 上传图片的最大宽度
        private String maxHeight; // 上传图片的最大高度
        private String smallWidth; // 小图的宽度(为0时按照高度等比压缩)
        private String smallHeight; // 小图的高度(为0时按照宽度等比压缩)
        private String maxSize; // 图片上传的最大尺寸
    }

    @Setter
    @Getter
    public static class AttachmentUploadProperties {
        private List<String> extNames; // 上传附件所允许的扩展名
        private String maxSize; // 附件上传的最大尺寸
    }

    @Setter
    @Getter
    public static class FileUploadResult {
        private String imageUrl; // 文件路径
        private String smallImageUrl; // 缩略图
        private String localFile; //分布式文件系统上完整文件路径
        private String localBaseDir;//配置的父目录，通常是符号链接指向分布式存储的一个目录下
        private String localSubDir;//相对子目录
    }

}
