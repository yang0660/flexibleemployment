package com.flexibleemployment.utils.file;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 文件媒体类型帮助类
 */
public class FileMediaExtensions {

    private static final Logger logger = LoggerFactory.getLogger(FileMediaExtensions.class);

    private static final HashMap<String, String> MEDIA_TYPES = new HashMap<>();

    static {
        MEDIA_TYPES.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
        MEDIA_TYPES.put("ffd8ffe1009c45786966", "jpg"); //JPEG (jpg)
        MEDIA_TYPES.put("ffd8ffe11d8545786966", "jpg"); //JPEG (jpg)
        MEDIA_TYPES.put("ffd8ffe123ce45786966", "jpg"); //JPEG (jpg)
        MEDIA_TYPES.put("89504e470d0a1a0a0000", "png"); //PNG (png)
        MEDIA_TYPES.put("47494638396126026f01", "gif"); //GIF (gif)
        MEDIA_TYPES.put("474946383961c8009a00", "gif"); //GIF (gif)
        MEDIA_TYPES.put("474946383961b8010001", "gif"); //GIF (gif)
        MEDIA_TYPES.put("49492a00227105008037", "tif"); //TIFF (tif)
        MEDIA_TYPES.put("424d228c010000000000", "bmp"); //16色位图(bmp)
        MEDIA_TYPES.put("424d8240090000000000", "bmp"); //24位位图(bmp)
        MEDIA_TYPES.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)
        MEDIA_TYPES.put("41433130313500000000", "dwg"); //CAD (dwg)
        MEDIA_TYPES.put("3c21444f435459504520", "html"); //HTML (html)
        MEDIA_TYPES.put("3c21646f637479706520", "htm"); //HTM (htm)
        MEDIA_TYPES.put("48544d4c207b0d0a0942", "css"); //css
        MEDIA_TYPES.put("696b2e71623d696b2e71", "js"); //js
        MEDIA_TYPES.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)
        MEDIA_TYPES.put("38425053000100000000", "psd"); //Photoshop (psd)
        MEDIA_TYPES.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)
        MEDIA_TYPES.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
        MEDIA_TYPES.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图
        MEDIA_TYPES.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
        MEDIA_TYPES.put("252150532D41646F6265", "ps");
        MEDIA_TYPES.put("255044462d312e350d0a", "pdf"); //Adobe Acrobat (pdf)
        MEDIA_TYPES.put("255044462d312e350d25", "pdf"); //Adobe Acrobat (pdf)
        MEDIA_TYPES.put("255044462d312e340d25", "pdf"); //Adobe Acrobat (pdf)
        MEDIA_TYPES.put("255044462d312e", "pdf"); //Adobe Acrobat (pdf)
        MEDIA_TYPES.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同
        MEDIA_TYPES.put("464c5601050000000900", "flv"); //flv与f4v相同
        MEDIA_TYPES.put("00000020667479706d70", "mp4");
        MEDIA_TYPES.put("49443303000000002176", "mp3");
        MEDIA_TYPES.put("000001ba210001000180", "mpg"); //
        MEDIA_TYPES.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同
        MEDIA_TYPES.put("52494646e27807005741", "wav"); //Wave (wav)
        MEDIA_TYPES.put("52494646d07d60074156", "avi");
        MEDIA_TYPES.put("4f676753000200000000", "spx");
        MEDIA_TYPES.put("4d546864000000060001", "mid"); //MIDI (mid)
        MEDIA_TYPES.put("504b0304140000000800", "zip");
        MEDIA_TYPES.put("504b0304140008000800", "zip");
        MEDIA_TYPES.put("526172211a0700cf9073", "rar");
        MEDIA_TYPES.put("235468697320636f6e66", "ini");
        MEDIA_TYPES.put("504b03040a0000000000", "jar");
        MEDIA_TYPES.put("4d5a9000030000000400", "exe");//可执行文件
        MEDIA_TYPES.put("3c25402070616765206c", "jsp");//jsp文件
        MEDIA_TYPES.put("4d616e69666573742d56", "mf");//MF文件
        MEDIA_TYPES.put("3c3f786d6c2076657273", "xml");//xml文件
        MEDIA_TYPES.put("494e5345525420494e54", "sql");//xml文件
        MEDIA_TYPES.put("7061636b616765207765", "java");//java文件
        MEDIA_TYPES.put("406563686f206f66660d", "bat");//bat文件
        MEDIA_TYPES.put("1f8b0800000000000000", "gz");//gz文件
        MEDIA_TYPES.put("6c6f67346a2e726f6f74", "properties");//bat文件
        MEDIA_TYPES.put("cafebabe0000002e0041", "class");//bat文件
        MEDIA_TYPES.put("49545346030000006000", "chm");//bat文件
        MEDIA_TYPES.put("04000000010000001300", "mxp");//bat文件
        MEDIA_TYPES.put("504b0304140006000800", "docx");//docx文件
        MEDIA_TYPES.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
        MEDIA_TYPES.put("6431303a637265617465", "torrent");
        MEDIA_TYPES.put("FF575043", "wpd"); //WordPerfect (wpd)
        MEDIA_TYPES.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
        MEDIA_TYPES.put("2142444E", "pst"); //Outlook (pst)
        MEDIA_TYPES.put("AC9EBD8F", "qdf"); //Quicken (qdf)
        MEDIA_TYPES.put("E3828596", "pwl"); //Windows Password (pwl)
        MEDIA_TYPES.put("2E7261FD", "ram"); //Real Audio (ram)
    }

    public static String parseMediaExtension(File file) {
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            return parseMediaExtension(bytes);
        } catch (IOException e) {
            logger.error("读取文件失败, {}", e.getMessage());
        }
        return "";
    }

    public static String parseMediaExtension(String base64) {
        return parseMediaExtension(Base64.decodeBase64(base64));
    }

    public static String parseMediaExtension(byte[] fileBytes) {
        byte[] headBytes = Arrays.copyOfRange(fileBytes, 0, 10);
        String headCode = bytesToHexString(headBytes);
        Iterator<String> keyIter = MEDIA_TYPES.keySet().iterator();
        String extension = "";
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            if (key.toLowerCase().startsWith(headCode.toLowerCase()) || headCode.toLowerCase().startsWith(key.toLowerCase())) {
                extension = MEDIA_TYPES.get(key);
                break;
            }
        }
        return extension;
    }

    private static String bytesToHexString(byte[] input) {
        if (input == null || input.length <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            int v = input[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
