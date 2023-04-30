package com.example.votizen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupAdmins extends AppCompatActivity {

    EditText username,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admins);
        username=findViewById(R.id.Name);
        pass = findViewById(R.id.Password);
    }
    public void Signup(View v){
        final String user_name = username.getText().toString();
        final String pass_club = pass.getText().toString();


        if (TextUtils.isEmpty(user_name)) {
            Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass_club)) {
            Toast.makeText(getApplicationContext(), "Enter Password ", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();


        final DatabaseReference myRef = database.getReference("Admins").child(user_name);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "Account Exists", Toast.LENGTH_SHORT).show();

                }
                else

                {

                    myRef.child("username").setValue(user_name);
                    myRef.child("password").setValue(pass_club);

                    Intent i = new Intent(SignupAdmins.this, admin_login.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Account successfully made....Please Login", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });

    }
}

