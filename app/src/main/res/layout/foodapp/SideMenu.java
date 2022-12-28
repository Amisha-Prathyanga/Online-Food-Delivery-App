package com.example.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SideMenu extends AppCompatActivity {

    TextView profile;
    TextView home;
    TextView foodMenu;
    TextView myOrders;
    TextView reviews;
    TextView cards;
    TextView username;
    TextView orderManage, foodMenuManage;
    ImageView orderManageImg, foodMenuManageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        username = findViewById(R.id.txtUsernameInSideMenu);

        orderManage = findViewById(R.id.txtOrderManagement);
        foodMenuManage = findViewById(R.id.txtFoodMenuManagement);
        orderManageImg = findViewById(R.id.imgOderManagement);
        foodMenuManageImg = findViewById(R.id.imgFoodMenuManagement);


        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        String email1 = sharedPreferences.getString("Email", null);
        String type = sharedPreferences.getString("Type", null);
        username.setText(email1);

        if (type.equals("admin")) {
            orderManage.setVisibility(View.VISIBLE);
            foodMenuManage.setVisibility(View.VISIBLE);
            orderManageImg.setVisibility(View.VISIBLE);
            foodMenuManageImg.setVisibility(View.VISIBLE);
        }


        profile = findViewById(R.id.txtMenuMyProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMyProfile();
            }
        });


        home = findViewById(R.id.txtMenuHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenu();
            }
        });

        reviews = findViewById(R.id.txtReview);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReviews();
            }
        });

        cards = findViewById(R.id.txtCard);
        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToMycards(); }
        });

        foodMenu = findViewById(R.id.txtMenuFoodMenu);
        foodMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToFoodMenu(); }
        });






    }

    public void  goToMyProfile(){
        Intent intent = new Intent(this, UserAccount.class);
        startActivity(intent);
    }

    public void goToMenu(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }

    public void goToReviews(){
        Intent intent = new Intent(this, ListReview.class);
        startActivity(intent);
    }

    public void goToMycards(){
        Intent intent = new Intent(this,saveCardDetails.class);
        startActivity(intent);
    }

    public void  goToFoodMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }





}