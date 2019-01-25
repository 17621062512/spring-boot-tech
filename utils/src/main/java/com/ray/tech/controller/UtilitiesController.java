package com.ray.tech.controller;

import com.ray.tech.model.ApiResponse;
import com.ray.tech.service.FileService;
import com.ray.tech.service.TableDemoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能展示接口
 */
@RestController
@RequestMapping("/utilities")
public class UtilitiesController {
    @Resource
    private FileService fileService;
    @Resource
    private TableDemoService tableDemoService;


    @GetMapping("/table")
    public ApiResponse getTableString() {
        return new ApiResponse<>(tableDemoService.getStatisticTable());
    }

    @GetMapping("/table/to_local")
    public void write2Local() {
        try {
            tableDemoService.writeToLocal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }


    @GetMapping("/download")
    public ApiResponse download(@RequestParam("name") String fileName, HttpServletResponse response) {
        return fileService.downloadFromServer(fileName, response);
    }


}
