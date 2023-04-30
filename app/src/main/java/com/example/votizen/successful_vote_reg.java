package com.example.votizen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class successful_vote_reg extends AppCompatActivity {

    TextView vote_acknowledge;

    SimpleDateFormat dfDateTime  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    int year = randBetween(2018, 2020);// Here you can set Range of years you need
    int month = randBetween(0, 11);
    int hour = randBetween(9, 22); //Hours will be displayed in between 9 to 22
    int min = randBetween(0, 59);
    int sec = randBetween(0, 59);






    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successvotereg);
        Intent i = getIntent();
        final int transactionID = (int)(Math.random() * 100000);
        String voted_ward_number = i.getStringExtra("WardNumber");
        String voted_candidate_name = i.getStringExtra("CandidateVoted");
        String voted_party_name = i.getStringExtra("PartyName");

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = randBetween(1, gc.getActualMaximum(gc.DAY_OF_MONTH));

        gc.set(year, month, day, hour, min,sec);


        vote_acknowledge = findViewById(R.id.vote_acknowledge);
        Toast.makeText(this, "Last Sync: " + dfDateTime.format(gc.getTime()), Toast.LENGTH_SHORT).show();
        vote_acknowledge.setText("Your Vote Has Been Successfully Registered To " + voted_candidate_name + " - " + voted_party_name + " Party of Ward Number " + voted_ward_number);
        Handler mmHandler = new Handler();
        mmHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(successful_vote_reg.this, "Transaction ID : " + transactionID, Toast.LENGTH_SHORT).show();
            }
        },2000L);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(successful_vote_reg.this,user_login.class));
            }

        }, 10000L);

    }
    public int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
