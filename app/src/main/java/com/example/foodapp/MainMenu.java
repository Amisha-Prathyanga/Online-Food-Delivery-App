package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainMenu extends AppCompatActivity {

    private Button chckoutBtn1;
    private Button chckoutBtn2;
    private Button chckoutBtn3;
    private Button atcBtn1;
    private Button atcBtn2;
    private Button atcBtn3;

    //private Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        chckoutBtn1 = (Button) findViewById(R.id.btnChkout01);
        chckoutBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openMain();

            }

        });

        chckoutBtn2 = (Button) findViewById(R.id.btnChkout02);
        chckoutBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openMain();

            }

        });

        chckoutBtn3 = (Button) findViewById(R.id.btnChkout03);
        chckoutBtn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openMain();

            }

        });

        atcBtn1 = (Button) findViewById(R.id.ctnAdc1);
        atcBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCart();
            }
        });

        atcBtn2 = (Button) findViewById(R.id.ctnAdc2);
        atcBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCart();
            }
        });

        atcBtn3 = (Button) findViewById(R.id.ctnAdc3);
        atcBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCart();
            }
        });
//        viewButton = (Button) findViewById(R.id.btnClick);
//        viewButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                openMymenuPage();
//
//            }
//
//        });
    }

    private void openCart() {
        Intent intent = new Intent(this, com.example.foodapp.cart.class);
        startActivity(intent);
    }

    private void openMain() {
        Intent intent = new Intent(this, com.example.foodapp.Order_Details.class);
        startActivity(intent);
    }

//        public void openMymenuPage(){
//            Intent intent = new Intent(this, My_Menu.class);
//            startActivity(intent);
//
//        }
    }