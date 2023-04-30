package com.example.votizen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showContestants extends Activity {

    String ward_number_from_search;
    Candidate can;
    ArrayList<String> ward_list_searched = new ArrayList<>(),candidate_name_list_searched = new ArrayList<>(),party_name_list_searched = new ArrayList<>(),promises_made_list_searched = new ArrayList<>(),criminal_records_list_searched = new ArrayList<>(), photo_list_searched=new ArrayList<>(), symbol_list_searched = new ArrayList<>();
    ArrayList<String> ward_list = new ArrayList<>(),candidate_name_list = new ArrayList<>(),party_name_list = new ArrayList<>(),promises_made_list = new ArrayList<>(),criminal_records_list = new ArrayList<>(), photo_list = new ArrayList<>(), symbol_list = new ArrayList<>();
    String Selected;
    int POS;
    GridView simpleList;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,user_choose_state_and_ward.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidateslistforuser);
        Intent i = getIntent();
        ward_number_from_search = i.getStringExtra("WardNumber");

        simpleList = findViewById(R.id.gridView);
        FirebaseDatabase databaseToShow = FirebaseDatabase.getInstance();
        DatabaseReference reference = databaseToShow.getReference("Candidates");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ward_list.clear();
                party_name_list.clear();
                candidate_name_list.clear();
                promises_made_list.clear();
                criminal_records_list.clear();
                photo_list.clear();
                symbol_list.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot childSnapshot : children) {
                    can = childSnapshot.getValue(Candidate.class);
                    System.out.println(can);
                    String Ward_Number = can.ward_number;
                    String Candidate_Name = can.first_name + " " + can.last_name;
                    String Party_Name = can.party_name;
                    String Promises_Made = can.promises_made;
                    String Criminal_Records = can.criminal_records;
                    String photURL = can.photo;
                    String symbolURL = can.symbol;
                    ward_list.add(Ward_Number);
                    party_name_list.add(Party_Name);
                    candidate_name_list.add(Candidate_Name);
                    promises_made_list.add(Promises_Made);
                    criminal_records_list.add(Criminal_Records);
                    photo_list.add(photURL);
                    symbol_list.add(symbolURL);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ward_list_searched.clear();
                candidate_name_list_searched.clear();
                party_name_list_searched.clear();
                criminal_records_list_searched.clear();
                promises_made_list_searched.clear();
                photo_list_searched.clear();
                symbol_list_searched.clear();
                for (int j = 0; j < ward_list.size(); j++) {
                    if (ward_list.get(j).equals(ward_number_from_search)) {
                        ward_list_searched.add(ward_list.get(j));
                        candidate_name_list_searched.add(candidate_name_list.get(j));
                        party_name_list_searched.add(party_name_list.get(j));
                        promises_made_list_searched.add(promises_made_list.get(j));
                        criminal_records_list_searched.add(criminal_records_list.get(j));
                        photo_list_searched.add(photo_list.get(j));
                        symbol_list_searched.add(symbol_list.get(j));
                    }
                }
                if (ward_list_searched.size() == 0) {
                    Toast.makeText(showContestants.this, "There is no candidate from this ward....Please Check Ward Number again!!!", Toast.LENGTH_SHORT).show();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startActivity(new Intent(showContestants.this,user_choose_state_and_ward.class));
                        }

                    }, 3000L);
                } else {

                    MyAdapter myAdapter = new MyAdapter(showContestants.this, R.layout.layout_for_candidates_in_user_view, candidate_name_list_searched, ward_list_searched, party_name_list_searched, promises_made_list_searched, criminal_records_list_searched, photo_list_searched,symbol_list_searched);
                    simpleList.setAdapter(myAdapter);
                    simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
                            String SelectedCandidate = candidate_name_list.get(position);
                            Toast.makeText(getApplicationContext(), "Selected Candidate is: " + SelectedCandidate, Toast.LENGTH_SHORT).show();

                        }
                    });
                    simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            POS = position;
                            Selected = candidate_name_list_searched.get(position);
                            Toast.makeText(showContestants.this, "Selected : " + Selected, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), alert_dialog_activity.class);
                            i.putExtra("CandidateVoted", candidate_name_list_searched.get(POS));
                            i.putExtra("PartyName", party_name_list_searched.get(POS));
                            i.putExtra("WardNumber", ward_list_searched.get(POS));
                            startActivity(i);
//               AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//               builder.setMessage("Are you sure you want to vote " + candidate_name_list[position] + " ?").setPositiveButton("Yes", dialogClickListener)
//                       .setNegativeButton("No", dialogClickListener).show();

                        }
                    });

                }
            }

        }, 3000L);


    }
    public static class Candidate {

        public String state_name, first_name, last_name, party_name, ward_number, promises_fulfilled, promises_made, criminal_records, symbol, photo;

        public Candidate() {
        }
        public String getState_name() {
            return state_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getParty_name() {
            return party_name;
        }

        public String getWard_number() {
            return ward_number;
        }

        public String getPromises_fulfilled() {
            return promises_fulfilled;
        }

        public String getPromises_made() {
            return promises_made;
        }

        public String getCriminal_records() {
            return criminal_records;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public void setParty_name(String party_name) {
            this.party_name = party_name;
        }

        public void setWard_number(String ward_number) {
            this.ward_number = ward_number;
        }

        public void setPromises_fulfilled(String promises_fulfilled) {
            this.promises_fulfilled = promises_fulfilled;
        }

        public void setPromises_made(String promises_made) {
            this.promises_made = promises_made;
        }

        public void setCriminal_records(String criminal_records) {
            this.criminal_records = criminal_records;
        }
    }

    /*private void ShowAlertDialog(int POS) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        builder.setTitle("Confirm Alert")
                .setMessage("Are you sure you want to vote " + candidate_name_list[POS] + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Selected Option: YES",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Selected Option: NO",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }*/
    /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Toast.makeText(getApplicationContext(), "Voting Successful.....Thank You For Voting!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),successful_vote_reg.class);
                    i.putExtra("CandidateVoted",candidate_name_list[POS]);
                    i.putExtra("PartyName",party_name_list[POS]);
                    i.putExtra("WardNumber",ward_list[POS]);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Toast.makeText(getApplicationContext(), "No Is Pressed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };*/



}
