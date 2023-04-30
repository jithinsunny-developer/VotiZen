package com.example.votizen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class edit_candidates extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static ArrayList<String> ward_list = new ArrayList<String>();
    public static ArrayList<String> party_name_list = new ArrayList<String>();
    public static ArrayList<String> candidate_name_list = new ArrayList<String>();
    public static ArrayList<String> state_list = new ArrayList<String>();
    Spinner choose_state_to_edit_candidate;
    SearchView searchView;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Candidate can;
    String searchViewText;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,add_or_edit_candidates.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcandidate);
        choose_state_to_edit_candidate = findViewById(R.id.choose_state_to_edit_candidate);
        searchView = findViewById(R.id.searchView);

        choose_state_to_edit_candidate.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> states = new ArrayList<>();
        states.add(0,"Select State");
        states.add("Andra Pradesh");
        states.add("Arunachal Pradesh");
        states.add("Assam");
        states.add("Bihar");
        states.add("Goa");
        states.add("Gujurat");
        states.add("Haryana");
        states.add("Himachal Pradesh");
        states.add("Jammu And Kashmir");
        states.add("Jharkhand");
        states.add("Karnataka");
        states.add("Kerala");
        states.add("Madhya Pradesh");
        states.add("Maharashtra");
        states.add("Manipur");
        states.add("Meghalaya");
        states.add("Mizoram");
        states.add("Nagaland");
        states.add("Orissa");
        states.add("Punjab");
        states.add("Rajasthan");
        states.add("Sikkim");
        states.add("Tamil Nadu");
        states.add("Telangana");
        states.add("Tripura");
        states.add("Uttaranchal");
        states.add("Uttar Pradesh");
        states.add("West Bengal");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        choose_state_to_edit_candidate.setAdapter(dataAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchViewText = newText;
                FirebaseDatabase databaseToShow = FirebaseDatabase.getInstance();
                DatabaseReference reference = databaseToShow.getReference("Candidates");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ward_list.clear();
                        state_list.clear();
                        party_name_list.clear();
                        candidate_name_list.clear();
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot childSnapshot : children) {
                            can = childSnapshot.getValue(Candidate.class);
                            String Ward_Number = can.ward_number;
                            String Candidate_Name = can.first_name + " " + can.last_name;
                            String Party_Name = can.party_name;
                            String State = can.state_name;
                            String photoURL = can.photo;
                            String symbolURL = can.symbol;

                            if(Ward_Number.equals(searchViewText)){
                                System.out.println(photoURL+ ":" + symbolURL);
                                ward_list.add(Ward_Number);
                                party_name_list.add(Party_Name);
                                candidate_name_list.add(Candidate_Name);
                                state_list.add(State);}
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
                        fragment = new FragCandidatesList(state_list,ward_list,party_name_list,candidate_name_list);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.FrameLayoutToViewParticipants, fragment);
                        fragmentTransaction.commit();
                    }

                }, 3000L);

                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        public String getParty_name() {
            return party_name;
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

}
