package com.lll.online.exam.service;

import java.io.InputStream;

/**
 * 文件上传接口
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
public interface FileUpload {
    String uploadImage(InputStream inputStream,Long size,String imageName );
}
