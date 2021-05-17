package com.opencode.code.zip;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 新 ziputil 支持直接内存多个文件打包为zip
 * @author  WTY
 * @date    2020/7/15 17:48
 */
@SuppressWarnings("Duplicates")
public class ZipUtil extends CompressUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 将内存文件写入zip内。注意：最后必须调用closeZipOutputStream关闭输出流，或者手动关闭
     *
     * @author  WTY
     * @date    2020/7/15 17:55
     * @param fileName 文件名
     * @param data 文件数据
     * @param password 密码
     * @param zipOutputStream 流
     */
    public static void addFileToZip(String fileName, byte[] data, String password, ZipOutputStream zipOutputStream)
        throws ZipException, IOException {

        if (StringUtils.isEmpty(fileName) || data == null || data.length == 0 || zipOutputStream == null) {
            throw new ZipException(new StringBuilder("参数异常,fileName=").append(fileName).append(",data=").append(data)
                .append(",zipOutputStream=").append(zipOutputStream).toString());
        }

        ZipParameters zipParameters = new ZipParameters();
        // 压缩方式
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        zipParameters.setFileNameInZip(fileName);

        if (StringUtils.isNotBlank(password)) {
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParameters.setPassword(password.toCharArray());
        }

        // 源文件是否为外部流，true表示使用内存文件而非本地存储文件
        zipParameters.setSourceExternalStream(true);

        zipOutputStream.putNextEntry(null, zipParameters);
        zipOutputStream.write(data);
        zipOutputStream.closeEntry();
    }

    /**
     * 将内存文件写入zip内。注意：最后必须调用closeZipOutputStream关闭输出流，或者手动关闭
     * @author  WTY
     * @date    2020/7/15 17:56
     * @param fileName 文件名
     * @param data 数据
     * @param zipOutputStream 流
     */
    public static void addFileToZip(String fileName, byte[] data, ZipOutputStream zipOutputStream)
        throws ZipException, IOException {
        addFileToZip(fileName, data, null, zipOutputStream);
    }

    /**
     * 将内存文件写入zip内。注意：最后必须调用closeZipOutputStream关闭输出流，或者手动关闭
     * @author  WTY
     * @date    2020/7/15 17:57
     * @param zipParameters zip参数
     * @param data 文件数据
     * @param zipOutputStream 输出流
     */
    public static void addFileToZip(ZipParameters zipParameters, byte[] data, ZipOutputStream zipOutputStream)
        throws ZipException, IOException {

        if (zipParameters == null || data == null || data.length == 0 || zipOutputStream == null) {
            throw new ZipException(new StringBuilder("参数异常,zipParameters=").append(zipParameters).append(",data=")
                .append(data).append(",zipOutputStream=").append(zipOutputStream).toString());
        }
        zipOutputStream.putNextEntry(null, zipParameters);
        zipOutputStream.write(data);
        zipOutputStream.closeEntry();
    }

    /**
     * 关闭流
     * @author  WTY
     * @date    2020/7/15 17:57
     * @param zipOutputStream 输出流
     */
    public static void closeZipOutputStream(ZipOutputStream zipOutputStream) throws IOException, ZipException {
        if (zipOutputStream == null) {
            return;
        }
        zipOutputStream.finish();
        zipOutputStream.close();
    }

    /**
     * 多个文件打包为一个zip文件
     * @author  WTY
     * @date    2020/7/15 17:58
     * @param params 带文件们
     * @return  byte[] 字节
     */
    public static byte[] addToZip(ZipParams params) throws IOException, ZipException {
        if(params == null){
            return null;
        }
        List<ZipParams.InnerParam> dataArray = params.getInnerParams();
        if(CollectionUtils.isEmpty(dataArray)){
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        String password = params.getPassword();

        for(ZipParams.InnerParam innerParam : dataArray){
            byte[] data = innerParam.getData();
            String fileName = innerParam.getFileName();

            if(innerParam.isNeedPass() && StringUtils.isNotBlank(password)){
                //带密码
                addFileToZip(fileName,data,password,zipOutputStream);
            }else{
                //不带密码
                addFileToZip(fileName,data,zipOutputStream);
            }
        }

        closeZipOutputStream(zipOutputStream);

        byte[] zipData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        LOGGER.info("fileSize : {}",zipData.length);
        return zipData;
    }

    /**
     * 可用于测试输出
     * @author  WTY
     * @date    2020/7/16 14:50
     * @param bs 数据字节
     * @param fileName 路径名称
     */
    public static void writeBytesToFile(byte[] bs,String fileName) throws IOException{
        UUID uuid = UUID.randomUUID();
        OutputStream out = new FileOutputStream(fileName);
        InputStream is = new ByteArrayInputStream(bs);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }

     public static void main(String[] args) throws Exception {

        ZipParams.InnerParam innerParam = new ZipParams.InnerParam("哈哈哈1.txt", "11111112222222".getBytes());
        ZipParams.InnerParam innerParam1 = new ZipParams.InnerParam("哈哈哈2.txt", "aa11111112222222".getBytes());
        ZipParams.InnerParam innerParam2 = new ZipParams.InnerParam("哈哈哈3.txt", "111奥术大师大所多11112222222".getBytes(),true);
        ZipParams.InnerParam innerParam3 = new ZipParams.InnerParam("哈哈哈4.txt", "1111111222222水电费大师傅第三方AAAAAAAAA2".getBytes(),true);
        ZipParams params = new ZipParams("123456");
        params.addParam(innerParam,innerParam1,innerParam2,innerParam3);

        byte[] zipData = addToZip(params);

        new FileOutputStream("D:\\n.zip").write(zipData);
     }

}
