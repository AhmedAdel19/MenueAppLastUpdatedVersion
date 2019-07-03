package com.example.resturantmenuapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Category_Options_Activity extends AppCompatActivity
{
    private Button show_cat_items , delete_cat;
    private ArrayList<Category> all_cat_item_list;
    private ArrayList<Integer> Categor_Ids_List;
    public  static sqliteHelper sqlHelper;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__options_);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        show_cat_items = findViewById(R.id.show_categoey_items_btn);
        delete_cat = findViewById(R.id.delete_category_btn);

        all_cat_item_list = new ArrayList<>();
        Categor_Ids_List = new ArrayList<>();

        Intent intent = getIntent();
        position = intent.getIntExtra("category_position" , 0);

        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);

        try
        {
            Cursor categoryCursor = sqlHelper.getData("SELECT * FROM CATEGORIES");
            all_cat_item_list.clear();
            if (categoryCursor.moveToFirst()) {
                do{
                    int cat_id = categoryCursor.getInt(0);
                    Categor_Ids_List.add(cat_id);
                }while (categoryCursor.moveToNext());
            }
            //----------------------------------------
        }
        catch (Exception e)
        {

        }

        show_cat_items.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent cat_items_intent = new Intent(Category_Options_Activity.this , Show_All_Category_Items_Activity.class);
                cat_items_intent.putExtra("cat_position" , position);
                startActivity(cat_items_intent);
            }
        });

        delete_cat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    sqlHelper.deleteCategoryRecord(""+Categor_Ids_List.get(position)+"");
                    Intent  go_back_categories = new Intent(Category_Options_Activity.this , Show_All_Categries_Activity.class);
                    startActivity(go_back_categories);

                }
                catch (Exception e)
                {

                }
            }
        });
    }
}
