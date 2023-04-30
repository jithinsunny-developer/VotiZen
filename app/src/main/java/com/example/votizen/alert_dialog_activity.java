package com.example.votizen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class alert_dialog_activity extends AppCompatActivity {

    TextView  alert_text;
    String voted_ward_number,voted_candidate_name,voted_party_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog_activity);
        Intent i = getIntent();
        voted_ward_number = i.getStringExtra("WardNumber");
        voted_candidate_name = i.getStringExtra("CandidateVoted");
        voted_party_name = i.getStringExtra("PartyName");
        alert_text = findViewById(R.id.alert_text);
        alert_text.setText("Are you sure you want to vote " + voted_candidate_name + " ?");
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Are you sure you want to vote " + voted_candidate_name + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(alert_dialog_activity.this,"Selected Option: YES",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(alert_dialog_activity.this,"Selected Option: NO",Toast.LENGTH_SHORT).show();

                    }
                }).show();*/
    }
    public void YesOrNo(View V){
        switch(V.getId()){
            case R.id.Yes:
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                final DatabaseReference ref = db.getReference("Candidates").child(voted_candidate_name).child("live_poll");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long Votes = dataSnapshot.getValue(Long.class);
                        long NewVote = Votes+1;
                        ref.setValue(NewVote);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent i = new Intent(this,successful_vote_reg.class);
                i.putExtra("CandidateVoted",voted_candidate_name);
                i.putExtra("PartyName",voted_party_name);
                i.putExtra("WardNumber",voted_ward_number);
                finish();
                startActivity(i);

                break;
            case R.id.No:
                Intent intent = new Intent(this,showContestants.class);
                intent.putExtra("WardNumber", voted_ward_number);
                startActivity(intent);
                break;
        }
    }
}
