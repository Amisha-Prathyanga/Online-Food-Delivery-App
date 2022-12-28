package com.example.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserAccount extends AppCompatActivity {

    private Button editProfileButton, changePasswordButton, deleteProfileButton, logoutButton;
    private TextView name, email, mobile, birthDate, username;
    private DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        name = findViewById(R.id.txtNameProfile);
        email = findViewById(R.id.txtEmailProfile);
        mobile = findViewById(R.id.txtMobileProfile);
        birthDate = findViewById(R.id.txtBirthDayProfile);
        username = findViewById(R.id.txtHiUser);

        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        String name1 = sharedPreferences.getString("Name", null);
        String email1 = sharedPreferences.getString("Email", null);
        String mobile1 = sharedPreferences.getString("Mobile", null);
        String birthDate1 = sharedPreferences.getString("BirthDate", null);

        name.setText(name1);
        email.setText(email1);
        mobile.setText(mobile1);
        birthDate.setText(birthDate1);

        editProfileButton = findViewById(R.id.btnEditProfile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfilePage();
            }
        });


        changePasswordButton = findViewById(R.id.btnChangePassword);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChangePasswordPage();
            }
        });


        deleteProfileButton = findViewById(R.id.btnDeleteProfile);
        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDeleteProfilePage();
            }
        });


        logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToMainPage();
                logOut();
            }
        });

    }

    public void goToEditProfilePage(){
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }

    public void goToChangePasswordPage(){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    public void goToDeleteProfilePage(){
        Intent intent = new Intent(this, DeleteProfile.class);
        startActivity(intent);
    }

    public void goToMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logOut(){
        SessionManagement sessionManagement = new SessionManagement(UserAccount.this);
        sessionManagement.removeSession();
        goToMainPage();
    }



}