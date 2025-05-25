package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.DeepSeekMapper;
import com.example.campusmarketserver.model.dto.DeepSeekData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeepSeekService {
    private final DeepSeekMapper deepSeekMapper;
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-fa44715063e34b36b03bcbf5d800d0cf";

    public DeepSeekService(DeepSeekMapper deepSeekMapper) {
        this.deepSeekMapper = deepSeekMapper;
    }

    public String getResponseFromDeepSeek(String userMessage, int userId) {
        List<DeepSeekData> deepSeekDataList = deepSeekMapper.selectData(userId);
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // 准备 messages 列表
            List<Map<String, String>> messages = new ArrayList<>();

            // 系统角色消息
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是一款校园二手交易小程序的智能AI客服");
            messages.add(systemMessage);

            // 遍历 deepSeekData 加入商品信息
            for (DeepSeekData data : deepSeekDataList) {
                Map<String, String> productMessage = new HashMap<>();
                productMessage.put("role", "user");
                productMessage.put("content", String.format("用户 %s 发布了商品：%s，价格：%s元，描述：%s",
                        data.getSellerName(),
                        data.getProductName(),
                        data.getProductPrice(),
                        data.getProductDescription()));
                messages.add(productMessage);
            }

            // 当前用户输入
            Map<String, String> userMessageMap = new HashMap<>();
            userMessageMap.put("role", "user");
            userMessageMap.put("content", userMessage);
            messages.add(userMessageMap);

            // 构造最终请求体 Map
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("model", "deepseek-chat");
            requestBodyMap.put("messages", messages);

            // 转成 JSON 字符串
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            // 构造 HTTP 请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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
