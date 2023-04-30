package com.example.votizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class user_login extends AppCompatActivity {
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
    }
    public void ClickToContinue(View V){
        switch(V.getId()){
            case R.id.ClickToContinue:
                Toast.makeText(this, "Continuing", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, user_login_login.class));
                break;
        }
    }
}
