package com.opencode.code.common;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Document(indexName = "student",shards = 1,replicas = 1)
public class Student implements Serializable {

    /** ID */
    @Id
    private String id;

    /** 名字 */
    @Field(type = FieldType.Text)
    private String name;

    /** 性别 */
    @Field(type = FieldType.Keyword)
    private char gender;

    /** 年龄 */
    @Field(type = FieldType.Byte)
    private Byte age;

    /** 编号 */
    @Field(type = FieldType.Integer)
    private Integer number;

    /** 最高分 */
    @Field(type = FieldType.Short)
    private Short maxScore;

    /** 生日 */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthDay;

    /** 资产 */
    @Field(type = FieldType.Double)
    private Double salary;

    /** 身高 */
    @Field(type = FieldType.Float)
    private Float height;

    /** 体重 */
    @Field(type = FieldType.Float)
    private Float bodyWeight;

    /** 是否愚蠢 */
    @Field(type = FieldType.Boolean)
    private Boolean isFool;

}
