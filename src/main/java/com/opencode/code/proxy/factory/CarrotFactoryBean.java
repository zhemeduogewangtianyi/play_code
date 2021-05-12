package com.opencode.code.proxy.factory;

import com.opencode.code.proxy.interfaces.UserService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class CarrotFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        Class<UserService> cls = UserService.class;
        Object o = Proxy.newProxyInstance(CarrotFactoryBean.class.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                } else {
                    return "代理类，随便干点啥都可以！";
                }
            }
        });
        return o;
    }


    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }
}
