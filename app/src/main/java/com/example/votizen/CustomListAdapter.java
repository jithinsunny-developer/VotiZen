package com.example.votizen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {
    final Activity context;
    ArrayList<String> candidate_name_list = new ArrayList<>();
    ArrayList<String> ward_list = new ArrayList<>();
    ArrayList<String> party_name_list = new ArrayList<>();
    ArrayList<String> state_list = new ArrayList<>();
//    private final Integer[] imgid;

    public CustomListAdapter(Activity context, ArrayList<String> state_list, ArrayList<String> ward_list, ArrayList<String> candidate_name_list, ArrayList<String> party_name_list) {
        super(context, R.layout.cadidates_list, state_list);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.state_list=state_list;
        this.candidate_name_list=candidate_name_list;
        this.ward_list=ward_list;
        this.party_name_list=party_name_list;
//        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cadidates_list, null,true);

        TextView state_in_list = rowView.findViewById(R.id.state_in_list);
        TextView ward_in_list = rowView.findViewById(R.id.ward_in_list);
        TextView candidate_name_in_list = rowView.findViewById(R.id.candidate_name_in_list);
        TextView party_name_in_list = rowView.findViewById(R.id.party_name_in_list);

        state_in_list.setText(state_list.get(position));
        ward_in_list.setText(ward_list.get(position));
        candidate_name_in_list.setText(candidate_name_list.get(position));
        party_name_in_list.setText(party_name_list.get(position));
//        imageView.setImageResource(imgid[position]);


        return rowView;

    }
}

