package com.example.campusmarketserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DeepSeekService {

    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-fa44715063e34b36b03bcbf5d800d0cf";

    public String getResponseFromDeepSeek(String userMessage) {
        try {
            // 构造请求体 JSON
            String requestBody = """
                {
                    "model": "deepseek-chat",
                    "messages": [
                        {
                            "role": "system",
                            "content": "你是一款校园二手交易小程序的智能AI客服"
                        },
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }
                """.formatted(userMessage);

            // 创建请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 发送请求
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 解析返回结果
            return parseResponse(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return "调用出错：" + e.getMessage();
        }
    }

    private String parseResponse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            return "解析返回值出错";
        }
    }
}
