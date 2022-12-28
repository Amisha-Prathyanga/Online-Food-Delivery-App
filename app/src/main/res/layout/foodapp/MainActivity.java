package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginButtonInMain;
    private Button signUpButtonInMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButtonInMain = (Button) findViewById(R.id.btnLogin);
        loginButtonInMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });

        signUpButtonInMain = (Button) findViewById(R.id.btnSignUp);
        signUpButtonInMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterPage();
            }
        });


    }

    public void openLoginPage(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegisterPage(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


}