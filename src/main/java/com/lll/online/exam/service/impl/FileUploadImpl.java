package com.lll.online.exam.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.config.property.QnConfig;
import com.lll.online.exam.config.property.SystemConfig;
import com.lll.online.exam.service.FileUpload;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 文件上传接口实现
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
@Component
public class FileUploadImpl implements FileUpload {

    private final Logger logger = LoggerFactory.getLogger(FileUpload.class);

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public String uploadImage(InputStream inputStream, Long size, String imageName) {
        QnConfig qnConfig = systemConfig.getQnConfig();
        Configuration configuration = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(configuration);
        Auth auth = Auth.create(qnConfig.getAccessKey(), qnConfig.getSecretKey());
        String token = auth.uploadToken(qnConfig.getBucket());

        try {
            Response  put = uploadManager.put(inputStream, null, token, null, null);
            DefaultPutRet putRet = JSONObject.parseObject(put.bodyString(), DefaultPutRet.class);
            return qnConfig.getUrl()+"/"+putRet.key;
        } catch (QiniuException e) {
            logger.info(e.getMessage(),e);
        }
        return null;
    }
}
