package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 simple letter
                    "(?=.*[A-Z])" +         //at least 1 capital letter
                    ".{8,}" +               //at least 8 characters
                    "$");

    Button saveChanges;
    TextView currentPassword, newPassword, reEnterPassword;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = this;

        saveChanges = findViewById(R.id.btnChangePassword1);
        currentPassword = findViewById(R.id.inputPassowrd);
        newPassword = findViewById(R.id.inputPassowrd4);
        reEnterPassword = findViewById(R.id.inputPassowrd3);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validatePassword()){

                    SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                    SessionManagement sessionManagement = new SessionManagement(context);

                    int id = sessionManagement.getSession();
                    String currPass = currentPassword.getText().toString();
                    String newPass = newPassword.getText().toString();

                    DBHandler dbHandler = new DBHandler(context);
                    boolean res = dbHandler.checkPasswordWithID(id,currPass);

                    if (res){
                        boolean res1 = dbHandler.changePassword(id,newPass);
                        if (res1)
                            Toast.makeText(getApplicationContext(), "Password Update Unsuccessful ! ", Toast.LENGTH_LONG).show();
                        else{
                            Toast.makeText(getApplicationContext(), "Password Changed Successfully ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ChangePassword.this, UserAccount.class);
                            startActivity(intent);
                        }
                    }else
                        Toast.makeText(getApplicationContext(), "Please Check Your Password Again ! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean validatePassword(){
        String userCurrentPassword = currentPassword.getText().toString();
        String userPassword = newPassword.getText().toString();
        String userRePassword = reEnterPassword.getText().toString();

        if (userPassword.isEmpty() || userRePassword.isEmpty() || userCurrentPassword.isEmpty()) {
            currentPassword.setError("Fields Cannot be Empty !");
            newPassword.setError("Fields Cannot be Empty !");
            reEnterPassword.setError("Fields Cannot be Empty !");
            return false;
        }
        else if (!userPassword.equals(userRePassword)){
            newPassword.setError("Password Mismatch !");
            reEnterPassword.setError("Password Mismatch !");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(userPassword).matches() || !PASSWORD_PATTERN.matcher(userRePassword).matches() || !PASSWORD_PATTERN.matcher(userCurrentPassword).matches()){
            currentPassword.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            newPassword.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            reEnterPassword.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            return false;
        }
        else
            return true;
    }
}