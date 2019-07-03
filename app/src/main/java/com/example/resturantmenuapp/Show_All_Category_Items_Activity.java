package com.example.resturantmenuapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class Show_All_Category_Items_Activity extends AppCompatActivity
{

    LinearLayoutManager layoutManager;
    RecyclerView recyclerView_Items;
    ArrayList<Integer> Categor_Ids_List;
    ArrayList<Category> Category_List;
    ArrayList<Item> Items_Category_List;
    // position;

    Show_All_Items_Category_Adapter ItemsCategoryadapter = null;

    public  static sqliteHelper sqlHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__all__category__items_);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        int position = intent.getIntExtra("cat_position" , 0);
        Constants.Category_Position = position;


        Category_List = new ArrayList<>();
        Categor_Ids_List = new ArrayList<>();
        Items_Category_List = new ArrayList<>();


        layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView_Items = findViewById(R.id.recycler_View_item);
        recyclerView_Items.setLayoutManager(layoutManager);

        ItemsCategoryadapter = new Show_All_Items_Category_Adapter(this, R.layout.category_one_item , Items_Category_List);
        recyclerView_Items.setAdapter(ItemsCategoryadapter);
        ItemsCategoryadapter.notifyDataSetChanged();


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
                    //Toast.makeText(this,cat_name, Toast.LENGTH_SHORT).show();
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

        try
        {
            Cursor ItemCursor = sqlHelper.getData("SELECT * FROM CATEGORY_ITEMS WHERE ItemCategory = "+Categor_Ids_List.get(position)+"");
            Category_List.clear();
            if (ItemCursor.moveToFirst()) {
                do{
                    int Item_id = ItemCursor.getInt(0);
                    String Item_name = ItemCursor.getString(1);
                    String Item_price = ItemCursor.getString(2);
                    byte[] Item_icon = ItemCursor.getBlob(3);
                    int cat_Item_id = ItemCursor.getInt(4);
                    Items_Category_List.add(new Item(Item_id, cat_Item_id, Item_name , Item_price , Item_icon));
                }while (ItemCursor.moveToNext());
            }
            //----------------------------------------
        }
        catch (Exception e)
        {

        }


    }



}
