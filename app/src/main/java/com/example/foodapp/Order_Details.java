package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Order_Details extends AppCompatActivity {

    private Button chkoutBtn;
    private Button myOdrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        chkoutBtn = (Button) findViewById(R.id.btnCheckout2);
        chkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openenroll();
            }
        });

        myOdrBtn = (Button) findViewById(R.id.btnMyOdr);
        myOdrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyOdr();
            }
        });
    }

    private void openMyOdr() {
        Intent intent = new Intent(this, MyOrders.class);
        startActivity(intent);
    }

    private void openenroll() {
        Intent intent = new Intent(this, com.example.foodapp.enrolment.class);
        startActivity(intent);
    }
}