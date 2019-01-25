package com.ray.tech.controller;

import com.ray.tech.model.ApiResponse;
import com.ray.tech.model.ControllerFeedbackEnum;
import com.ray.tech.service.commond.CommandService;
import com.ray.tech.service.decorator.ReportDecoratorService;
import com.ray.tech.service.factory.DataService;
import com.ray.tech.service.factory.NoSuchDataSourceException;
import com.ray.tech.service.observer.ObService;
import com.ray.tech.service.state.AuthService;
import com.ray.tech.service.strategy.AStory;
import com.ray.tech.service.template.TemplateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;


@RestController
@RequestMapping("/design_pattern")
public class PatternController {
    @Resource
    private DataService dataService;
    @Resource
    private AStory aStory;
    @Resource
    private ObService obService;
    @Resource
    private ReportDecoratorService reportDecoratorService;
    @Resource
    private CommandService commandService;
    @Resource
    private AuthService authService;

    @GetMapping("/factory")
    public ApiResponse getDataByType(@RequestParam("type") String type) {
        return new ApiResponse<>(dataService.getData(type));
    }

    @PostMapping("/factory/online")
    public ApiResponse registerDataSource(@RequestParam("type") String type) {
        try {
            dataService.register(type);
            return new ApiResponse<>("注册成功");
        } catch (NoSuchDataSourceException e) {
            return ApiResponse.createFeedback(ControllerFeedbackEnum.API_MONITOR_PLATFORM_INVOKE_FAILED, e.getMessage());
        }
    }

    @PostMapping("/factory/offline")
    public ApiResponse unregisterDataSource(@RequestParam("type") String type) {
        dataService.unregister(type);
        return new ApiResponse<>("注销成功");
    }

    @GetMapping("/strategy")
    public void showStory() {
        aStory.start();
    }

    @PostMapping("/observer")
    public void postMsgToRegisters(@RequestBody String msg) {
        obService.notifyObs(msg);
    }

    @PostMapping("/observer/register")
    public void registerObs(@RequestBody Map map) {
        String o = (String) map.get("type");
        String name = (String) map.get("name");
        if (name == null) {
            return;
        }
        if (o.equals("register")) {
            obService.register(name);
        } else {
            obService.unregister(name);
        }
    }

    @PostMapping("/decorator")
    public ApiResponse getDecoratedReport(@RequestParam(value = "report", defaultValue = "mifeng") String reportType, @RequestBody List<String> types) {
        return new ApiResponse<>(reportDecoratorService.getReport(reportType, types));
    }

    @GetMapping("/command")
    public ApiResponse doCommand() {
        commandService.invoke();
        return new ApiResponse<>("SUCCESS");
    }

    @GetMapping("/template")
    public ApiResponse runnableTemplate() throws InterruptedException {
        List list = new ArrayList<>();
        list.add(new TemplateService("X"));
        Collections.sort(list);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(500));
        executor.invokeAll(list);
        return new ApiResponse<>("SUCCESS");
    }

    public static void main(String[] args) throws InterruptedException {
        List list = new ArrayList<>();
        System.out.println("list");
        list.add(new TemplateService("A"));
        for (Object o : list) {
            System.out.println(o);
        }
//        list.add(new TemplateService("B"));
//        list.add(new TemplateService("C"));
//        list.add(new TemplateService("D"));
        Collections.sort(list);
        System.out.println("sort");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(500));
        executor.invokeAll(list);
        System.out.println("invoke");
        executor.shutdown();
        System.out.println("shutdown");
    }

    @GetMapping("/state")
    public ApiResponse authState() {
        authService.auth();
        return new ApiResponse<>("SUCCESS");
    }
}
