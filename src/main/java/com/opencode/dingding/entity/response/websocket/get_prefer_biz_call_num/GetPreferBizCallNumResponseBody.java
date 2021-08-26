package com.opencode.dingding.entity.response.websocket.get_prefer_biz_call_num;

import lombok.Data;

import java.util.List;

@Data
public class GetPreferBizCallNumResponseBody {

    private GetPreferBizCallNumResponseBodyResult result;
    private Boolean isSupport;
    private List<Object> numInfoList;
    private Integer callType;

}
