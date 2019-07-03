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

public class show_all_categories_Adapter extends RecyclerView.Adapter<show_all_categories_Adapter.MyViewHolder>
{
    ArrayList<Category> CategoryList = new ArrayList<>();
    Context mContext;
    private int layout;

    public show_all_categories_Adapter(Context mContext , int layout ,ArrayList<Category> categoryList) {
        CategoryList = categoryList;
        this.mContext = mContext;
        this.layout = layout;
    }

    @NonNull
    @Override
    public show_all_categories_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout , parent , false);

        show_all_categories_Adapter.MyViewHolder holder = new show_all_categories_Adapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull show_all_categories_Adapter.MyViewHolder holder, final int position)
    {
        final Category category = CategoryList.get(position);

        Glide.with(mContext)
                .asBitmap()
                .load(category.getCategory_Icon())
                .into(holder.Category_Icon);

        holder.Category_Name.setText(category.getCategory_name());

        holder.Category_Name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent category_obtions_intent = new Intent(mContext , Category_Options_Activity.class);
                category_obtions_intent.putExtra("category_position" , position);
                mContext.startActivity(category_obtions_intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView Category_Icon;
        TextView Category_Name;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Category_Icon = itemView.findViewById(R.id.category_icon_single);
            Category_Name = itemView.findViewById(R.id.category_name_single);
        }
    }
}
