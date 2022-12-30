package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = dtoToEntity(order);
        orderRepository.save(orderEntity);
        return entityToDto(orderEntity);
    }



    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity order = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = entityToDto(order);
        return orderDto;
    }


    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderEntity.setUserId(order.getUserId());
        orderEntity.setCost(order.getCost());
        orderEntity.setItems(order.getItems());
//        orderEntity.setStatus(order.isStatus());
        orderRepository.save(orderEntity);
        return entityToDto(orderEntity);
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderRepository.findAll().forEach(order->orderDtoList.add(entityToDto(order)));
        return orderDtoList;
    }

    private OrderDto entityToDto(OrderEntity order) {
        return OrderDto.builder().id(order.getId()).
                        orderId(order.getOrderId()).
                        cost(order.getCost()).
                        items(order.getItems()).
                        userId(order.getUserId()).
                        status(order.isStatus()).build();
    }

    private OrderEntity dtoToEntity(OrderDto order) {
        return OrderEntity.builder().orderId(order.getOrderId()).
                                    cost(order.getCost()).
                                    items(order.getItems()).
                                    userId(order.getUserId()).
                                    status(order.isStatus()).build();
    }

}