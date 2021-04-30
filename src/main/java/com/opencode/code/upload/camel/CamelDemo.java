package com.opencode.code.upload.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.RandomAccessFile;

/**
 * 服务器之间的文件同步
 */
public class CamelDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelDemo.class);

    public static void main(String[] args) throws Exception {

        RouteBuilder routeBuilder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String uri = "sftp://sftpuser@11.164.25.64/?password=sftpuser&readLock=rename&delay=10s&binary=true&filter=#zipFileFilter&noop=true&recursive=true";
                String uri1 = "sftp://sftpuser@11.164.25.64?password=sftpuser";
                from(uri).to(uri1)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        GenericFileMessage<RandomAccessFile> inFileMessage = (GenericFileMessage<RandomAccessFile>) exchange.getIn();
                        String fileName = inFileMessage.getGenericFile().getFileName();
                        String file_path = "/var/data" + '/' + fileName;
                        System.out.println("111");
//                        readZip(file_path);
                    }
                })
                .log(LoggingLevel.INFO, LOGGER, "Download file ${file:name} complete.");
            }
        };

        Main main = new Main();
        main.addRouteBuilder(routeBuilder);
        main.run();

    }

}
