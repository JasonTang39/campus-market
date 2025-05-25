package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.dto.OrderResponse;
import com.example.campusmarketserver.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getSoldProductOrders")
    public List<OrderResponse> getSoldProductOrders(@CookieValue("userId") int sellerId) {
        return orderService.getSoldProductOrders(sellerId);
    }

    @GetMapping("/getBoughtProductOrders")
    public List<OrderResponse> getBoughtProductOrders(@CookieValue("userId") int buyerId) {
        return orderService.getBoughtProductOrders(buyerId);
    }

    @PutMapping("/confirmReceipt")
    public void confirmReceipt(@RequestBody Map<String, Integer> requestBody){
        orderService.updateOrderStatus(requestBody.get("orderId"));
    }

}
