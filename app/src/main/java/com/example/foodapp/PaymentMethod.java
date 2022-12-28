package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethod extends AppCompatActivity {

    private TextView txtMaster, txtVisa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        txtMaster = findViewById(R.id.txtMaster);
        txtVisa = findViewById(R.id.txtVisa);

        txtVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPayment();
            }
        });

        txtMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPayment();
            }
        });
    }

    public void goToPayment(){
        Intent intent = new Intent(this, CardsDetail.class);
        startActivity(intent);
    }
}