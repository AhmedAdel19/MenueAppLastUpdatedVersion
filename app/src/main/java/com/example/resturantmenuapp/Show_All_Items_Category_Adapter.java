package com.example.resturantmenuapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_All_Items_Category_Adapter extends RecyclerView.Adapter<Show_All_Items_Category_Adapter.MyViewHolder>
{
    ArrayList<Item> ItemsCategoryList = new ArrayList<>();
    Context mContext;
    private int layout;


    public Show_All_Items_Category_Adapter(Context mContext ,  int layout , ArrayList<Item> itemsCategoryList)
    {
        ItemsCategoryList = itemsCategoryList;
        this.mContext = mContext;
        this.layout = layout;
    }

    @NonNull
    @Override
    public Show_All_Items_Category_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout , parent , false);

        Show_All_Items_Category_Adapter.MyViewHolder holder = new Show_All_Items_Category_Adapter.MyViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        final Item item = ItemsCategoryList.get(position);

        Glide.with(mContext)
                .asBitmap()
                .load(item.getItem_Icon())
                .into(holder.Item_Icon);

        holder.Item_Name.setText(item.getItem_Name());

        holder.Item_Name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Show_All_Category_Items_Activity g= new Show_All_Category_Items_Activity();

                Intent item_obtions_intent = new Intent(mContext , Item_Options_Activity.class);
                item_obtions_intent.putExtra("ItemPosition" , position);
                mContext.startActivity(item_obtions_intent);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return ItemsCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView Item_Icon;
        TextView Item_Name;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Item_Icon = itemView.findViewById(R.id.category_icon_single);
            Item_Name = itemView.findViewById(R.id.category_name_single);
        }
    }
}
