package com.xyou.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StatProvider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        //2. 启动
        context.start();
        //3. 编写阻塞型的函数让服务器别停
        System.in.read();

    }
}
