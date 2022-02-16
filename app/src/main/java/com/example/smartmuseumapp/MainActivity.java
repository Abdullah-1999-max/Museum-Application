package com.example.smartmuseumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmuseumapp.Ticketing.TicketHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    private EditText editTextemail, editTextpassword;
    private TextView signup,forgetpassword;
    private Button signin;
    String uid;
    String user_name;
    private FirebaseAuth mAuth;
    DatabaseReference user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         user1 = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        signin = (Button) findViewById(R.id.login);
        signin.setOnClickListener(this);
        forgetpassword = (TextView) findViewById(R.id.forgetpass);
        forgetpassword.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                Intent myIntent = new Intent(v.getContext(), SignUp.class);
                startActivityForResult(myIntent,
                        0);
                break;
            case R.id.login:
                userlogin();
                break;
            case R.id.forgetpass:
                Intent i = new Intent(v.getContext(), ForgetPassword.class);
                startActivityForResult(i,
                        0);
                break;
        }
    }

    public  void userlogin() {

            String email = editTextemail.getText().toString();
            String password = editTextpassword.getText().toString();
            if (email.isEmpty()) {
                editTextemail.setError("Email is required");
                editTextemail.requestFocus();
                return;

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextemail.setError("Please enter a Valid Email");
                editTextemail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextpassword.setError("Password is required");
                editTextpassword.requestFocus();
                return;

            }
            if (password.length() < 6) {
                editTextpassword.setError("Min Password length is 6 characters");
                editTextpassword.requestFocus();
                return;

            }
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->

            {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        user1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.i("snapshot", "onCreate: "+snapshot);
                                if (snapshot.exists()) {

                                    UserModel model = snapshot.getValue(UserModel.class);
                                    TicketHelper.setUserModel(model);

//
//                                    if (model != null)
//                                    {
//                                        uid="Ga4JJY2aExgCIGohQi5kYHWFWCw1";
//                                        user_name=model.getFullname();
//                                        Log.i("user", "onCreate: "+user_name);
//                                    }

                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("user_id", uid);
                        bundle1.putString("user_name", user_name);
                        Intent myIntent = new Intent(MainActivity.this, NavbarActivity.class);
                        myIntent.putExtras(bundle1);
                        startActivityForResult(myIntent,
                                0);
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Failed to login! User is nor Registered", Toast.LENGTH_LONG).show();
                }
            });



    }
}