package com.encurtadorUrl;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {
        String body = input.get("body").toString();

        Map<String, String> bodyMap;

        try{
            bodyMap = objectMapper.readValue(body, new TypeReference<Map<String, String>>() {});
        } catch (Exception exception) {
            throw new RuntimeException("Error parsing Json body: " + exception.getMessage(), exception);
        }

        String originalUrl = bodyMap.get("originalUrl");
        String expirationTime = bodyMap.get("expirationTime");

        String shorUrlCode = UUID.randomUUID().toString().substring(0,8);

        Map<String, String> response =  new HashMap<>();
        response.put("code", shorUrlCode);

        return response;
    }
}