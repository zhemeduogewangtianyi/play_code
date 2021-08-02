package com.opencode.code.read;

import com.carrot.sec.annotation.CSearchAdd;
import com.carrot.sec.annotation.CSearchQuery;
import com.carrot.sec.enums.NewCFieldTypeEnum;
import com.carrot.sec.enums.OccurEnum;
import lombok.Data;

@Data
public class XiYouZhaoJiuWanWu {

    @CSearchAdd(enums = NewCFieldTypeEnum.STRING_FIELD,analyzer = true)
    @CSearchQuery(enums = NewCFieldTypeEnum.STRING_FIELD,occur = OccurEnum.Occur.SHOULD,updateFlag = true)
    private String id;

    @CSearchAdd(enums = NewCFieldTypeEnum.STRING_FIELD)
    @CSearchQuery(enums = NewCFieldTypeEnum.STRING_FIELD,occur = OccurEnum.Occur.MUST)
    private String title;

    @CSearchAdd(enums = NewCFieldTypeEnum.TEXT_FIELD,analyzer = true)
    @CSearchQuery(enums = NewCFieldTypeEnum.TEXT_FIELD,occur = OccurEnum.Occur.SHOULD)
    private String content;

    @CSearchAdd(enums = NewCFieldTypeEnum.INT_POINT)
    @CSearchQuery(enums = NewCFieldTypeEnum.INT_POINT,occur = OccurEnum.Occur.MUST)
    private Integer lineNum;

    @CSearchAdd(enums = NewCFieldTypeEnum.STRING_FIELD)
    @CSearchQuery(enums = NewCFieldTypeEnum.STRING_FIELD,occur = OccurEnum.Occur.MUST)
    private String url;

    @CSearchAdd(enums = NewCFieldTypeEnum.STRING_FIELD)
    @CSearchQuery(enums = NewCFieldTypeEnum.STRING_FIELD,occur = OccurEnum.Occur.MUST)
    private String nextPage;

    @CSearchAdd(enums = NewCFieldTypeEnum.LONG_POINT)
    @CSearchQuery(enums = NewCFieldTypeEnum.LONG_POINT,occur = OccurEnum.Occur.MUST,sort = true)
    private Long timestamp;

}
