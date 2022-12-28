package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class DeleteProfile extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 simple letter
                    "(?=.*[A-Z])" +         //at least 1 capital letter
                    ".{8,}" +               //at least 8 characters
                    "$");

    Button deleteProfile;
    EditText currentPassword;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);
        context = this;

        deleteProfile = findViewById(R.id.btnDeleteProfile1);
        currentPassword = findViewById(R.id.inputPassowrd5);

            deleteProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (validatePassword()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setMessage("Are you sure want to delete your account ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                                    SessionManagement sessionManagement = new SessionManagement(context);

                                    int id = sessionManagement.getSession();
                                    String password = currentPassword.getText().toString();

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        DBHandler dbHandler = new DBHandler(context);
                                        boolean res =  dbHandler.checkPasswordWithID(id, password);

                                        if (res){
                                            boolean res1 = dbHandler.deleteUser(id);
                                            if (!res1) {
                                                SessionManagement sessionManagement = new SessionManagement(context);
                                                sessionManagement.deleteSession();
                                                Toast.makeText(getApplicationContext(), "User Deleted Successfully ! ", Toast.LENGTH_LONG).show();
                                                logOut();
                                            }
                                            else
                                                Toast.makeText(getApplicationContext(), "User Deletion Unsuccessful !", Toast.LENGTH_LONG).show();
                                        }else
                                            Toast.makeText(getApplicationContext(), "Please Check Your Password Again ! ", Toast.LENGTH_LONG).show();
                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });
    }



    public void logOut(){
        SessionManagement sessionManagement = new SessionManagement(DeleteProfile.this);
        sessionManagement.removeSession();
        goToMainPage();
    }

    public void goToMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean validatePassword(){
        String userCurrentPassword = currentPassword.getText().toString();

        if (userCurrentPassword.isEmpty()) {
            currentPassword.setError("Fields Cannot be Empty !");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(userCurrentPassword).matches()){
            currentPassword.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            return false;
        }
        else
            return true;
    }


}