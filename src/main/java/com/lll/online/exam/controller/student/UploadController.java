package com.lll.online.exam.controller.student;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.service.FileUpload;
import com.lll.online.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传控制器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
@RestController
@RequestMapping("/api/student/upload")
public class UploadController extends BaseController {

    @Autowired
    private FileUpload fileUpload;
    @Autowired
    private UserService userService;

    /**
    * @Description: 用户图片上传
    * @Param: MultipartFile
    * @return: com.lll.online.exam.base.Result
    * @Date: 2021/3/20
    */
    @PostMapping("image")
    public Result<String> updateUserImage( MultipartFile file){
        //TODO：调用七牛云api存储图片
        String imageName = file.getOriginalFilename();
        long size = file.getSize();
        try (InputStream inputStream = file.getInputStream();){
            String imagePath = fileUpload.uploadImage(inputStream, size, imageName);
            userService.changeUserImage(getCurrentUser(),imagePath);
            return Result.ok(imagePath);
        } catch (IOException e) {
            return Result.fail(2,e.getMessage());
        }
    }

}
