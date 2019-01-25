package com.ray.tech.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiResponse<T> extends JsonResponse<T> {

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
        this.code = ControllerFeedbackEnum.API_MONITOR_PLATFORM_INVOKE_SUCCESS.name();
        this.message = ControllerFeedbackEnum.API_MONITOR_PLATFORM_INVOKE_SUCCESS.description();
    }

    public static ApiResponse createFeedback(ControllerFeedbackEnum feedback, String... placeHolders) {
        ApiResponse jsonResponse = new ApiResponse();
        jsonResponse.code = feedback.name();
        String defaultMessage = feedback.description();
        String message = defaultMessage;
        if (defaultMessage.contains("%s")) {
            message = String.format(defaultMessage, placeHolders);
        }
        jsonResponse.message = message;
        log.debug(feedback.toString());
        return jsonResponse;
    }
}
