package com.opencode.code.mybatis.context;

import lombok.Data;

import java.io.File;

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
     * DO 实体类存放位置
     */
    private String doPackage;

    /**
     * param 实体类存放位置
     */
    private String paramPackage;

    /**
     * VO 实体类存放位置
     */
    private String voPackage;
    /**
     * dao 接口存放位置
     */
    private String daoPackage;
    /**
     * mapper.xml 存放位置
     */
    private String mapperPackage;

    /**
     * service 存放位置
     */
    private String servicePackage;

    /**
     * serviceImpl 存放位置
     */
    private String serviceImplPackage;

    /**
     * controller 存放位置
     */
    private String ControllerPackage;

    /**
     * 表名称 tableName
     */
    private String tableName;

    /**
     * 实体类 name
     */
    private String entityName;

    /**
     * 指定 数据库表 的主键字段
     */
    private String primaryKey;

    /**
     * 目标项目地址 DO VO Param Dao Service ServiceImpl Controller 的位置
     * */
    private String targetProject = "src" + File.separator + "main" + File.separator + "java";

    /**
     * 目标资源地址 .xml 的位置
     * */
    private String targetResources = "src" + File.separator + "main" + File.separator + "resources";

}
