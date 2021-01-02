package com.xyou.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.UUID;

public class QiniuTest {

    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());//修改，刚刚你选择的是华南区
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "06QeCmEwW3Q80vDUsakS9gb1U-A5_oU-239f_vnP";
        String secretKey = "_5yh_LLnQ70ng04c5gw8R0dzpXOm0ShcIgj3CQ7k";
        String bucket = "147abc";  //空间名称
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\Wenline\\Desktop\\xiaozhuang.jpg";  //要上传图片的地址本机地址
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID().toString(); //上传文件在服务器中的名字，如果不指定使用hash值作为文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            //文件的访问路径： http://域名/文件名
            System.out.println("访问的路径：http://qm8zrcj61.hn-bkt.clouddn.com/"+key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
