package com.flexibleemployment.utils.file;


import com.flexibleemployment.configuration.properties.FileUploadProperties;
import com.flexibleemployment.utils.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
@Slf4j
public class FileUploadHandle {

    @Autowired
    private FileUploadProperties fileUploadProperties;

    public String upload(byte[] buffer, String fileName) {
        return upload(new ByteArrayInputStream(buffer), fileName);
    }

    public String upload(InputStream in, String fileName) {
//        fileName = URLEncoder.encode(fileName, "UTF-8");  外部自己去encode, 如果有乱码的话， 没有就无所谓

        File dir = new File(toURI(getPath()));
        if(! dir.exists()) {
            dir.mkdirs();
        }

        File targetFile = autoIncrementTargetFile(dir, fileName);

        try {
            FileUtils.copyInputStreamToFile(in, targetFile);
        } catch (IOException e) {
            throw new BizException("上传文件失败", e);
        }
        log.info("成功上传文件到{}", targetFile.getAbsolutePath());

        return fileUploadProperties.getBaseUrl() + getPath() + "/" + targetFile.getName();
    }

    private Pattern INCREMENT_FILE_NAME_PATTERN = Pattern.compile("^(.*)\\((\\d+)\\)$");
    private File autoIncrementTargetFile(File dir, String fileName) {
        int lastDotIndexOf = fileName.lastIndexOf(".");
        String frontName = fileName;
        String extName = "";
        if(lastDotIndexOf != -1) {
            frontName = fileName.substring(0, lastDotIndexOf);
            extName = "." + fileName.substring(lastDotIndexOf + 1);
        }

        File targetFile = new File(dir, System.nanoTime() + extName);
        /*for(int i = 1; ; i ++) {
            Matcher matcher = INCREMENT_FILE_NAME_PATTERN.matcher(frontName);
            if(targetFile.exists()) {
                if(matcher.matches()) {
                    frontName = matcher.group(1);
                    i = Integer.parseInt(matcher.group(2)) + 1;
                }
                frontName = String.format("%s(%d)", frontName, i);
                targetFile = new File(dir, frontName + extName);
            } else {
                break;
            }
        }*/
        return targetFile;
    }

    private String getPath() {
        return FileUploadProperties.CONTEXT_PATH + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public URI toURI(String path) {
        if(path.startsWith("http://") || path.startsWith("https://")) {
            try {
                return new URI(path);
            } catch (URISyntaxException e) {
                log.error("路径" + path + "解析成URI异常", e);
            }
        }
        return new File(fileUploadProperties.getBasePath() + (path.startsWith("/") ? path : "/" + path)).toURI();
    }

    public String trimBase64(String base64) {
        if(StringUtils.isNotBlank(base64)){
            int indexOf = base64.indexOf(";base64,");
            if (indexOf != -1) {
                indexOf += 8;
                base64 = base64.substring(indexOf);
            }
        }
        return base64;
    }

    public String uploadBase64(String base) {
        String signUrl = "";
        if(StringUtils.isNotBlank(base)){
            byte[] con = Base64.decode(trimBase64(base));
            signUrl = upload(con, UUID.randomUUID().toString()+".png");
        }
        return signUrl;
    }
}
