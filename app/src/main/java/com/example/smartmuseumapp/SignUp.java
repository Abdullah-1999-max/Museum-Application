package com.example.smartmuseumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText fullname, Phone, Email, Password;
    private TextView already;
    private Button register;
    private FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        register = (Button) findViewById(R.id.btn_login);
        register.setOnClickListener(this);
        already = (TextView) findViewById(R.id.already);
        already.setOnClickListener(this);


        fullname = (EditText) findViewById(R.id.uname);
        Phone = findViewById(R.id.phone);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                registeruser();

                break;
            case R.id.already:
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(i,
                        0);
                break;
        }
    }

    public void registeruser() {
        String Name = fullname.getText().toString();
        String Number = Phone.getText().toString();
        String Pass = Password.getText().toString();
        String mEmail = Email.getText().toString();
        if (Name.isEmpty()) {
            fullname.setError("Full Name is required");
            fullname.requestFocus();
            return;

        }
        if (Number.isEmpty()) {
            Phone.setError("Phoneno is required");
            Phone.requestFocus();
            return;

        }
        if (mEmail.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            Email.setError("Please Provide Valid Email");
            Email.requestFocus();
            return;
        }
        if (Pass.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return;

        }
        if (Pass.length() < 6) {
            Password.setError("Min Password length should be 6 characters");
            Password.requestFocus();
            return;

        }


        mAuth.createUserWithEmailAndPassword(mEmail, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("Fullname", Name);
                map.put("phone", Number);
                map.put("email", mEmail);
                map.put("UID", FirebaseAuth.getInstance().getCurrentUser().getUid());

                if (task.isSuccessful()) {
                    ref.child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("DataAdded", "onComplete: ");
                                        Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUp.this, MainActivity.class));
                                        finish();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("RealError", "onFailure: "+e.getMessage());
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("AuthErro", "onFailure: "+e.getMessage());
                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        mAuth.createUserWithEmailAndPassword(mEmail,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    User user = new User(Name, Number, mEmail, Pass);
//                    FirebaseDatabase.getInstance().getReference("Users")
//                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(SignUp.this,MainActivity.class));
//                                finish();
//
//                            } else {
//                                Toast.makeText(SignUp.this, "Registration Failed!Try Again", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(SignUp.this, "Registration Failed!", Toast.LENGTH_LONG).show();
//                }
//            }
//
//
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("TAG", "onFailure: "+e.getMessage());
//            }
//        });


    }
}