package com.ray.tech.service;

import com.ray.tech.model.ApiResponse;
import com.ray.tech.model.ControllerFeedbackEnum;
import com.ray.tech.constant.FileConstant;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class FileService {
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File dest = new File(FileConstant.UPLOAD_PATH + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "SUCCESS";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    public ApiResponse downloadFromServer(@RequestParam("name") String name, HttpServletResponse response) {
        File file = new File(FileConstant.UPLOAD_PATH + name + FileConstant.Suffix.JPG);
        if (!file.exists()) {
            return ApiResponse.createFeedback(ControllerFeedbackEnum.API_FILE_NOT_FOUND);
        }
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            // 设置被下载而不是被打开
            response.setContentType("application/gorce-download");
            // 设置被第三方工具打开,设置下载的文件名
            response.addHeader("Content-disposition", "attachment;fileName=" + name + FileConstant.Suffix.JPG);
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            return ApiResponse.createFeedback(ControllerFeedbackEnum.API_INVOKE_FAILED, e.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                return ApiResponse.createFeedback(ControllerFeedbackEnum.API_INVOKE_FAILED, e.getMessage());
            }
        }
        //如果成功则不返回任何值。
        // 因为：由于ContentType已经被成功修改，
        // 如果返回JSON则会出现HTTP不支持该类型的异常，虽然不影响下载
        return null;
    }
}
