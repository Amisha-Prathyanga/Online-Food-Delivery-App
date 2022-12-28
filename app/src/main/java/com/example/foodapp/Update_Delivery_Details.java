package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_Delivery_Details extends AppCompatActivity {

    EditText phoneTxt, nameTxt, addressTxt;
    Button btnUpdate;
    DatabaseReference dbRef;
    Enrol enrol;
    Context context;

    private void clearControls(){
        nameTxt.setText("");
        phoneTxt.setText("");
        addressTxt.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_details);

        phoneTxt= findViewById(R.id.phone_txt);
        nameTxt= findViewById(R.id.name_txt);
        addressTxt = findViewById(R.id.address_txt);

        enrol= new Enrol();


        Show();

        btnUpdate = (Button) findViewById(R.id.btnUpdate1);
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openOdrSummry();
//            }
//        });
    }

    public void Show(){
        DatabaseReference readRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Enrol").child("cus1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    nameTxt.setText(snapshot.child("name").getValue().toString());
                    phoneTxt.setText(snapshot.child("phone").getValue().toString());
                    addressTxt.setText(snapshot.child("address").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Update(View view){
        DatabaseReference upRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Enrol");
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("cus1")) {
                    try {
                        enrol.setName(nameTxt.getText().toString().trim());
                        enrol.setPhone(phoneTxt.getText().toString().trim());
                        enrol.setAddress(addressTxt.getText().toString().trim());

                        dbRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Enrol").child("cus1");
                        dbRef.setValue(enrol);
                        //clearControls();

                        Toast.makeText(getApplicationContext(), "Data Updated Succefully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, order_summary.class);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "No Source to Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void openOdrSummry() {
//        Intent intent = new Intent(this, order_summary.class);
//        startActivity(intent);
//    }
}