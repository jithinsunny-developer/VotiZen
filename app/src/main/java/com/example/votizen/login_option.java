package com.example.votizen;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class login_option extends AppCompatActivity {

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(this,MainActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_option);
    }

    public void ChooseLoginOption(View V){
        switch(V.getId()){
            case R.id.OptionLoginAdmin:
                Toast.makeText(this, "Admin Is Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,admin_login.class));
                break;
            case R.id.OptionLoginUser:
                Toast.makeText(this, "User Is Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,user_login.class));
                break;
            case R.id.gotowebsite:
                Toast.makeText(this, "Taking You To VOTIZEN Website...", Toast.LENGTH_SHORT).show();
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bluesail.co.in/")));
                    }
                },1000L);
        }
    }
}
