package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class order_summary extends AppCompatActivity {

    TextView phoneTxt, nameTxt, addressTxt;
    Button cnclOdr;
    Button editOdr;
    Button editDel;
    ImageView menu;
    DatabaseReference dbRef;
    Enrol enrol;
    TextView recieve_total;

    private void clearControls(){
        nameTxt.setText("");
        phoneTxt.setText("");
        addressTxt.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        phoneTxt= findViewById(R.id.phoneTxt1);
        nameTxt= findViewById(R.id.nameTxt1);
        addressTxt = findViewById(R.id.addressTxt1);

        enrol= new Enrol();


        Show();

        recieve_total = findViewById(R.id.subTotal2);

        Intent intent = getIntent();
        String total = intent.getStringExtra("total2");
        recieve_total.setText(total);


        menu = findViewById(R.id.menuTab);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });

        cnclOdr = (Button) findViewById(R.id.btncanclOdr2);
        cnclOdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });

//        editOdr = (Button) findViewById(R.id.btnEditOdr);
//        editOdr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openEditOdr();
//            }
//        });

        editDel = (Button) findViewById(R.id.btnEditDel);
        editDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditDel();
            }
        });
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





    private void openEditDel() {
        Intent intent = new Intent(this, Update_Delivery_Details.class);
        startActivity(intent);
    }

//    private void openEditOdr() {
//        Intent intent = new Intent(this, updateOrder.class);
//        startActivity(intent);
//    }

    private void openMain() {
        Intent intent = new Intent(this, Order_Details.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }
}