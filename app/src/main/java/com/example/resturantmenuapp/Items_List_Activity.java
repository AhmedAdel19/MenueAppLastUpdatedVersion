package com.example.resturantmenuapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class Items_List_Activity extends AppCompatActivity
{
     GridView ItemGridView;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
     ArrayList<Item> Item_List;
    ArrayList<Item> Item_List2;

    ArrayList<Integer> Categor_Ids_List;
    ArrayList<Category> Category_List;
    ArrayList<String> Categor_Names_List ;
    ArrayList<byte []> Category_Images_List;
     Items_List_Adapter ItemAdapter = null;
    RecyclerViewAdapter Categoryadapter = null;
    public  static sqliteHelper sqlHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items__list_);

            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        int position = intent.getIntExtra("Item_Position" , 0);
        ItemGridView = findViewById(R.id.Items_GridView);
        Item_List = new ArrayList<>();
        Category_List = new ArrayList<>();
        Categor_Names_List = new ArrayList<>();
        Category_Images_List = new ArrayList<>();
        Categor_Ids_List = new ArrayList<>();




        ItemAdapter = new Items_List_Adapter(this , R.layout.one_item , Item_List);

        ItemGridView.setAdapter(ItemAdapter);

        layoutManager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false);
        recyclerView = findViewById(R.id.Recycler_list_Categories);
        recyclerView.setLayoutManager(layoutManager);

        Categoryadapter = new RecyclerViewAdapter(this, R.layout.layout_list_item , Categor_Names_List , Category_Images_List);
        recyclerView.setAdapter(Categoryadapter);
        Categoryadapter.notifyDataSetChanged();


        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);


        //get AllCategories from database-------------------------------------------

//
//        Cursor categoryCursor = Add_Category.sqlHelper.getData("SELECT * FROM CATEGORIES");

        try
        {
            Cursor categoryCursor = sqlHelper.getData("SELECT * FROM CATEGORIES");
            Category_List.clear();
            if (categoryCursor.moveToFirst()) {
                do{
                    int cat_id = categoryCursor.getInt(0);
                    String cat_name = categoryCursor.getString(1);
                    //  Toast.makeText(this,cat_name, Toast.LENGTH_SHORT).show();
                    byte[] cat_icon = categoryCursor.getBlob(2);
                    Category_List.add(new Category(cat_id, cat_name, cat_icon));
                    Categor_Names_List.add(cat_name);
                    Category_Images_List.add(cat_icon);
                    Categor_Ids_List.add(cat_id);
                }while (categoryCursor.moveToNext());
            }
            //----------------------------------------
        }
        catch (Exception e)
        {

        }


        //get Category Items from database-----------------------------------------

        try
        {
            Cursor categoryItemsCursor = sqlHelper.getData("SELECT * FROM CATEGORY_ITEMS WHERE ItemCategory = "+Categor_Ids_List.get(position)+"");

            Item_List.clear();

            if (categoryItemsCursor.moveToFirst()) {
                do{
                    int Item_id = categoryItemsCursor.getInt(0);
                    String Item_name = categoryItemsCursor.getString(1);
                    String Item_price = categoryItemsCursor.getString(2);
                    byte[] Item_icon = categoryItemsCursor.getBlob(3);
                    int cat_Item_id = categoryItemsCursor.getInt(4);


                    Item_List.add(new Item(Item_id, cat_Item_id, Item_name , Item_price , Item_icon));

                }while (categoryItemsCursor.moveToNext());
            }
        }
        catch (Exception e)
        {

        }

        ItemAdapter.notifyDataSetChanged();


        //-------------------------------------------------------------------------




      //  Initi_Recycler_View();


        ItemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
              Intent fullView = new Intent(Items_List_Activity.this , Activity_Grid_Item.class);
              fullView.putExtra("Item_Name" , Item_List.get(position).getItem_Name());
              fullView.putExtra("Item_Price" , Item_List.get(position).getItem_Price());
              fullView.putExtra("Item_View" , Item_List.get(position).getItem_Icon());
              startActivity(fullView);
            }
        });


    }


}
