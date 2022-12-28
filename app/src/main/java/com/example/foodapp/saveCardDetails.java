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

public class saveCardDetails extends AppCompatActivity {
    private Button delBtn, updBtn;
    private Context context;
    private EditText name, number, month, year, cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card_details);
        context =this;
        delBtn=findViewById(R.id.deleteButton);
        updBtn=findViewById(R.id.updateButton);

        name = findViewById(R.id.cdname);
        number = findViewById(R.id.cdnum);
        month = findViewById(R.id.cdmonth);
        year = findViewById(R.id.cdyear);
        cvv = findViewById(R.id.cdcvv);

        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        SessionManagement sessionManagement = new SessionManagement(context);
        int id = sessionManagement.getSession();

        DBHandler dbHandler = new DBHandler(context);
        Cdmodel cdmodel;
        cdmodel = dbHandler.getCardDetails(id);

        int cardID = cdmodel.getId();
        name.setText(cdmodel.getCardName());
        number.setText(cdmodel.getCardNumber());
        month.setText(cdmodel.getMonth());
        year.setText(cdmodel.getYear());;
        cvv.setText(cdmodel.getCvv());


        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                SessionManagement sessionManagement = new SessionManagement(context);

                int id = sessionManagement.getSession();

                boolean res = dbHandler.deleteCardDetails(cardID);

                if (!res){
                    Toast.makeText(getApplicationContext(), "Card Details Deleted Successfully ! ", Toast.LENGTH_LONG).show();
                    goToMainMenu();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Card Details Deletion Unsuccessful ! ", Toast.LENGTH_LONG).show();
                    goToMainMenu();
                }

            }
        });

        updBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cdnumber = number.getText().toString();
                String cdname = name.getText().toString();
                String cdmonth = month.getText().toString();
                String cdyear = year.getText().toString();
                String cdcvv = cvv.getText().toString();

                Cdmodel cdmodel = new Cdmodel(cardID, cdnumber,cdname, cdmonth, cdyear, cdcvv);

                int res = dbHandler.updateCardDetails(cdmodel);

                if (res == 1){
                    Toast.makeText(getApplicationContext(), "Card Details Updated Successfully ! ", Toast.LENGTH_LONG).show();
                    goToMainMenu();
                }else {
                    Toast.makeText(getApplicationContext(), "Card Details Update Unsuccessful ! ", Toast.LENGTH_LONG).show();
                    goToMainMenu();
                }

            }
        });

    }

    public void goToMainMenu(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }
}