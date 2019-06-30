package com.example.resturantmenuapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class Items_List_Adapter extends BaseAdapter
{
    private Context context;
    private int layout;
    private ArrayList<Item> ItemList;

    public Items_List_Adapter(Context context, int layout, ArrayList<Item> itemList)
    {
        this.context = context;
        this.layout = layout;
        ItemList = itemList;
    }

    @Override
    public int getCount()
    {
        return ItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return ItemList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private class ViewHolder
    {
        ImageView ItemImage;
        TextView ItemName , ItemPrice;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout , null);

            holder.ItemName = row.findViewById(R.id.one_item_name_view);
            holder.ItemPrice = row.findViewById(R.id.one_item_price_view);
            holder.ItemImage = row.findViewById(R.id.one_item_view);
            row.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        Item item = ItemList.get(position);
        holder.ItemName.setText(item.getItem_Name());
        holder.ItemPrice.setText(item.getItem_Price());

        byte [] ItemImage = item.getItem_Icon();
        Bitmap bitmap = BitmapFactory.decodeByteArray(ItemImage , 0 , ItemImage.length);

        holder.ItemImage.setImageBitmap(bitmap);


        return row;
    }
}
