package com.example.votizen;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyAdapter extends ArrayAdapter {
    ArrayList<String> ward_list = new ArrayList<>(), party_name_list = new ArrayList<>(), candidate_name_list = new ArrayList<>(), promises_made_list = new ArrayList<>(), criminal_records_list = new ArrayList<>(), photo_list = new ArrayList<>(), symbol_list = new ArrayList<>();
    private final Activity context;

    public MyAdapter(Activity context, int textViewResourceId, ArrayList<String> ward_list, ArrayList<String> candidate_name_list,ArrayList<String> party_name_list, ArrayList<String> promises_made_list, ArrayList<String> criminal_records_list, ArrayList<String> photo_list, ArrayList<String> symbol_list) {
        super(context, textViewResourceId, ward_list);
        this.candidate_name_list = candidate_name_list;
        this.party_name_list = party_name_list;
        this.ward_list = ward_list;
        this.promises_made_list = promises_made_list;
        this.criminal_records_list = criminal_records_list;
        this.photo_list = photo_list;
        this.symbol_list = symbol_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_for_candidates_in_user_view, null, true);

//        TextView state_in_list = rowView.findViewById(R.id.state_in_list);
        TextView ward_in_grid = rowView.findViewById(R.id.promises_fulfilled_in_grid_view);
        TextView candidate_name_in_grid = rowView.findViewById(R.id.candidate_name_in_grid_view);
        TextView party_name_in_grid = rowView.findViewById(R.id.party_name_in_grid_view);
        TextView criminal_records_in_grid = rowView.findViewById(R.id.criminal_records_in_grid_view);
        TextView promises_made_in_grid = rowView.findViewById(R.id.promises_made_in_grid_view);
        ImageView candidate_image_in_grid = rowView.findViewById(R.id.candidate_image_in_grid_view);
        ImageView candidate_party_symbol_in_grid = rowView.findViewById(R.id.party_symbol_user);


//        state_in_list.setText(ward_in_list[position]);
        candidate_name_in_grid.setText("Name : " + ward_list.get(position));
        ward_in_grid.setText("Ward Number : " + candidate_name_list.get(position));
        party_name_in_grid.setText("Party : " + party_name_list.get(position));
        promises_made_in_grid.setText("Promises Made : " + promises_made_list.get(position));
        criminal_records_in_grid.setText("Criminal Records : " + criminal_records_list.get(position));
        Picasso.with(getContext()).load(photo_list.get(position)).into(candidate_image_in_grid);
        Picasso.with(getContext()).load(symbol_list.get(position)).into(candidate_party_symbol_in_grid);
        return rowView;
    }
}
