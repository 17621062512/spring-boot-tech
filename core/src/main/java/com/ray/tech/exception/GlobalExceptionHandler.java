package com.ray.tech.exception;

import com.ray.tech.constant.GlobalControllerFeedbackEnum;
import com.ray.tech.model.JsonResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author JacobDong
 * @date 2018/5/10 17:27
 */
@Slf4j
public class GlobalExceptionHandler {

    public static JsonResponse convertExceptionToJsonResponse(Exception e) {
        if (e instanceof ArgumentException) {
            ArgumentException argumentException = (ArgumentException) e;
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setCode(GlobalControllerFeedbackEnum.GLOBAL_EXCEPTION_PARAMETERS_INVALID.name());
            jsonResponse.setMessage(argumentException.getMessage());
            return jsonResponse;
        }
        if (e instanceof PermissionException) {
            PermissionException permissionException = (PermissionException) e;
            String errorMessage = String.format("访问权限不足：%s", permissionException.getMessage());
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setCode(GlobalControllerFeedbackEnum.GLOBAL_EXCEPTION_PERMISSION_DENY.name());
            jsonResponse.setMessage(errorMessage);
            return jsonResponse;
        }
        if (e instanceof InternalException) {
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setCode(GlobalControllerFeedbackEnum.GLOBAL_EXCEPTION_INTERNAL_SYSTEM_ERROR.name());
            jsonResponse.setMessage(e.getMessage());
            return jsonResponse;
        } else {
            String locateToken = UUID.randomUUID().toString().replace("-", "");
            String code = GlobalControllerFeedbackEnum.GLOBAL_EXCEPTION_INTERNAL_SYSTEM_ERROR.name();
            String message = GlobalControllerFeedbackEnum.GLOBAL_EXCEPTION_INTERNAL_SYSTEM_ERROR.getMessage();
            log.error("{} {}", locateToken, e);
            return JsonResponse.createFeedback(code, message, " TRACK_ID = " + locateToken);
        }
    }
}
