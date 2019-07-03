package com.example.resturantmenuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Activity_Grid_Item extends AppCompatActivity
{
    private TextView Item_name_full , Item_price_full;
    private ImageView Item_view_full;
    byte[] Item_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__grid__item);



        Item_name_full = findViewById(R.id.one_item_name_view_full);
        Item_price_full = findViewById(R.id.one_item_price_view_full);
        Item_view_full = findViewById(R.id.one_item_view_full);

        Intent intent = getIntent();
        Item_name_full.setText(intent.getStringExtra("Item_Name"));
        Item_price_full.setText(intent.getStringExtra("Item_Price"));

        Item_icon = intent.getByteArrayExtra("Item_View");


        Glide.with(Activity_Grid_Item.this)
                .asBitmap()
                .load(Item_icon)
                .into(Item_view_full);

    }
}
