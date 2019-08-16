package com.flexibleemployment.utils.file;

import com.alibaba.fastjson.JSON;
import com.flexibleemployment.configuration.properties.FileUploadProperties;
import com.flexibleemployment.utils.BizException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by zhangwanli on 2017/11/11.
 */
public class FileUploadHandler {

    private final FileUploadProperties properties;

    @Autowired
    private FileUploadProperties fileUploadProperties;

    private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd");

    public FileUploadHandler(FileUploadProperties properties) {
        this.properties = properties;
    }

    private static Logger log = LoggerFactory.getLogger(FileUploadHandler.class);


    /**
     * @param base64File   图片base64字符串
     * @param isScaleSmall 是否生成略缩图
     * @param expectExt    扩展名
     * @return
     */
    public FileUploadProperties.FileUploadResult upload(String base64File, boolean isScaleSmall, String... expectExt) {
        if (StringUtils.isBlank(base64File)) {
            throw new BizException("上传图片不能为空");
        }
        return upload(Base64.decodeBase64(base64File), isScaleSmall, expectExt);
    }

    /**
     * @param fileBytes    图片base64字节数组
     * @param isScaleSmall 是否生成略缩图
     * @param extNames     扩展名
     * @return
     */
    public FileUploadProperties.FileUploadResult upload(byte[] fileBytes, boolean isScaleSmall, String... extNames) {
        // 扩展名
        String extension = FileMediaExtensions.parseMediaExtension(fileBytes);
        List<String> extNameList = extNames != null && extNames.length > 0 ? Arrays.asList(extNames) : Collections.EMPTY_LIST;
        if (!extNameList.isEmpty() && !extNameList.contains(extension)) {
            extension = extNameList.get(0);
        }
        if (StringUtils.isBlank(extension)) {
            log.error("解析文件扩展名失败");
            throw new BizException("解析文件扩展名失败");
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
        String fileName = UUID.randomUUID().toString() + "." + extension; // 文件名
        String localBaseDir = getLocalBasePath();
        String localSubDir = getLocalSubDir(localBaseDir);
        String localFile = localBaseDir + localSubDir + File.separator + fileName;
        // 复制文件到目标文件(使用ImageIO处理流)
        try {
            FileUtils.copyInputStreamToFile(bis, new File(localFile));
        } catch (IOException ioe) {
            log.error("复制文件失败{}", ioe);
            return null;
        }
        // 生成源图外网地址
        String urlPath = this.getExternalUrl(localSubDir.replaceAll("\\\\", "/") + "/" + fileName);
        FileUploadProperties.FileUploadResult fileUploadResult = new FileUploadProperties.FileUploadResult();
        fileUploadResult.setImageUrl(urlPath);
        if (isScaleSmall && scaleSmallImage(localFile)) {
            String smallUrlPath = urlPath.replaceFirst("\\.(?=[a-z]+$)", "_" + ImageUploadUtil.SCALE_FROMAT_SMALL + ".");
            fileUploadResult.setSmallImageUrl(smallUrlPath);
        }
        log.info("上传图片成功###本地文件地址={}, 外网文件地址={}", localFile, JSON.toJSONString(fileUploadResult));
        return fileUploadResult;
    }

    /**
     * @param localImageUrl 本地图片路径
     * @return
     */
    private boolean scaleSmallImage(String localImageUrl) {
        assert StringUtils.isNotBlank(localImageUrl);
        String localSmallFile = localImageUrl.replaceFirst("\\.(?=[a-z]+$)", "_" + ImageUploadUtil.SCALE_FROMAT_SMALL + ".");
        String extName = localImageUrl.substring(StringUtils.lastIndexOf(localImageUrl, ".") + 1);
        try {
            File file = new File(localImageUrl);
            BufferedImage image = ImageIO.read(new FileInputStream(file));
            int smallWidth = image.getWidth();
            int smallHeight = image.getHeight();
            if ((file.length() / ImageUploadUtil.BYTES_CONVERSION) > ImageUploadUtil.PICTURE_LIMITED_SIZE) {
                double ratio = ImageUploadUtil.SMALL_WIDTH / image.getHeight();
                smallWidth = (int) (image.getWidth() * ratio);
                smallHeight = (int) (image.getHeight() * ratio);
            }
            if (smallWidth > 0 || smallHeight > 0) {
                // 生成缩略图
                int status = ImageUploadUtil.scale(localImageUrl, localSmallFile, smallWidth, smallHeight, extName, false);
                // 原图比指定的压缩格式小,直接复制
                if (status == ImageUploadUtil.SCALE_STATUS_SMALLER_THAN_CANVAS) {
                    FileUtils.copyFile(new File(localImageUrl), new File(localSmallFile));
                }
            } else {
                // 如果配置的压缩宽度和压缩高度都为0，则人为的复制一张图片重命名为小图(需求方的意见)
                FileUtils.copyFile(new File(localImageUrl), new File(localSmallFile));
            }
        } catch (MalformedURLException e) {
            log.error("生成略缩图失败###MalformedURLException", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("生成略缩图失败###IOException", e);
            e.printStackTrace();
        }
        log.info("上传图片成功###本地压缩文件地址={}", localSmallFile);
        return true;
    }


    public String getLocalBasePath() {
        assert StringUtils.isNotBlank(fileUploadProperties.getBasePath());
        String basePath = fileUploadProperties.getBasePath();
        File dir = new File(basePath);
        assert dir.isDirectory();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return basePath;
    }

    private String getLocalSubDir(String localParentPath) {
        return this.getLocalSubDir(localParentPath, null);
    }

    private String getLocalSubDir(String localParentPath, String classifySubDir) {
        assert StringUtils.isNotBlank(localParentPath);
        StringBuilder filePath = new StringBuilder();
        String fileFolder = fileUploadProperties.getFileFolder();
        filePath.append(localParentPath.endsWith(File.separator) ? "" : File.separator);
        filePath.append(StringUtils.isNotBlank(fileFolder) ? fileFolder + File.separator : "");
        filePath.append(StringUtils.isNotBlank(classifySubDir) ? classifySubDir + File.separator : "");
        filePath.append(dateFormat.format(new Date()));
        File dir = new File(localParentPath + filePath.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return filePath.toString();
    }

    /**
     * 检查图片大小
     *
     * @return
     */
    public boolean checkImageUploadMaxSize(String base64File) {
        FileUploadProperties.ImageUploadProperties imageConfig = fileUploadProperties.getImage();
        String maxSizeStr = imageConfig.getMaxSize();
        long maxSize = StringUtils.isNoneBlank(maxSizeStr) ? Long.valueOf(maxSizeStr) : 0;
        byte[] imageBytes = Base64.decodeBase64(base64File);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        if (maxSize > 0 && maxSize < bis.available()) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件外网访问地址
     *
     * @param url 相对地址
     */
    private String getExternalUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))
            return url;
        return fileUploadProperties.getBaseUrl() + url;
    }

    /**
     * 保存附件
     *
     * @param fileBytes
     * @param extName
     * @return
     */
    public FileUploadProperties.FileUploadResult saveAttachment(byte[] fileBytes, String extName) {
        return this.saveAttachment((File file) -> FileUtils.copyInputStreamToFile(new ByteArrayInputStream(fileBytes), file), extName, null);
    }

    public FileUploadProperties.FileUploadResult saveAttachment(Collection<?> lines, String extName, String classifySubDir) {
        return this.saveAttachment((File file) -> FileUtils.writeLines(file, "UTF-8", lines), extName, classifySubDir);
    }

    public FileUploadProperties.FileUploadResult saveAttachment(UncheckedConsumer<File> consumer, String extName, String classifySubDir) {
        FileUploadProperties.FileUploadResult file = this.createFile(extName, classifySubDir);

        // 复制文件到目标文件(使用ImageIO处理流)
        try {
            consumer.accept(new File(file.getLocalFile()));
        } catch (Exception ioe) {
            log.error("复制文件失败{}", ioe);
            return null;
        }
        return file;
    }

    public FileUploadProperties.FileUploadResult createFile(String extName, String classifySubDir, String fileName) {
        String realFileName = StringUtils.isBlank(fileName) ? UUID.randomUUID().toString() + "." + extName : fileName;
        String localBaseDir = getLocalBasePath();
        String localSubDir = getLocalSubDir(localBaseDir, classifySubDir);
        String localFile = localBaseDir + localSubDir + File.separator + realFileName;
        String urlPath = this.getExternalUrl(localSubDir.replaceAll("\\\\", "/") + "/" + realFileName);
        FileUploadProperties.FileUploadResult fileUploadResult = new FileUploadProperties.FileUploadResult();
        fileUploadResult.setImageUrl(urlPath);
        fileUploadResult.setLocalFile(localFile);
        fileUploadResult.setLocalBaseDir(localBaseDir);
        fileUploadResult.setLocalSubDir(localSubDir);
        return fileUploadResult;
    }

    public FileUploadProperties.FileUploadResult createFile(String extName) {
        return this.createFile(extName, null, null);
    }

    public FileUploadProperties.FileUploadResult createFile(String extName, String classifySubDir) {
        return this.createFile(extName, classifySubDir, null);
    }

    /**
     * 检查附件大小
     *
     * @param attachmentSize
     * @return
     */
    public boolean checkAttachmentUploadMaxSize(Long attachmentSize) {
        FileUploadProperties.AttachmentUploadProperties attachmentConfig = fileUploadProperties.getAttachment();
        String maxSizeStr = attachmentConfig.getMaxSize();
        long maxSize = StringUtils.isNoneBlank(maxSizeStr) ? Long.valueOf(maxSizeStr) : 0;
        if (maxSize > 0 && maxSize < attachmentSize) {
            return false;
        }
        return true;
    }

    /**
     * 检查附件的扩展名
     *
     * @param attachmentExtName
     * @return
     */
    public boolean checkAttachmentExtNames(String attachmentExtName) {
        FileUploadProperties.AttachmentUploadProperties attachmentConfig = fileUploadProperties.getAttachment();
        List<String> extNames = attachmentConfig.getExtNames();
        if (!extNames.contains(attachmentExtName)) {
            return false;
        }
        return true;
    }

    public String getAttachmentPath(String url) {
        if (url == null) {
            return null;
        }
        if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
            url = url.substring(fileUploadProperties.getBaseUrl().length());
        }
        return fileUploadProperties.getBasePath() + url;
    }

    @FunctionalInterface
    public interface UncheckedConsumer<T> {
        void accept(T t) throws Exception;
    }
}
