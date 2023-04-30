package com.example.votizen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class user_fingerprint_login extends AppCompatActivity {

    boolean isCancelPressed = false;
    String AadharCard;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,user_login_login.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprintverification);
        AadharCard = getIntent().getStringExtra("AadharCard");
        /*if(isCancelPressed == false) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(new Intent(user_fingerprint_login.this, user_choose_state_and_ward.class));
                }

            }, 5000L);
        }*/
        Handler mmHandler = new Handler();
        mmHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(user_fingerprint_login.this, "Fingerprint Successfully Verified", Toast.LENGTH_SHORT).show();
            }
        }, 5000L);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(user_fingerprint_login.this, user_choose_state_and_ward.class);
                i.putExtra("AadharCard",AadharCard);
                startActivity(i);
            }
        }, 6000L);
    }
    public void FingerprintCancelButton(View V){
        isCancelPressed = true;
        startActivity(new Intent(this,user_login_login.class));
    }
}
