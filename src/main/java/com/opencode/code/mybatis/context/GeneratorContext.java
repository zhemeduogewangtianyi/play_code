package com.opencode.code.mybatis.context;

import lombok.Data;

/**
 * 生成器
 */
@Data
public class GeneratorContext {

    /**
     * 生成注释作者的名字
     */
    private String author;
    /**
     * 生成注释的时间
     */
    private String dateFormat = "yyyy-MM-dd";

    /**
     * 指定本地的 mysql jar 包 d:\\jar\\mysql-connector-java-5.1.9.jar
     */
    private String mysqlJarPath;

    /**
     * 数据库连接地址
     */
    private String connectionUrl;
    /**
     * 驱动包名称
     */
    private String driverClass;
    /**
     * db用户名
     */
    private String username;
    /**
     * db密码
     */
    private String password;

    /**
     * 实体类存放位置
     */
    private String entityPackage;
    /**
     * dao 接口存放位置
     */
    private String daoPackage;
    /**
     * mapper.xml 存放位置
     */
    private String mapperPackage;

    /** service 存放位置 */
    private String servicePackage;

    /** serviceImpl 存放位置 */
    private String serviceImplPackage;

    /** controller 存放位置 */
    private String ControllerPackage;

    /**
     * 表名称 tableName
     */
    private String tableName;

    /**
     * 实体类 name
     * */
    private String entityName;

}
