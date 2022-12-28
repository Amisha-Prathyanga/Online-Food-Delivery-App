package com.example.foodapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;

public class Category extends AppCompatActivity {

    Button viewAdd,beverge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_menu);

        viewAdd = findViewById(R.id.btnAdd);
        beverge = (Button) findViewById(R.id.beverage);
        beverge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBeverage();
            }
        });

        viewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddMenupage();
            }
        });
    }

    private void openAddMenupage() {
        Intent intent = new Intent(this,InsertItem.class);
        startActivity(intent);
    }

    private void openBeverage(){
        Intent intent = new Intent(this,FoodActivity.class);
        startActivity(intent);
    }


}
