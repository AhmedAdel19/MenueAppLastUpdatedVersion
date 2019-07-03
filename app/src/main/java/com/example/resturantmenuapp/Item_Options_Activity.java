package com.example.resturantmenuapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Item_Options_Activity extends AppCompatActivity
{
    private TextView ItemNameView , ItemPriceView ;
    private ImageView ItemIconView ;
    private Button deleteItemBtn;
    private ArrayList<Category> ItemList;
    private ArrayList<Integer> Ids_item_list;
    private ArrayList<Integer> Categor_Ids_List;
    int Itemposition , Item_id , cat_Item_id ,CategoryP ;
    byte [] Item_icon;
    String Item_name ,Item_price;
    public  static sqliteHelper sqlHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__options_);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ItemList = new ArrayList<>();
        Ids_item_list = new ArrayList<>();
        Categor_Ids_List = new ArrayList<>();

        ItemNameView = findViewById(R.id.one_item_name_view_full2);
        ItemPriceView = findViewById(R.id.one_item_price_view_full2);
        ItemIconView = findViewById(R.id.one_item_view_full2);
        deleteItemBtn = findViewById(R.id.delete_item_btn);
        CategoryP = Constants.Category_Position;

        Intent intent = getIntent();
        Itemposition = intent.getIntExtra("ItemPosition" , 0);

        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);

        try
        {
            Cursor categoryCursor = sqlHelper.getData("SELECT * FROM CATEGORIES");
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

        //get Category Items from database-----------------------------------------

        try
        {
            Cursor categoryItemsCursorAll = sqlHelper.getData("SELECT * FROM CATEGORY_ITEMS WHERE ItemCategory = "+Categor_Ids_List.get(CategoryP)+"");

            ItemList.clear();

            if (categoryItemsCursorAll.moveToFirst())
            {
                do{
                    int Item_id = categoryItemsCursorAll.getInt(0);
                    Ids_item_list.add(Item_id);

                }while (categoryItemsCursorAll.moveToNext());
            }
        }
        catch (Exception e)
        {

        }

        try
        {
            Cursor categoryItemsCursor = sqlHelper.getData("SELECT * FROM CATEGORY_ITEMS WHERE ItemId = "+Ids_item_list.get(Itemposition)+"");

            ItemList.clear();

            if (categoryItemsCursor.moveToFirst()) {
                do{
                     Item_id = categoryItemsCursor.getInt(0);
                     Item_name = categoryItemsCursor.getString(1);
                     Item_price = categoryItemsCursor.getString(2);
                     Item_icon = categoryItemsCursor.getBlob(3);
                     cat_Item_id = categoryItemsCursor.getInt(4);

                }while (categoryItemsCursor.moveToNext());
            }
        }
        catch (Exception e)
        {

        }

        ItemNameView.setText(Item_name);
        ItemPriceView.setText(Item_price);

        Glide.with(Item_Options_Activity.this)
                .asBitmap()
                .load(Item_icon)
                .into(ItemIconView);


        deleteItemBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try
                {
                    sqlHelper.deleteItemRecord(""+Ids_item_list.get(Itemposition)+"");
                    Intent  go_back_items = new Intent(Item_Options_Activity.this , Show_All_Category_Items_Activity.class);
                    startActivity(go_back_items);

                }
                catch (Exception e)
                {

                }

            }
        });




    }
}
