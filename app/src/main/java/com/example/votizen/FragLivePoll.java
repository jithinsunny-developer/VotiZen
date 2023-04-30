package com.example.votizen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragLivePoll extends android.support.v4.app.Fragment {
    public static ArrayList<String> ward_list = new ArrayList<String>();
    public static ArrayList<String> party_name_list = new ArrayList<String>();
    public static ArrayList<String> candidate_name_list = new ArrayList<String>();
    public static ArrayList<Long> live_poll_list = new ArrayList<Long>();
    CustomListAdapterPoll adapter;
    ListView lv;

    FragLivePoll(ArrayList<Long> live_poll_list, ArrayList<String> ward_list, ArrayList<String> party_name_list, ArrayList<String> candidate_name_list){
        this.party_name_list = party_name_list;
        this.ward_list = ward_list;
        this.candidate_name_list = candidate_name_list;
        this.live_poll_list = live_poll_list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_show_candidates_in_live_polls, container, false);
        lv = view.findViewById(R.id.live_poll_list);

        adapter = new CustomListAdapterPoll(getActivity(), live_poll_list, ward_list, candidate_name_list, party_name_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String SelectedCandidate = candidate_name_list.get(+position);
                System.out.println(SelectedCandidate);
                Toast.makeText(getActivity(), "Selected Candidate is: " + SelectedCandidate, Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(getActivity(), edit_candidate_details.class);
                i.putExtra("SelectedCandidate", SelectedCandidate);
                startActivity(i);*/

            }
        });


        return view;
    }
}
