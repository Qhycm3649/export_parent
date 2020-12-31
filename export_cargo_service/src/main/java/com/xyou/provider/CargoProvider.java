package com.xyou.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//消费者
public class CargoProvider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring/applicationContext-*.xml");
        context.start();
        System.in.read();
    }
}
