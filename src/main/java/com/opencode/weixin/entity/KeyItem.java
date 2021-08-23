package com.opencode.weixin.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * KeyItem
 */
@Data
public class KeyItem implements Serializable {

    @SerializedName("Key")
    private Integer key;

    @SerializedName("Val")
    private Integer val;
}
