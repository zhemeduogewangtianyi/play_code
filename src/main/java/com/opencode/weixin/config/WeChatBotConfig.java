package com.opencode.weixin.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * 微信API配置
 *
 * @author biezhi
 * @date 2018/1/18
 */
public class WeChatBotConfig {

    public static final WeChatBotConfig INSTANCE = new WeChatBotConfig();

    private WeChatBotConfig(){

    }

    /**
     * 资源存储的文件夹，包括图片、视频、音频
     */
    private static final String CONF_ASSETS_DIR         = "wechat.assets-path";
    private static final String CONF_ASSETS_DIR_DEFAULT = System.getProperty("user.dir") + "/assets";

    /**
     * 是否输出二维码到终端
     */
    private static final String CONF_PRINT_TERMINAL         = "wechat.print-terminal";
    private static final String CONF_PRINT_TERMINAL_DEFAULT = "false";

    /**
     * 自动回复消息，测试时用
     */
    private static final String CONF_AUTO_REPLY         = "wechat.auto-reply";
    private static final String CONF_AUTO_REPLY_DEFAULT = "false";

    /**
     * 自动登录
     */
    private static final String CONF_AUTO_LOGIN         = "wechat.auto-login";
    private static final String CONF_AUTO_LOGIN_DEFAULT = "false";

    /**
     * 自动添加好友请求
     */
    private static final String CONF_AUTO_ADDFRIEND         = "wechat.auto-addfriend";
    private static final String CONF_AUTO_ADDFRIEND_DEFAULT = "false";

    private Properties props = new Properties();

    public static WeChatBotConfig getInstance() {
        return INSTANCE;
    }

    /**
     * 加载 ClassPath 下的配置文件
     *
     * @param filePath
     * @return
     */
    public static WeChatBotConfig load(String filePath) {
        WeChatBotConfig config = new WeChatBotConfig();
        try (final InputStream stream = WeChatBotConfig.class.getResourceAsStream(filePath)) {
            config.props.load(stream);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件出错", e);
        }
        return config;
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public String assetsDir() {
        return props.getProperty(CONF_ASSETS_DIR, CONF_ASSETS_DIR_DEFAULT);
    }

    public WeChatBotConfig assetsDir(String dir) {
        props.setProperty(CONF_ASSETS_DIR, dir);
        return this;
    }

    public boolean showTerminal() {
        return Boolean.valueOf(props.getProperty(CONF_PRINT_TERMINAL, CONF_PRINT_TERMINAL_DEFAULT));
    }

    public WeChatBotConfig showTerminal(boolean show) {
        props.setProperty(CONF_PRINT_TERMINAL, String.valueOf(show));
        return this;
    }

    public boolean autoReply() {
        return Boolean.valueOf(props.getProperty(CONF_AUTO_REPLY, CONF_AUTO_REPLY_DEFAULT));
    }

    public WeChatBotConfig autoReply(boolean autoReply) {
        props.setProperty(CONF_AUTO_REPLY, String.valueOf(autoReply));
        return this;
    }

    public WeChatBotConfig autoLogin(boolean autoLogin) {
        props.setProperty(CONF_AUTO_LOGIN, String.valueOf(autoLogin));
        return this;
    }

    public WeChatBotConfig autoAddFriend(boolean autoAddFriend) {
        props.setProperty(CONF_AUTO_ADDFRIEND, String.valueOf(autoAddFriend));
        return this;
    }

    public boolean autoAddFriend() {
        return Boolean.valueOf(props.getProperty(CONF_AUTO_ADDFRIEND, CONF_AUTO_ADDFRIEND_DEFAULT));
    }

    public boolean autoLogin() {
        return Boolean.valueOf(props.getProperty(CONF_AUTO_LOGIN, CONF_AUTO_LOGIN_DEFAULT));
    }

}
