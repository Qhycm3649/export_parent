package com.xyou.service.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//企业提供者
public class CompanyProvider {

    public static void main(String[] args) throws IOException {
        //1 加载核心配置文件
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        //2. 启动
        context.start();
        //3. 使用一个阻塞型参数不要停止服务
        System.in.read();
    }
}
