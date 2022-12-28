package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 simple letter
                    "(?=.*[A-Z])" +         //at least 1 capital letter
                    ".{8,}" +               //at least 8 characters
                    "$");

    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("[0][0-9]{9}");

    private Button registerButton;
    private EditText fullName, email, mobile, birthDate, password, rePassword;
    private DBHandler dbHandler;
    private Context context;
    private TextView alreadyHaveAccount;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Calender View
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DAY_OF_MONTH);

        alreadyHaveAccount = findViewById(R.id.txtAlreadyHaveAccount);

        registerButton = findViewById(R.id.btnRegister);
        fullName = findViewById(R.id.inputFullName);
        email = findViewById(R.id.inputEmail);
        mobile = findViewById(R.id.inputMobile);
        birthDate = findViewById(R.id.inputBirthDate);
        password = findViewById(R.id.inputPassowrdReg);
        rePassword = findViewById(R.id.inputReEnterPasswordReg);

        context = this;
        dbHandler = new DBHandler(context);

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


        rePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX() >= rePassword.getRight()-rePassword.getCompoundDrawables()[right].getBounds().width()){
                        int selection  = rePassword.getSelectionEnd();
                        if (passwordVisible){
                            rePassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            rePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else {
                            rePassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_24, 0);
                            rePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        rePassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });



        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        month = month+1;
                        String day = date+"/"+month+"/"+year;
                        birthDate.setText(day);
                    }
                }, year, month, date);
                dialog.show();
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoginPage();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String userFullName = fullName.getText().toString();
               String userEmail = email.getText().toString();
               String userMobile = mobile.getText().toString();
               String userBirthDate = birthDate.getText().toString();
               String userPassword = password.getText().toString();
               long regDate = System.currentTimeMillis();
               String type = "admin";

                //String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

               if (validateName() && validateEmail() && validateMobile() && validBirthDate() && validatePassword()){
                   UserModel userModel = new UserModel(userFullName, userEmail, userMobile, userBirthDate, userPassword, regDate, type);

                   if (!dbHandler.checkUsername(userEmail)){
                       boolean result = dbHandler.registerUser(userModel);

                       if (result) {
                           Toast.makeText(getApplicationContext(), "Registration Successful !", Toast.LENGTH_LONG).show();
                           goToLoginPage();
                       }else
                           Toast.makeText(getApplicationContext(), "Registration Unsuccessful !", Toast.LENGTH_LONG).show();
                   }else {
                       Toast.makeText(getApplicationContext(), "User Already Existed! Please Login", Toast.LENGTH_LONG).show();
                       email.setError("Email is Already Taken !");
                   }
               }
               else
                   Toast.makeText(getApplicationContext(), "Please Fill Required Fields Correctly !", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*public void goToMenuPage(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }*/

    public void goToLoginPage(){
        Intent intent = new Intent(this, Login.class );
        startActivity(intent);
    }


    public boolean validateEmail(){
        String userEmail = email.getText().toString();
        if (userEmail.isEmpty()){
            email.setError("Field Cannot be Empty !");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Please Enter Valid Email Address !");
            return false;
        }
        else {
            return true;
        }
    }


    public boolean validatePassword(){
        String userPassword = password.getText().toString();
        String userRePassword = rePassword.getText().toString();

        if (userPassword.isEmpty() || userRePassword.isEmpty()) {
            password.setError("Fields Cannot be Empty !");
            rePassword.setError("Fields Cannot be Empty !");
            return false;
        }
        else if (!userPassword.equals(userRePassword)){
            password.setError("Password Mismatch !");
            rePassword.setError("Password Mismatch !");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(userPassword).matches() || !PASSWORD_PATTERN.matcher(userRePassword).matches()){
            password.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            rePassword.setError("Password Must Contain Minimum 8 characters, At least One Digit and At least One Letter");
            return false;
        }
        else
            return true;
    }


    public boolean validateName(){
        String userFullName = fullName.getText().toString();

        if (userFullName.isEmpty()) {
            fullName.setError("Field Cannot be Empty !");
            return false;
        }
        else
            return true;
    }


    public boolean validateMobile(){
        String userMobile = mobile.getText().toString();

        if (userMobile.isEmpty()) {
            mobile.setError("Field Cannot be Empty !");
            return false;
        }
        else if (!MOBILE_PATTERN.matcher(userMobile).matches()) {
            mobile.setError("Please Enter a Valid Mobile Number !");
            return false;
        }
        else
            return true;
    }

    public boolean validBirthDate(){
        String userBirthDate = birthDate.getText().toString();

        if (userBirthDate.isEmpty()) {
            birthDate.setError("Field Cannot be Empty !");
            return false;
        }
        else {
            birthDate.setError(null);
            return true;
        }
    }

}
