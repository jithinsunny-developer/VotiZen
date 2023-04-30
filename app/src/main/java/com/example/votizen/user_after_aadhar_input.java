package com.example.votizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class user_after_aadhar_input extends AppCompatActivity {

    String CalculatedPIN,AadharCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);
        Intent i = getIntent();
        CalculatedPIN = i.getStringExtra("CalculatedPIN");
        AadharCard = i.getStringExtra("AadharCard");
    }
    public void ChooseVerificationType(View V){
        switch(V.getId()){
            case R.id.choose_fingerprint:
                Toast.makeText(this, "You have preferred Fingerprint Verfication", Toast.LENGTH_SHORT).show();
                Intent intentFingerprint = new Intent(this,user_fingerprint_login.class);
                intentFingerprint.putExtra("AadharCard",AadharCard);
                startActivity(intentFingerprint);
                break;
            case R.id.choose_otp:
                Toast.makeText(this, "You have preferred OTP Verfication", Toast.LENGTH_SHORT).show();
                Intent intentOTP = new Intent(this,user_otp_login.class);
                intentOTP.putExtra("AadharCard",AadharCard);
                startActivity(intentOTP);
                break;
            case R.id.choose_pin:
                Toast.makeText(this, "You have preferred PIN Verfication", Toast.LENGTH_SHORT).show();
                Intent intentPIN = new Intent(this,user_pin_login.class);
                intentPIN.putExtra("CalculatedPIN",CalculatedPIN);
                intentPIN.putExtra("AadharCard",AadharCard);
                startActivity(intentPIN);
                break;
        }
    }
}
