package com.opencode.weixin.api;

import com.opencode.weixin.entity.Account;
import lombok.Getter;

import java.util.*;

public class WeChatAccountBaseInfo {

    /**
     * 所有账号
     */
    @Getter
    protected Map<String, Account> accountMap = new HashMap<>();

    /**
     * 特殊账号
     */
    @Getter
    protected List<Account> specialUsersList = new ArrayList<>();

    /**
     * 公众号、服务号
     */
    @Getter
    protected List<Account> publicUsersList = new ArrayList<>();

    /**
     * 好友列表
     */
    @Getter
    protected List<Account> contactList = new ArrayList<>();

    /**
     * 群组
     */
    @Getter
    protected List<Account> groupList = new ArrayList<>();

    /**
     * 群UserName列表
     */
    protected Set<String> groupUserNames = new HashSet<>();
    
}
