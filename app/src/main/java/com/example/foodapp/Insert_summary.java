package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.model.DrinkModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Insert_summary extends AppCompatActivity {

    EditText imgtxt,nametxt,pricetxt;
    Button updateBtn, foodMenuBtn;
    DrinkModel drinkModel;

    private void clearcontrols(){
        imgtxt.setText("");
        nametxt.setText("");
        pricetxt.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_summary);

        imgtxt = findViewById(R.id.imgtxt01);
        nametxt = findViewById(R.id.nametxt01);
        pricetxt = findViewById(R.id.pricetxt01);

        drinkModel = new DrinkModel();

        Show();

        updateBtn = (Button) findViewById(R.id.updtbtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpdateItem();
            }
        });

    }

    public void Show(){
        DatabaseReference readRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Drink").child("07");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    imgtxt.setText(snapshot.child("image").getValue().toString());
                    nametxt.setText(snapshot.child("name").getValue().toString());
                    pricetxt.setText(snapshot.child("price").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openUpdateItem(){
        Intent intent = new Intent(this,update_Item.class);
        startActivity(intent);
    }
}