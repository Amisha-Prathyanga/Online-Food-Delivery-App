package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class paymentSuccess extends AppCompatActivity {
    private Button success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);


        success = findViewById(R.id.nextButton);
        success.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToSideMenu();
            }
        });
    }

    public void goToSideMenu(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }
}