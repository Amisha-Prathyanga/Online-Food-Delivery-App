package com.example.foodapp.listener;

import com.example.foodapp.model.DrinkModel;

import java.util.List;

public interface IDrinkLoadListener {
    void onDrinkLoadSuccess(List<DrinkModel> drinkModelList);
    void onDrinkLoadFailed(String message);
}
