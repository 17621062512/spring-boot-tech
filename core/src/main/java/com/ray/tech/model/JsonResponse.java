package com.ray.tech.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class JsonResponse<T> {

    protected T data;
    protected String code;
    protected String message;


    public JsonResponse() {
        //保留空构造方法
    }

    public static JsonResponse createFeedback(String code, String feedbackMessage, Object... placeHolders) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.code = code;
        String message = feedbackMessage;
        if (feedbackMessage.contains("%s")) {
            message = String.format(feedbackMessage, placeHolders);
        }
        jsonResponse.message = message;
        return jsonResponse;
    }

}
