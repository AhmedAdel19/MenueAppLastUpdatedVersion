package com.example.resturantmenuapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manage_Menue_Activity extends AppCompatActivity
{
    private Button go_add_category , go_add_item , go_show_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__menue_);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        go_add_category = findViewById(R.id.go_to_add_category);
        go_add_item = findViewById(R.id.go_to_add_item);
        go_show_categories = findViewById(R.id.go_to_all_Categories);


        go_add_category.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent add_category_intent = new Intent(Manage_Menue_Activity.this , Add_Category.class);
                startActivity(add_category_intent);

            }
        });


        go_add_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent add_item_intent = new Intent(Manage_Menue_Activity.this , Add_Item_Category.class);
                startActivity(add_item_intent);
            }
        });


        go_show_categories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent show_categories_intent = new Intent(Manage_Menue_Activity.this , Show_All_Categries_Activity.class);
                startActivity(show_categories_intent);
            }
        });

    }
}
