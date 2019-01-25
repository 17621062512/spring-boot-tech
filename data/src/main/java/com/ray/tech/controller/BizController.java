package com.ray.tech.controller;

import com.ray.tech.model.ApiResponse;
import com.ray.tech.model.AuthorizeTrackPointEnum;
import com.ray.tech.model.BaseTaskPo;
import com.ray.tech.service.BizTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/biz_tasks")
public class BizController {

    @Resource
    private BizTaskService bizTaskService;


    /**
     * 根据机构账号分页获取任务列表
     * <p>
     * biz_tasks?page_index=1&page_size=10&token=选填&account=选填&website=选填
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping
    public ApiResponse findByAccount(@RequestParam(value = "page_index", required = false, defaultValue = "1") int pageIndex,
                                     @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                                     @RequestParam(value = "token", required = false) String token,
                                     @RequestParam(value = "account", required = false) String account,
                                     @RequestParam(value = "website", required = false) String website,
                                     @RequestParam(value = "track", required = false) AuthorizeTrackPointEnum authorizeTrackPoint,
                                     @RequestParam(value = "start_time", required = false, defaultValue = "1") Long start,
                                     @RequestParam(value = "end_time", required = false) Long end,
                                     @RequestParam(value = "org_account", required = false) String orgAccount
//                                                     @RequestAttribute OrganizationBo organizationBo
    ) {
//        String loginAccount = organizationBo.getAccount();
        String loginAccount = "demo";
        String queryAccount;
        if (loginAccount.equals("demo1") && StringUtils.isNotBlank(orgAccount)) {
            queryAccount = orgAccount;
        } else {
            queryAccount = loginAccount;
        }
        BaseTaskPo queryObject = new BaseTaskPo();
        queryObject.setOrganizationAccount(queryAccount);
        queryObject.setAuthorizeTrackPoint(authorizeTrackPoint);
        queryObject.setToken(token);
        queryObject.setAccount(account);
        queryObject.setWebsite(website);
        if (end == null) {
            end = System.currentTimeMillis();
        }
        return new ApiResponse<>(bizTaskService.findByOrganizationAccount(queryObject, start, end, pageIndex, pageSize));
    }


}
