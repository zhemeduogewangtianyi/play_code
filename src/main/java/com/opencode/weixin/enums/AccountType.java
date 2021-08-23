package com.opencode.weixin.enums;

import lombok.Getter;

/**
 * 账号类型
 *
 */
@Getter
public enum AccountType {

    /**
     * 公众号/服务号
     */
    TYPE_MP,
    /**
     * 特殊账号
     */
    TYPE_SPECIAL,
    /**
     * 群组
     */
    TYPE_GROUP,
    /**
     * 好友
     */
    TYPE_FRIEND

}
