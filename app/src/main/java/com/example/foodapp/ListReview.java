package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListReview extends AppCompatActivity {

    Button addReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review);

        addReview = findViewById(R.id.btnAddYourReview);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSaveReview();
            }
        });

    }

    public void  goToSaveReview(){
        Intent intent = new Intent(this, AddReview.class);
        startActivity(intent);
    }
}