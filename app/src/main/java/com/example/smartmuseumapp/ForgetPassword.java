package com.example.smartmuseumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity  implements View.OnClickListener {
    private EditText Email;
    private Button restpassword;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Email=(EditText) findViewById(R.id.email);
        restpassword=(Button) findViewById(R.id.resetpassword);
        auth=FirebaseAuth.getInstance();
        restpassword.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        restPassword();
    }
    public void restPassword(){
        String email = Email.getText().toString().trim();
        if (email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please provide a Valid Email");
            Email.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task ->

        {
            if (task.isSuccessful()) {
                Toast.makeText(ForgetPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                startActivity(new Intent((ForgetPassword.this),MainActivity.class));


            } else {
                Toast.makeText(ForgetPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

}