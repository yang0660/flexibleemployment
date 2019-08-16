package com.flexibleemployment.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author zhangyuandi
 */
public class ImageUploadUtil {

    private static Logger log = LoggerFactory.getLogger(ImageUploadUtil.class);

    /**
     * 支持的图片格式：JPG
     */
    public static final String IMAGE_TYPE_JPG = "jpg";

    /**
     * 支持的图片格式：GIF
     */
    public static final String IMAGE_TYPE_GIF = "gif";

    /**
     * 支持的图片格式：PNG
     */
    public static final String IMAGE_TYPE_PNG = "png";

    /**
     * 支持的图片格式：TIFF
     */
    public static final String IMAGE_TYPE_TIFF = "tif";

    /**
     * 支持的图片格式：BMP
     */
    public static final String IMAGE_TYPE_BMP = "bmp";

    /**
     * 压缩标识：成功
     */
    public static final int SCALE_STATUS_OK = 0;

    /**
     * 压缩标识：不支持的图片格式
     */
    public static final int SCALE_STATUS_NOT_SUPPORT = 1;

    /**
     * 压缩标识：原图不存在
     */
    public static final int SCALE_STATUS_NOT_FOUND = 2;

    /**
     * 压缩标识：原图比指定的压缩格式小
     */
    public static final int SCALE_STATUS_SMALLER_THAN_CANVAS = 3;

    /**
     * 压缩标识：压缩失败
     */
    public static final int SCALE_STATUS_ERROR = 4;

    /**
     * 压缩规格：小图
     */
    public static final String SCALE_FROMAT_SMALL = "s";

    /**
     * 压缩规格：中图
     */
    public static final String SCALE_FROMAT_MIDDLE = "m";

    /**
     * 压缩规格：大图
     */
    public static final String SCALE_FROMAT_BIG = "b";

    /**
     * 字节换算
     */
    public static final double BYTES_CONVERSION = 1024;

    /**
     * 前端规定高度
     */
    public static final double SMALL_WIDTH = 180;

    /**
     * 图片限制大小
     */
    public static final double PICTURE_LIMITED_SIZE = 10;


    /**
     * 图片缩放
     *
     * @param srcPath        源文件路径
     * @param destPath       目标文件路径
     * @param canvasWidth    画布宽度
     * @param canvasHeight   画布高度
     * @param type           图片类型
     * @param scallIfSmaller 如果原图的宽度和高度均比画布的宽度和高度小时候要拉伸
     * @throws IOException
     */
    public static int scale(String srcPath, String destPath, int canvasWidth, int canvasHeight, String type, boolean scallIfSmaller) throws IOException {
        try {
            if (!checkType(type)) return SCALE_STATUS_NOT_SUPPORT;
            BufferedImage src = ImageIO.read(new File(srcPath));
            if (src == null) return SCALE_STATUS_NOT_FOUND;
            int imageWidth = src.getWidth();
            int imageHeight = src.getHeight();
            if (!scallIfSmaller && (canvasWidth == 0 || imageWidth <= canvasWidth) && (canvasHeight == 0 || imageHeight <= canvasHeight)) {
                return SCALE_STATUS_SMALLER_THAN_CANVAS;
            }
            double ratio = getScaleRate(canvasWidth, canvasHeight, imageWidth, imageWidth);
            if (canvasWidth <= 0) {
                canvasWidth = (int) (ratio * imageWidth);
            }
            if (canvasHeight <= 0) {
                canvasHeight = (int) (ratio * imageHeight);
            }
            if (ratio > 0) {
                BufferedImage bfImage = new BufferedImage(canvasWidth, canvasHeight,
                        IMAGE_TYPE_PNG.equals(type) ? BufferedImage.TYPE_4BYTE_ABGR : BufferedImage.TYPE_INT_RGB);
                bfImage.getGraphics().drawImage(src.getScaledInstance(canvasWidth, canvasHeight, Image.SCALE_SMOOTH), 0, 0, null);
                ImageIO.write(bfImage, type, new File(destPath));
                return SCALE_STATUS_OK;
            }
        } catch (IOException e) {
            log.error("图片 " + srcPath + " 压缩失败", e.getMessage());
        }
        return SCALE_STATUS_ERROR;
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param srcPath  源图像文件地址
     * @param destPath 缩放后的图像地址
     * @param ratio    缩放比例
     */
    public final static void scale(String srcPath, String destPath, double ratio, String type) {
        try {
            BufferedImage src = ImageIO.read(new File(srcPath));
            int imageWidth = src.getWidth();
            int imageHeight = src.getHeight();
            if (ratio > 0 && imageWidth > 0 && imageHeight > 0) {
                int canvasWidth = (int) (imageWidth * ratio);
                int canvasHeight = (int) (imageHeight * ratio);
                BufferedImage bfImage = new BufferedImage(canvasWidth, canvasHeight,
                        IMAGE_TYPE_PNG.equals(type) ? BufferedImage.TYPE_4BYTE_ABGR : BufferedImage.TYPE_INT_RGB);
                bfImage.getGraphics().drawImage(src.getScaledInstance(canvasWidth, canvasHeight, Image.SCALE_SMOOTH), 0, 0, null);
                ImageIO.write(bfImage, type, new File(destPath));
            }
        } catch (IOException e) {
            log.error("按比例缩放图片失败", e.getMessage());
        }
    }

    private static double getScaleRate(int canvasWidth, int canvasHeight, int imageWidth, int imageHeight) {
        double ratio = 0;
        if (imageHeight <= 0 || imageWidth <= 0 || canvasWidth <= 0 && canvasHeight <= 0) return 0;
        if (canvasWidth <= 0) {
            ratio = canvasHeight * 1.0 / imageHeight;
        } else if (canvasHeight <= 0) {
            ratio = canvasWidth * 1.0 / imageWidth;
        } else {
            ratio = Math.min(canvasWidth * 1.0 / imageWidth, canvasHeight * 1.0 / imageHeight);
        }
        return ratio;
    }

    /**
     * 检查图片类型
     *
     * @param type
     * @return
     */
    public static boolean checkType(String type) {
        return IMAGE_TYPE_JPG.equals(type) || IMAGE_TYPE_GIF.equals(type) || IMAGE_TYPE_PNG.equals(type)
                || IMAGE_TYPE_TIFF.equals(type) || IMAGE_TYPE_BMP.equals(type);
    }


}
