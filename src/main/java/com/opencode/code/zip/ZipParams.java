package com.opencode.code.zip;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 参数实体
 * @author  WTY
 * @date    2020/7/16 9:37
 */
@Data
public class ZipParams {

    /** 密码 */
    private String password;

    /** 文件数据配置 */
    private List<InnerParam> innerParams;

    public ZipParams(){
        innerParams = new ArrayList<>();
    }

    public ZipParams(String password) {
        this.password = password;
        innerParams = new ArrayList<>();
    }

    public ZipParams(String password, List<InnerParam> innerParams) {
        this.password = password;
        this.innerParams = innerParams;
    }

    public void addParam(InnerParam... innerParam){
        if(CollectionUtils.isEmpty(this.innerParams)){
           this.innerParams = new ArrayList<>();
        }
        Collections.addAll(this.innerParams,innerParam);
    }

    @Data
    public static class InnerParam{

        /** 要压缩的数据 */
        private byte[] data;

        /** 文件名 */
        private String fileName;

        /** 是否需要 密码 */
        private boolean needPass;

        public InnerParam(String fileName,byte[] data) {
            this.fileName = fileName;
            this.data = data;
            this.needPass = false;
        }

        public InnerParam(String fileName,byte[] data, boolean needPass) {
            this.fileName = fileName;
            this.data = data;
            this.needPass = needPass;
        }
    }

}
