package com.example.resturantmenuapp;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private Button go_menue , manage_menue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        go_menue = findViewById(R.id.go_to_menue_btn);
        manage_menue = findViewById(R.id.go_to_settings_btn);

        go_menue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent go_to_menue_intent = new Intent(MainActivity.this , Items_List_Activity.class);
                startActivity(go_to_menue_intent);
            }
        });

        manage_menue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent manage_menue_intent = new Intent(MainActivity.this , Manage_Menue_Activity.class);
                startActivity(manage_menue_intent);

            }
        });

    }

}
