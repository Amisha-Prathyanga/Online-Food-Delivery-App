package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.model.DrinkModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertItem extends AppCompatActivity {

    EditText txtImage, txtName, txtPrice;
    Button addItem;
    DatabaseReference dbRef;
    DrinkModel drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);

        txtImage = findViewById(R.id.imageInput);
        txtName = findViewById(R.id.nameInput);
        txtPrice = findViewById(R.id.priceInput);

        addItem = findViewById(R.id.addItemBtn);

        drink = new DrinkModel();
    }

    public void Save(View view){
        dbRef = FirebaseDatabase.getInstance("https://foodapp-aa7cd-default-rtdb.firebaseio.com/").getReference().child("Drink");
        try {
            if (TextUtils.isEmpty(txtImage.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter an Image Link", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtName.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a Name", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a Price", Toast.LENGTH_SHORT).show();
            else {
                drink.setImage(txtImage.getText().toString().trim());
                drink.setName(txtName.getText().toString().trim());
                drink.setPrice(txtPrice.getText().toString().trim());

                // dbRef.push().setValue(std);
                dbRef.child("07").setValue(drink);

                Toast.makeText(getApplicationContext(), "Data Saved Succesfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Insert_summary.class);
//                intent.putExtra("total2", total);
                startActivity(intent);
                //clearControls();

            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
        }
    }

//    public void Show(View view){
//        Intent intent = new Intent(this, FoodActivity.class);
//        startActivity(intent);
//    }
}