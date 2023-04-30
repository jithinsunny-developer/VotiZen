package com.example.votizen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class user_choose_state_and_ward extends AppCompatActivity implements View.OnClickListener{

    EditText enter_ward_number,enter_state_name;

    Button user_submit_state_and_ward;
    String AadharCard;
    User val;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,user_login_login.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stateandwardnumber);
        AadharCard = getIntent().getStringExtra("AadharCard");
        System.out.println(AadharCard);
        enter_ward_number = findViewById(R.id.user_enter_ward_number);
        enter_state_name = findViewById(R.id.user_enter_state_name);
//        choose_state_spinner = findViewById(R.id.choose_state_spinner);
        user_submit_state_and_ward = findViewById(R.id.user_submit_state_and_ward);
        user_submit_state_and_ward.setOnClickListener(this);

//        List<String> states = new ArrayList<>();
//        states.add(0,"Select State");
//        states.add("Andra Pradesh");
//        states.add("Arunachal Pradesh");
//        states.add("Assam");
//        states.add("Bihar");
//        states.add("Goa");
//        states.add("Gujurat");
//        states.add("Haryana");
//        states.add("Himachal Pradesh");
//        states.add("Jammu And Kashmir");
//        states.add("Jharkhand");
//        states.add("Karnataka");
//        states.add("Kerala");
//        states.add("Madya Pradesh");
//        states.add("Maharashtra");
//        states.add("Manipur");
//        states.add("Meghalaya");
//        states.add("Mizoram");
//        states.add("Nagaland");
//        states.add("Orissa");
//        states.add("Punjab");
//        states.add("Rajasthan");
//        states.add("Sikkim");
//        states.add("Tamil Nadu");
//        states.add("Telangana");
//        states.add("Tripura");
//        states.add("Uttaranchal");
//        states.add("Uttar Pradesh");
//        states.add("West Bengal");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        choose_state_spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.user_submit_state_and_ward){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference refer = database.getReference("Users").child(AadharCard);
            refer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    val = dataSnapshot.getValue(User.class);



                    System.out.println(val.getState_()+ " " + val.getWard_());
                    if(enter_ward_number.getText().toString().equals(val.ward) && enter_state_name.getText().toString().equals(val.state)){
                        Intent i = new Intent(user_choose_state_and_ward.this,showContestants.class);
                        i.putExtra("WardNumber",enter_ward_number.getText().toString());
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(user_choose_state_and_ward.this, "Please enter YOUR StateName and WardNumber", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
