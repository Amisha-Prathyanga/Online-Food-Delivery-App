package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class enrolment extends AppCompatActivity {

    Button submitBtn;
    Button canclOdrBtn;
    EditText name, phone, address;
    DatabaseReference dbRef;
    Enrol enrol;
    TextView recieve_total;

//    private static final Pattern MOBILE_PATTERN =
//            Pattern.compile("[0][0-9]{9}");

    private void clearControls(){
        name.setText("");
        phone.setText("");
        address.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolment);

        name = findViewById(R.id.nametxt);
        phone = findViewById(R.id.phonetxt);
        address = findViewById(R.id.addresstxt);

        submitBtn = findViewById(R.id.btnSubmit1);

        enrol = new Enrol();

        recieve_total = findViewById(R.id.subTotal);

        Intent intent = getIntent();

        String total = intent.getStringExtra("total");

        recieve_total.setText(total);




        canclOdrBtn = (Button) findViewById(R.id.btncanclOdr1);
        canclOdrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });

    }




    public void Save(View view){
        dbRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Enrol");
        try {
            if (TextUtils.isEmpty(name.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a Name", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(phone.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter Phone Number", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(address.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter an Address", Toast.LENGTH_SHORT).show();
            else {
                enrol.setName(name.getText().toString().trim());
                enrol.setPhone(phone.getText().toString().trim());
                enrol.setAddress(address.getText().toString().trim());


                //dbRef.push().setValue(enrol);
                dbRef.child("cus1").setValue(enrol);

                String total = recieve_total.getText().toString();

                Toast.makeText(getApplicationContext(), "Data Saved Succesfully", Toast.LENGTH_SHORT).show();
                clearControls();
                Intent intent = new Intent(this, order_summary.class);
               intent.putExtra("total2", total);
                startActivity(intent);

            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMain() {
        Intent intent = new Intent(this, Order_Details.class);
        startActivity(intent);
    }



}