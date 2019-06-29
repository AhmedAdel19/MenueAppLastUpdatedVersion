package com.example.resturantmenuapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference CategoryRef;
    ArrayList<String> Item_Names_List = new ArrayList<>();
    ArrayList<String> Item_Images_List = new ArrayList<>();
    ArrayList<String> AllCategories = new ArrayList<>();

   // private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  test = findViewById(R.id.test_btn);
        CategoryRef = FirebaseDatabase.getInstance().getReference().child("All_Categories");

        CategoryRef.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                AllCategories.add(dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        CategoryRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(int i = 0 ; i < AllCategories.size() ; i++)
                {
//                    if(dataSnapshot.exists())
//                    {
                        Item_Names_List.add(dataSnapshot.child(AllCategories.get(i)).child("Category_Name").getValue().toString());
                        Item_Images_List.add(dataSnapshot.child(AllCategories.get(i)).child("Category_Icon").getValue().toString());
                   // }


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

//        test.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Log.e("nameeeeeeeeees",Item_Names_List.get(0));
//                Log.e("imagggggggges",Item_Images_List.get(0));
//
//
//            }
//        });

        Initi_Recycler_View();




    }

    private void Initi_Recycler_View()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false);
        RecyclerView recyclerView = findViewById(R.id.Recycler_list_items);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this , Item_Names_List , Item_Images_List);
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        adapter.startListening();
//
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//
//        adapter.stopListening();
//    }
}
