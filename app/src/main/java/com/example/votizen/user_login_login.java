package com.example.votizen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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

public class user_login_login extends AppCompatActivity {

    EditText AadharNumber;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,user_login.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aadharnumberinput);
        AadharNumber = findViewById(R.id.UserAadhar);
    }

    public void SubmitAadhar(View V){
        switch(V.getId()){
            case R.id.UserSubmit:
                Toast.makeText(this, "Submit Is Selected", Toast.LENGTH_SHORT).show();
                final String username = AadharNumber.getText().toString();


                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Aadhar Card Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(username.length()!=12){
                    Toast.makeText(getApplicationContext(), "Wrong Aadhar Card Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String AadharCard = AadharNumber.getText().toString();
                    char A = AadharCard.charAt(2);
                    char B = AadharCard.charAt(5);
                    char C = AadharCard.charAt(8);
                    char D = AadharCard.charAt(11);
                    String CalculatedPIN = "" + A + B + C + D;
                    Intent i =new Intent(getApplicationContext(), user_after_aadhar_input.class);
                    i.putExtra("CalculatedPIN",CalculatedPIN);
                    i.putExtra("AadharCard",AadharCard);
                    startActivity(i);
                }
                break;
        }
    }

}
