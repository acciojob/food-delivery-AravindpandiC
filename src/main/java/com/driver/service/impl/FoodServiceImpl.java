package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = dtoToEntity(food);
        foodRepository.save(foodEntity);

        return entityToDto(foodEntity);
    }



    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        FoodDto foodDto = entityToDto(foodEntity);
        return foodDto;
    }

    private FoodDto entityToDto(FoodEntity foodEntity) {
        return FoodDto.builder().id(foodEntity.getId()).
                                foodId(foodEntity.getFoodId()).
                                foodName(foodEntity.getFoodName()).
                                foodCategory(foodEntity.getFoodCategory()).
                                foodPrice(foodEntity.getFoodPrice()).build();
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        foodEntity.setFoodPrice(foodEntity.getFoodPrice());
        foodEntity.setFoodCategory(foodEntity.getFoodCategory());
        foodEntity.setFoodName(foodEntity.getFoodName());
        foodRepository.save(foodEntity);
        return entityToDto(foodEntity);
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> foodDtoList = new ArrayList<>();
        foodRepository.findAll().forEach(food->foodDtoList.add(entityToDto(food)));
        return foodDtoList;
    }

    private FoodEntity dtoToEntity(FoodDto food) {
        return FoodEntity.builder().foodId(food.getFoodId()).
                foodCategory(food.getFoodCategory()).
                foodName(food.getFoodName()).
                foodPrice(food.getFoodPrice()).build();
    }
}