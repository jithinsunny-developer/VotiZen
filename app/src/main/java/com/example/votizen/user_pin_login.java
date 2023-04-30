package com.example.votizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class user_pin_login extends AppCompatActivity {
    String PIN,AadharCard;
    EditText pin_written;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,user_login_login.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinnumber);
        Intent i =getIntent();
        PIN =i.getStringExtra("CalculatedPIN");
        AadharCard = i.getStringExtra("AadharCard");
        pin_written=findViewById(R.id.pin_written);
    }
    public void PINCancelButton(View V){
        startActivity(new Intent(this,user_login_login.class));
    }
    public void PINSubmitButton(View V){

        if(PIN.equals(pin_written.getText().toString())) {
            Intent i = new Intent(this, user_choose_state_and_ward.class);
            i.putExtra("AadharCard",AadharCard);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Please Enter Correct PIN", Toast.LENGTH_SHORT).show();
        }
    }
}
