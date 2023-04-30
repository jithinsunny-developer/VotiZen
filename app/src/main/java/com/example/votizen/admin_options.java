package com.example.votizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class admin_options extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,login_option.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminoptions);
    }
    public void AdminOptions(View V){
        switch(V.getId()){
            case R.id.choose_live_polls:
                Toast.makeText(this, "Live Polls Chosen", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, live_polls.class));
                break;
            case R.id.choose_candidates:
                Toast.makeText(this, "Candidate Details Chosen", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, add_or_edit_candidates.class));
                break;
        }
    }
}
