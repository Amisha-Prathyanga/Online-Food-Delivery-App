package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class cart extends AppCompatActivity {

    private Button btnChckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnChckout = (Button) findViewById(R.id.btnCheckout2);
        btnChckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEnrol();
            }
        });
    }

    private void openEnrol() {
        Intent intent = new Intent(this, com.example.foodapp.enrolment.class);
        startActivity(intent);
    }
}