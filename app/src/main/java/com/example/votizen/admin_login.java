package com.example.votizen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

public class admin_login extends AppCompatActivity {

    EditText AdminLoginEmail, AdminLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        AdminLoginEmail = findViewById(R.id.AdminLoginEmail);
        AdminLoginPassword = findViewById(R.id.AdminLoginPassword);
    }

    public void AdminLoginButtonPressed(View V) {
        switch (V.getId()) {
            case R.id.AdminLoginButton:
                Toast.makeText(this, "Login Is Pressed", Toast.LENGTH_SHORT).show();
                final String username = AdminLoginEmail.getText().toString();
                final String password = AdminLoginPassword.getText().toString();


                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password ", Toast.LENGTH_SHORT).show();
                    return;
                }


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref1 = ref.child("Admins");
                DatabaseReference ref2 = ref1.child(username);
                DatabaseReference ref3 = ref2.child("password");


                ref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String value = dataSnapshot.getValue(String.class);

                        if (value.equals(password)) {

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(admin_login.this,admin_options.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Input", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Snackbar.make(admin_login.this,""+databaseError.getMessage(),Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(admin_login.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        }
    }
}

