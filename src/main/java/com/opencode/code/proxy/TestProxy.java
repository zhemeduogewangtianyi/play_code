package com.opencode.code.proxy;

import com.opencode.code.proxy.interfaces.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestProxy implements InitializingBean {

    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(userService.query(1L));
    }
}
