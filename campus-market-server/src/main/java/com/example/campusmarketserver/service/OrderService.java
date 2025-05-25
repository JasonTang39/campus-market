package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.OrderMapper;
import com.example.campusmarketserver.model.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public List<OrderResponse> getSoldProductOrders(int sellerId) {
        return orderMapper.selectSoldProductOrders(sellerId);
    }

    public List<OrderResponse> getBoughtProductOrders(int buyerId) {
        return orderMapper.selectBoughtProductOrders(buyerId);
    }

    public void updateOrderStatus(int orderId){
        orderMapper.updateOrderStatus(orderId);
    }
}
