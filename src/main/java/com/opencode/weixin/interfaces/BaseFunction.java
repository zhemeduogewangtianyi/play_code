package com.opencode.weixin.interfaces;

/**
 * 基础方法
 */
public interface BaseFunction {

    /**
     * 获取 UUID
     *
     * @return java.lang.String
     * @date 2021/8/5 4:08 下午
     */
    String getUUID();

    /**
     * 读取二维码图片
     *
     * @param uuid 二维码uuid
     * @param terminalShow 是否在终端显示输出
     */
    void getQrImage(String uuid, boolean terminalShow);


    /**
     * 检查是否登录
     *
     * @param uuid 二维码uuid
     * @return 返回登录状态码
     */
    String checkLogin(String uuid);

    /**
     * 处理登录session
     *
     * @param loginContent 登录text
     * @return 返回是否处理成功
     */
    boolean processLoginSession(String loginContent);

    /**
     * 推送登录
     *
     * @return 返回uuid
     */
    String pushLogin();

}
