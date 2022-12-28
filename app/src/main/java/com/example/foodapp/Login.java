package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SafeBrowsingResponse;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 simple letter
                    "(?=.*[A-Z])" +         //at least 1 capital letter
                    ".{8,}" +               //at least 8 characters
                    "$");

    private Button loginButtonInLogin;
    private EditText username, password;
    private TextView didntHaveAccount;
    DBHandler dbHandler;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButtonInLogin = (Button) findViewById(R.id.btnLogin2);
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPasswordLogin);
        didntHaveAccount = findViewById(R.id.txtDidntHaveAccount);
        dbHandler = new DBHandler(this);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX() >= password.getRight()-password.getCompoundDrawables()[right].getBounds().width()){
                        int selection  = password.getSelectionEnd();
                        if (passwordVisible){
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        loginButtonInLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = username.getText().toString();
                String userPassword = password.getText().toString();
                boolean res = dbHandler.login(userEmail, userPassword);

                if (validateEmail() && validatePassword()){
                    if (res) {
                        Toast.makeText(getApplicationContext(), "Login Successful ", Toast.LENGTH_LONG).show();
                        login();
                        //openProfile();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "User Not Found ! Check Username and Password !", Toast.LENGTH_LONG).show();                   }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        didntHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterPage();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check user is already logged in
        SessionManagement sessionManagement = new SessionManagement(Login.this);
        int userID = sessionManagement.getSession();

        if (userID != -1)
            openProfile();
    }

    public void openProfile(){
        Intent intent = new Intent(this, SideMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openRegisterPage(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    public boolean validatePassword(){
        String userPassword = password.getText().toString();

        if (userPassword.isEmpty()){
            password.setError("Fields Cannot be Empty !");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(userPassword).matches()){
            password.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            return false;
        }
        else
            return true;
    }


    public boolean validateEmail(){
        String userEmail = username.getText().toString();
        if (userEmail.isEmpty()){
            username.setError("Field Cannot be Empty !");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            username.setError("Please Enter Valid Email Address !");
            return false;
        }
        else {
            return true;
        }
    }

    public void login(){
        try {
            DBHandler db = new DBHandler(this);
            UserModel userModel = db.getLoggedUser(username.getText().toString());
            //System.out.println(userModel.getId());

            SessionManagement sessionManagement = new SessionManagement((Login.this));
            sessionManagement.saveSession(userModel);
        }catch (CursorIndexOutOfBoundsException ex){
            System.out.println(ex);
        }
        openProfile();
    }
}

//add loyalty customers