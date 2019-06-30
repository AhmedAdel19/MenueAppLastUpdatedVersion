package com.example.resturantmenuapp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class Items_List_Activity extends AppCompatActivity
{
     GridView ItemGridView;
     ArrayList<Item> Item_List;
     ArrayList<Category> Category_List;
    ArrayList<String> Categor_Names_List ;
    ArrayList<byte []> Category_Images_List;
     Items_List_Adapter ItemAdapter = null;
    public  static sqliteHelper sqlHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items__list_);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ItemGridView = findViewById(R.id.Items_GridView);
        Item_List = new ArrayList<>();
        Category_List = new ArrayList<>();
        Categor_Names_List = new ArrayList<>();
        Category_Images_List = new ArrayList<>();

        ItemAdapter = new Items_List_Adapter(this , R.layout.one_item , Item_List);
        ItemGridView.setAdapter(ItemAdapter);

        //get Category Items from database-----------------------------------------

        //-------------------------------------------------------------------------

        //get AllCategories from database-------------------------------------------

//        Cursor categoryCursor = Add_Category.sqlHelper.getData("SELECT * FROM CATEGORIES");
        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);

        Cursor categoryCursor = sqlHelper.getData("SELECT * FROM CATEGORIES");
        Category_List.clear();
        if (categoryCursor.moveToFirst()) {
            do{
                int cat_id = categoryCursor.getInt(0);
                String cat_name = categoryCursor.getString(1);
                Toast.makeText(this,cat_name, Toast.LENGTH_SHORT).show();
                byte[] cat_icon = categoryCursor.getBlob(2);
                Category_List.add(new Category(cat_id, cat_name, cat_icon));
                Categor_Names_List.add(cat_name);
                Category_Images_List.add(cat_icon);
            }while (categoryCursor.moveToNext());
        }
        //----------------------------------------

        ItemAdapter.notifyDataSetChanged();

        Initi_Recycler_View();

    }


    private void Initi_Recycler_View()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false);
        RecyclerView recyclerView = findViewById(R.id.Recycler_list_Categories);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter Categoryadapter = new RecyclerViewAdapter(this , Categor_Names_List , Category_Images_List);
        recyclerView.setAdapter(Categoryadapter);
        Categoryadapter.notifyDataSetChanged();
    }
}
