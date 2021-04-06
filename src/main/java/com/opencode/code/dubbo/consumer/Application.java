package com.opencode.code.dubbo.consumer;

import com.opencode.code.dubbo.provider.service.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class Application {

    public static void main(String[] args) {

        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();

        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-consumer");
        reference.setApplication(applicationConfig);

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        reference.setRegistry(registryConfig);

        reference.setInterface(GreetingService.class);

        GreetingService service = reference.get();

        String message = service.sayHi("dubbo");
        System.out.println(message);
    }

}
