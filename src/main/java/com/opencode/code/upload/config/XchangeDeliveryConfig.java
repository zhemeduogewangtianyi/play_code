package com.opencode.code.upload.config;

import lombok.Data;

@Data
public class XchangeDeliveryConfig {

    /** id */
    private Long deliveryId;

    /** 上下文版本号 */
    private Long version;

    /** ftp地址 */
    private String host;

    /** 端口号 */
    private Integer port = 21;

    /** 登录用户 */
    private String username;

    /** 登录密码 */
    private String password;

    /** 被动模式 */
    private boolean passiveMode = false;

    /** 编码 */
    private String encoding = "UTF-8";

    /** 连接超时时间(毫秒) */
    private Integer connectTimeout = 1000 * 60;

    /** 缓冲大小 */
    private Integer bufferSize = 1024;

    /** 传输文件类型 */
    private Integer transferFileType = 2;

    /** 目录 */
    private String changeDir;

    /** 连接个数 */
    private Integer poolSize = 8;

    /** 监控开关 0：关闭 1：开启  */
    private Integer monitorSwitch;

    /** 检查间隔时间 分钟 */
    private Long checkFrequency;

    /** 投递监控类型，0：ping 1：httpHealthcheck 2：ftp/sftp文件上传 3：sftp/sftp文件下载 */
    private String deliverMonitorType;

    /** 0-ftp 1-sftp 2-http 3-https */
    private Integer protocol;

}
