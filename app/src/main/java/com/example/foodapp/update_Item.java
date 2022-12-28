package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class update_Item extends AppCompatActivity {

    EditText imgUpdate, nameUpdate, priceUpdate;
    Button UpdateItem, foodMenu;
    DatabaseReference dbRef;
    DrinkModel drinkModel;
    Context context;

    private void clearControls(){
        imgUpdate.setText("");
        nameUpdate.setText("");
        priceUpdate.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        imgUpdate = findViewById(R.id.imgUpd);
        nameUpdate = findViewById(R.id.nameUpd);
        priceUpdate = findViewById(R.id.priceUpd);

        drinkModel = new DrinkModel();

        Show();
    }

    public void Show(){
        DatabaseReference readRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Drink").child("07");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    imgUpdate.setText(snapshot.child("image").getValue().toString());
                    nameUpdate.setText(snapshot.child("name").getValue().toString());
                    priceUpdate.setText(snapshot.child("price").getValue().toString());
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
        DatabaseReference upRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Drink");
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("07")) {
                    try {
                        drinkModel.setImage(imgUpdate.getText().toString().trim());
                        drinkModel.setName(nameUpdate.getText().toString().trim());
                        drinkModel.setPrice(priceUpdate.getText().toString().trim());

                        dbRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Drink").child("07");
                        dbRef.setValue(drinkModel);
                        //clearControls();

                        Toast.makeText(getApplicationContext(), "Data Updated Succefully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, Insert_summary.class);
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
}