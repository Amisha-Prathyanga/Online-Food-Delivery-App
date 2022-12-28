package com.example.foodapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>userList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);


    initData();
    initRecyclerView();

    }

    private void initData() {

        userList = new ArrayList<>();

        userList.add(new ModelClass(R.drawable.capu, "Capuccino", "Rs. 700", "Quantity : 4", "_______________________________________________________" ));

        userList.add(new ModelClass(R.drawable.mshake, "MilkShake", "Rs. 600", "Quantity : 5", "_______________________________________________________" ));

        userList.add(new ModelClass(R.drawable.pizza2, "Garlic Pizza", "Rs. 3700", "Quantity : 1", "_______________________________________________________" ));

        userList.add(new ModelClass(R.drawable.pepsi, "Pepsi", "Rs. 300", "Quantity : 6", "_______________________________________________________" ));

        userList.add(new ModelClass(R.drawable.rice, "Nazigorang", "Rs. 1900", "Quantity : 3", "_______________________________________________________" ));
    }

    private void initRecyclerView() {

        recyclerView=findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}