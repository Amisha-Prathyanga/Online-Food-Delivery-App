package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Update_Delivery_Details extends AppCompatActivity {

    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_details);

        btnUpdate = (Button) findViewById(R.id.btnUpdate1);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOdrSummry();
            }
        });
    }

    private void openOdrSummry() {
        Intent intent = new Intent(this, order_summary.class);
        startActivity(intent);
    }
}