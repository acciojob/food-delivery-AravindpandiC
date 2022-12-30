package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderService;


	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderDto = orderService.getOrderById(id);

		return dtoToResponse(orderDto);
	}


	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto = requestToDto(order);
		OrderDto respondedOrderDto = orderService.createOrder(orderDto);
		return dtoToResponse(respondedOrderDto);
	}


	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto = requestToDto(order);
		OrderDto respondedOrderDto = orderService.updateOrderDetails(id,orderDto);
		return dtoToResponse(respondedOrderDto);
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		orderService.deleteOrder(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete order");
		operationStatusModel.setOperationResult("Deleted successfully");
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> orderDtos = orderService.getOrders();
		List<OrderDetailsResponse> orderDetailsResponses = dtoListToResponseList(orderDtos);
		return orderDetailsResponses;
	}

	private List<OrderDetailsResponse> dtoListToResponseList(List<OrderDto> orderDtos) {
		List<OrderDetailsResponse> orderDetailsResponses = new ArrayList<>();
		for(OrderDto orderDto : orderDtos){
			orderDetailsResponses.add(dtoToResponse(orderDto));
		}
		return orderDetailsResponses;
	}

	private OrderDetailsResponse dtoToResponse(OrderDto orderDto) {
		return OrderDetailsResponse.builder().orderId(orderDto.getOrderId()).
				                    cost(orderDto.getCost()).
				                    items(orderDto.getItems()).
				                    userId(orderDto.getUserId()).
				                    status(orderDto.isStatus()).build();
	}

	private OrderDto requestToDto(OrderDetailsRequestModel order) {
		return OrderDto.builder().items(order.getItems()).
				                  cost(order.getCost()).
				                  userId(order.getUserId()).build();
	}
}
