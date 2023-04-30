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

import java.util.Calendar;

public class SignUpUsers extends AppCompatActivity {

    EditText username,dob,aadharcard,mobilenumber,ward,state;
    int year,month,day;
    Calendar calendar;
    EditText UserDOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_users);
        username = findViewById(R.id.UserName);
        dob = findViewById(R.id.UserDOB);
        aadharcard = findViewById(R.id.AadharCardNumber);
        mobilenumber = findViewById(R.id.MobileNumber);
        ward = findViewById(R.id.ward);
        state = findViewById(R.id.state);
    }
    public void UserSignup(View V){
    final String user_name = username.getText().toString();
    final String user_dob = dob.getText().toString();
    final String user_aadhar= aadharcard.getText().toString();
    final String user_mobile_number = mobilenumber.getText().toString();
    final String user_state = state.getText().toString();
    final String user_ward = ward.getText().toString();



        if (TextUtils.isEmpty(user_name)) {
        Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
        return;
    }

        if (TextUtils.isEmpty(user_dob)) {
        Toast.makeText(getApplicationContext(), "Enter DOB ", Toast.LENGTH_SHORT).show();
        return;
    }
        if (TextUtils.isEmpty(user_aadhar)) {
            Toast.makeText(getApplicationContext(), "Enter Aadhar ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user_mobile_number)) {
            Toast.makeText(getApplicationContext(), "Enter Mobile ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user_state)) {
            Toast.makeText(getApplicationContext(), "Enter State ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user_ward)) {
            Toast.makeText(getApplicationContext(), "Enter Ward ", Toast.LENGTH_SHORT).show();
            return;
        }

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    final DatabaseReference myRef = database.getReference("Users").child(user_aadhar);

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
                myRef.child("aadhar").setValue(user_aadhar);
                myRef.child("dob").setValue(user_dob);
                myRef.child("mobile").setValue(user_mobile_number);
                myRef.child("state").setValue(user_state);
                myRef.child("ward").setValue(user_ward);

//                Intent i = new Intent(SignUpUsers.this, user_login.class);
//                startActivity(i);
                Toast.makeText(getApplicationContext(), "Account successfully made....Please Login", Toast.LENGTH_SHORT).show();
            }

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {



        }
    });

}
}
