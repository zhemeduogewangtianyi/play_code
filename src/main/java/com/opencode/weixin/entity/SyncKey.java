package com.opencode.weixin.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SyncKey
 */
@Data
public class SyncKey implements Serializable {

    @SerializedName("Count")
    private Integer count;

    @SerializedName("List")
    private List<KeyItem> list;

}
