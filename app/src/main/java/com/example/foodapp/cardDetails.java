package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class cardDetails extends AppCompatActivity {
    private ConstraintLayout cardbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        cardbtn=findViewById(R.id.cardbtn);
        cardbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cardDetails.this,paymentSuccess.class));
            }
        });
    }
}