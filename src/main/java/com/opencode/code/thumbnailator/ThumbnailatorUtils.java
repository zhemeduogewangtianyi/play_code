package com.opencode.code.thumbnailator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ThumbnailatorUtils {

    public static void main(String[] args) throws IOException {
        byte[] data = new ThumbnailatorUtils().processor();
        File file = new File("d://test.jpg");
        OutputStream os = new FileOutputStream(file);
        os.write(data);
    }

    /** 合成 */
    public byte[] processor() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

        BufferedImage rqCode = generatorRqCode();

        String content = "锤子就是这么牛B";

        BufferedImage font = generatorFont(content);

        Thumbnails.of(new URL("http://img9.zol.com.cn/pic/youxi/800_11/118498.jpg"))
                .size(750, 563)
                .outputFormat("jpg")
                .watermark((enclosingWidth, enclosingHeight, width, height, insetLeft, insetRight, insetTop, insetBottom) -> {
                    int x1 = (enclosingWidth / 2) - (width / 2);
                    int y1 = 400;
                    return new Point(x1, y1);
                }, rqCode, 1.0f)
                .watermark((enclosingWidth, enclosingHeight, width, height, insetLeft, insetRight, insetTop, insetBottom) -> {
                    int x1 = (enclosingWidth / 2) - (width / 2) + 51;
                    int y1 = 20;
                    return new Point(x1, y1);
                }, font, 1.0f).toOutputStream(baos);

        return baos.toByteArray();
    }

    /**
     * 二维码
     * @author    WTY
     * @date    2020/12/14 18:29
     * @return  java.awt.image.BufferedImage
     */
    public BufferedImage generatorRqCode() {
        try {
            Map<EncodeHintType, String> settingMap = new HashMap<>(1 << 4);
            //设置编码 EncodeHintType类中可以设置MAX_SIZE， ERROR_CORRECTION，CHARACTER_SET，DATA_MATRIX_SHAPE，AZTEC_LAYERS等参数
            settingMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            settingMap.put(EncodeHintType.MARGIN, "2");

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode("http://www.baidu.com", BarcodeFormat.QR_CODE, 60, 60, settingMap);

            return MatrixToImageWriter.toBufferedImage(bitMatrix);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字体生成
     * @author    WTY
     * @date    2020/12/14 18:29
     * @param content
     * @return  java.awt.image.BufferedImage
     */
    public BufferedImage generatorFont(String content){

        Font font = new Font("微软雅黑", Font.PLAIN, 12);

        BufferedImage image = new BufferedImage(220, 80, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(220, 80, Transparency.TRANSLUCENT);

        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(font);

        g.drawString(content, 30, 30);

        g.dispose();

        return image;
    }

}
