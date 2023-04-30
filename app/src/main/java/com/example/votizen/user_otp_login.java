package com.example.votizen;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.Math;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*import kong.unirest.HttpResponse;
import kong.unirest.Unirest;*/
//import com.mashape.unirest.http.*;
//import com.mashape.unirest.http.exceptions.UnirestException;



public class user_otp_login extends AppCompatActivity {
    String auth_key = "286749AXXXYbVaD5d3c18a1";
    String mobile_number;
    int sent_otp;
    String urlSend,urlVerify;
    String AadharCard;
    EditText otp_written;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,user_login_login.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        sent_otp = (int)(Math.random() * 10000);
        otp_written = findViewById(R.id.otp_obtained);
        Intent i = getIntent();
        AadharCard = i.getStringExtra("AadharCard");


        FirebaseDatabase databaseToShow = FirebaseDatabase.getInstance();
        DatabaseReference reference = databaseToShow.getReference("Users").child(AadharCard).child("mobile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                mobile_number = val;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                urlSend = "https://control.msg91.com/api/sendotp.php?otp="+sent_otp+"&mobile="+mobile_number+"&authkey="+auth_key;
                urlVerify = "https://control.msg91.com/api/verifyRequestOTP.php?authkey="+auth_key+"&mobile="+mobile_number+"&otp="+sent_otp;
                RequestQueue queue1 = Volley.newRequestQueue(user_otp_login.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSend,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(("Didn't work"));
                    }
                });

                queue1.add(stringRequest);
            }

        }, 3000L);



    }
    public void OTPCancelButton(View V){
        startActivity(new Intent(this,user_login_login.class));
    }
    public void OTPSubmitButton(View V){
        if(otp_written.getText().toString().equals(Integer.toString(sent_otp))) {
            Intent i = new Intent(this, user_choose_state_and_ward.class);
            i.putExtra("AadharCard",AadharCard);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
        }
    }
}
