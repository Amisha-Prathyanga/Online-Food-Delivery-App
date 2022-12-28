package com.example.foodapp.listener;

import com.example.foodapp.model.CartModel;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);
}
