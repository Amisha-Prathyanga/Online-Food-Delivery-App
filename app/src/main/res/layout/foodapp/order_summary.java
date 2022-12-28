package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class order_summary extends AppCompatActivity {

    private Button cnclOdr;
    private Button editOdr;
    private Button editDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        cnclOdr = (Button) findViewById(R.id.btncanclOdr2);
        cnclOdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });

        editOdr = (Button) findViewById(R.id.btnEditOdr);
        editOdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditOdr();
            }
        });

        editDel = (Button) findViewById(R.id.btnEditDel);
        editDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditDel();
            }
        });
    }

    private void openEditDel() {
        Intent intent = new Intent(this, Update_Delivery_Details.class);
        startActivity(intent);
    }

    private void openEditOdr() {
        Intent intent = new Intent(this, updateOrder.class);
        startActivity(intent);
    }

    private void openMain() {
        Intent intent = new Intent(this, Order_Details.class);
        startActivity(intent);
    }
}