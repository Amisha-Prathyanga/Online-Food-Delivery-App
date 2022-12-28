package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class updateOrder extends AppCompatActivity {

    private Button btnUpdate2;
    private Button btnCnclOdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        btnUpdate2 = (Button) findViewById(R.id.btnUpdate2);
        btnUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOdrSummry();
            }
        });

        btnCnclOdr = (Button) findViewById(R.id.btncnclOdr4);
        btnCnclOdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCnclOdr();
            }
        });
    }

    private void openCnclOdr() {
        Intent intent = new Intent(this, Order_Details.class);
        startActivity(intent);
    }

    private void openOdrSummry() {
        Intent intent = new Intent(this, order_summary.class);
        startActivity(intent);
    }
}