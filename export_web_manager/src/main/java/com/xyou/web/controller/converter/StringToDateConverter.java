package com.xyou.web.controller.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
自定义工具类，进行日期格式转换
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        if (!StringUtils.isEmpty(s)) {
            //创建自定义转换器的对象
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(s);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
