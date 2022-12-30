package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.FoodService;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse = dtoToResponse(foodDto);
		return foodDetailsResponse;
	}


	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto = requestToDto(foodDetails);
		FoodDto respondedFoodDto = foodService.createFood(foodDto);
		return dtoToResponse(respondedFoodDto);
	}


	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto = requestToDto(foodDetails);
		FoodDto respondedFoodDto = foodService.updateFoodDetails(id,foodDto);
		return dtoToResponse(respondedFoodDto);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		foodService.deleteFoodItem(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete food");
		operationStatusModel.setOperationResult("Deleted successfully");
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto> foodDtoList = foodService.getFoods();
		List<FoodDetailsResponse> foodDetailsResponses = dtoListToResponseList(foodDtoList);

		return foodDetailsResponses;
	}

	private List<FoodDetailsResponse> dtoListToResponseList(List<FoodDto> foodDtoList) {
		List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();
		for(FoodDto foodDto:foodDtoList){
			foodDetailsResponseList.add(dtoToResponse(foodDto));
		}
		return foodDetailsResponseList;
	}

	private FoodDetailsResponse dtoToResponse(FoodDto foodDto) {
		return FoodDetailsResponse.builder().foodId(foodDto.getFoodId()).
				foodName(foodDto.getFoodName()).
				foodCategory(foodDto.getFoodCategory()).
				foodPrice(foodDto.getFoodPrice()).build();
	}
	private FoodDto requestToDto(FoodDetailsRequestModel foodDetails) {
		return FoodDto.builder().foodName(foodDetails.getFoodName()).
				foodCategory(foodDetails.getFoodCategory()).
				foodPrice(foodDetails.getFoodPrice()).build();
	}
}
