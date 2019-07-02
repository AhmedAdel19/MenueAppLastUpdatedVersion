package com.example.resturantmenuapp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class Show_All_Categries_Activity extends AppCompatActivity
{
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView_categories;
    ArrayList<Integer> Categor_Ids_List;
    ArrayList<Category> Category_List;
    show_all_categories_Adapter Categoryadapter = null;

    public  static sqliteHelper sqlHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__all__categries_);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Category_List = new ArrayList<>();
        Categor_Ids_List = new ArrayList<>();


        layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView_categories = findViewById(R.id.recycler_View_cat);
        recyclerView_categories.setLayoutManager(layoutManager);

        Categoryadapter = new show_all_categories_Adapter(this, R.layout.category_one_item , Category_List);
        recyclerView_categories.setAdapter(Categoryadapter);
        Categoryadapter.notifyDataSetChanged();


        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);


        //get AllCategories from database-------------------------------------------

//        Cursor categoryCursor = Add_Category.sqlHelper.getData("SELECT * FROM CATEGORIES");

        try
        {
            Cursor categoryCursor = sqlHelper.getData("SELECT * FROM CATEGORIES");
            Category_List.clear();
            if (categoryCursor.moveToFirst()) {
                do{
                    int cat_id = categoryCursor.getInt(0);
                    String cat_name = categoryCursor.getString(1);
                    Toast.makeText(this,cat_name, Toast.LENGTH_SHORT).show();
                    byte[] cat_icon = categoryCursor.getBlob(2);
                    Category_List.add(new Category(cat_id, cat_name, cat_icon));
                    Categor_Ids_List.add(cat_id);
                }while (categoryCursor.moveToNext());
            }
            //----------------------------------------
        }
        catch (Exception e)
        {

        }


    }
}
