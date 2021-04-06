package com.opencode.code.dubbo.provider;

import com.opencode.code.dubbo.provider.service.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

public class Application {

    public static void main(String[] args) throws Exception {

        ServiceConfig<GreetingService> service = new ServiceConfig<>();

        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-provider");
        service.setApplication(applicationConfig);

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        service.setRegistry(registryConfig);

        service.setInterface(GreetingService.class);

        GreetingServiceImpl greetingService = new GreetingServiceImpl();
        service.setRef(greetingService);
        service.setVersion("1.0.0.daily");
        service.setGroup("DUBBO");
        service.setGeneric("true");

        service.export();

        System.out.println("dubbo service started");

        new CountDownLatch(1).await();

    }


}
