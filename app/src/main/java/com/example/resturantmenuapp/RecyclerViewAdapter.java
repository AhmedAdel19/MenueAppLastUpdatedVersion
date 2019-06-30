package com.example.resturantmenuapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    ArrayList<String> Item_Names_List = new ArrayList<>();
    ArrayList<byte [] > Item_Images_Uris = new ArrayList<>();
    ArrayList<Category> CategoryList = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> CategoryList_Names , ArrayList<byte []> CategoryList_Images)
    {
        // this.CategoryList = Category_List;
        this.Item_Names_List = CategoryList_Names;
        this.Item_Images_Uris = CategoryList_Images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item , parent , false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerViewAdapter.MyViewHolder holder, final int position)
    {
        Glide.with(mContext)
                .asBitmap()
                .load(Item_Images_Uris.get(position))
                .into(holder.Item_Icon);
      //  Category category = CategoryList.get(position);

//        byte [] CategoryImage = category.getCategory_Icon();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(CategoryImage , 0 , CategoryImage.length);
//        holder.Item_Icon.setImageBitmap(bitmap);

        holder.Item_Name.setText(Item_Names_List.get(position));
        
        holder.Item_Icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext, Item_Names_List.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return Item_Images_Uris.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView Item_Icon;
        TextView Item_Name;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Item_Icon = itemView.findViewById(R.id.item_icon);
            Item_Name = itemView.findViewById(R.id.item_name);
        }
    }
}
