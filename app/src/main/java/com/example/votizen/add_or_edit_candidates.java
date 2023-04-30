package com.example.votizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class add_or_edit_candidates extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,admin_options.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidateoptions);
    }
    public void CandidateAddOrEdit(View V){
        switch (V.getId()){
            case R.id.add_candidate:
                Toast.makeText(this, "Add_Candidates Is Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,add_candidates.class));
                break;
            case R.id.edit_candidate:
                Toast.makeText(this, "Edit_Candidates Is Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,edit_candidates.class));
                break;
        }
    }

}
