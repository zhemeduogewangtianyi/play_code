package com.opencode.dingding.interfaces;

import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.http.CheckWebLoginResult;
import com.opencode.dingding.entity.response.http.SetCookieResult;

public interface LoginInterface {

    /** qrCode 认证 */
    String getQrCodeAuth();

    /** 获取二维码 */
    void openQrCode(String qrCodeAuth);

    /** 获取 token */
    String getParamToken();

    /** 检查登录 */
    CheckLoginMessage checkLogin(String qrCodeAuth, String token);

    /** 检查 WEB 登录 */
    CheckWebLoginResult checkWebLogin(String openId);

    /** 设置 COOKIE */
    SetCookieResult setCookie(String tmpCode);

}
