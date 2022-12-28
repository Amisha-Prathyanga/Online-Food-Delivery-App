package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class enrolment extends AppCompatActivity {

    private Button submitBtn;
    private Button canclOdrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolment);

        submitBtn = (Button) findViewById(R.id.btnSubmit1);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOdrSummry();
            }
        });

        canclOdrBtn = (Button) findViewById(R.id.btncanclOdr1);
        canclOdrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });
    }

    private void openMain() {
        Intent intent = new Intent(this, Order_Details.class);
        startActivity(intent);
    }

    private void openOdrSummry() {
        Intent intent = new Intent(this, order_summary.class);
        startActivity(intent);
    }
}