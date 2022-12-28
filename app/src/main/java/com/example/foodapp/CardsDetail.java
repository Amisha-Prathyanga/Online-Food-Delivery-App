package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CardsDetail extends AppCompatActivity {
        private Button Cbutton;
        private EditText CNumber,cName,cMonth,cYear,cCVV;
        private DBHandler dbHandler;
        private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_detail);

        Cbutton = findViewById(R.id.button);
        CNumber = findViewById(R.id.CNumber);
        cName = findViewById(R.id.cName);
        cMonth = findViewById(R.id.cMonth);
        cYear = findViewById(R.id.cYear);
        cCVV = findViewById(R.id.cCVV);
        context = this;

        dbHandler = new DBHandler(context);

        Cbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String userCNumber = CNumber.getText().toString();
                String userCName = cName.getText().toString();
                String usercMonth = cMonth.getText().toString();
                String usercYear =  cYear.getText().toString();
                String usercCVV = cCVV.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                SessionManagement sessionManagement = new SessionManagement(context);
                int id = sessionManagement.getSession();


                Cdmodel cdmodel = new Cdmodel(userCNumber,userCName,usercMonth,usercYear,usercCVV, id);
                boolean res = dbHandler.cardsDetail(cdmodel);

                if (!res){
                    Toast.makeText(getApplicationContext(), "Payment successful ! ", Toast.LENGTH_LONG).show();
                    goToPaymentSuccess();
                }else
                    Toast.makeText(getApplicationContext(), "Payment Unsuccessful ! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goToPaymentSuccess(){
        Intent intent = new Intent(this, paymentSuccess.class);
        startActivity(intent);
    }
}