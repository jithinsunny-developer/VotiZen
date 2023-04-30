package com.example.votizen;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class live_polls extends AppCompatActivity {
    public static ArrayList<String> ward_list = new ArrayList<String>();
    public static ArrayList<String> party_name_list = new ArrayList<String>();
    public static ArrayList<String> candidate_name_list = new ArrayList<String>();
    public static ArrayList<Long> live_poll_list = new ArrayList<Long>();
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    CandidatePoll can;
    SearchView searchViewLivePoll;
    String searchViewText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livepolls);
        searchViewLivePoll = findViewById(R.id.searchViewInLivePoll);
        searchViewLivePoll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchViewText = s;
                FirebaseDatabase databaseToShow = FirebaseDatabase.getInstance();
                DatabaseReference reference = databaseToShow.getReference("Candidates");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ward_list.clear();
                        live_poll_list.clear();
                        party_name_list.clear();
                        candidate_name_list.clear();
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot childSnapshot : children) {
                            can = childSnapshot.getValue(CandidatePoll.class);
                            String State_Name = can.state_name;
                            String Ward_Number = can.ward_number;
                            String Candidate_Name = can.first_name + " " + can.last_name;
                            String Party_Name = can.party_name;
                            long Live_Poll = can.live_poll;
                            if(State_Name.equals(searchViewText)){
                            ward_list.add(Ward_Number);
                            party_name_list.add(Party_Name);
                            candidate_name_list.add(Candidate_Name);
                            live_poll_list.add(Live_Poll);}
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
                        fragment = new FragLivePoll(live_poll_list,ward_list,party_name_list,candidate_name_list);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.FrameLayoutToViewPoll, fragment);
                        fragmentTransaction.commit();
                    }

                }, 3000L);
                return false;
            }
        });
    }
}
