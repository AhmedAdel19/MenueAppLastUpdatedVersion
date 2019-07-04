package com.example.resturantmenuapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Add_Item_Category extends AppCompatActivity
{
    private ArrayList<String> categories = new ArrayList<>();
    private Spinner sp_category_popup;
    private EditText Category_Item_Name , Category_Item_Price;
    private Button Category_Item_Icon;
    private Button Save_Item_Category;
    private ImageView Item_Icon_View;
    final int REQUEST_CODE_GALLERY = 999;
    ArrayList<Category> Category_List;
    ArrayList<String> Categor_Names_List ;
    ArrayList<Integer> Categor_Ids_List ;

    ArrayList<byte []> Category_Images_List;
    private int selected_Category_id;
    private ProgressDialog loadingBar;


    public  static sqliteHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item__category);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Category_List = new ArrayList<>();
        Categor_Names_List = new ArrayList<>();
        Category_Images_List = new ArrayList<>();
        Categor_Ids_List = new ArrayList<>();


        init();

        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);
        sqlHelper.queryData("CREATE TABLE IF NOT EXISTS CATEGORY_ITEMS (ItemId INTEGER PRIMARY KEY AUTOINCREMENT,  ItemName VARCHAR , ItemPrice VARCHAR , ItemImage BLOG , ItemCategory INTEGER ,FOREIGN KEY (ItemCategory) REFERENCES CATEGORIES(CategoryId))");



//        try
//        {
//
//             sqlHelper.queryData("Alter table CATEGORY_ITEMS Add ADD CONSTRAINT FK_ICID FOREIGN KEY (ItemCategory) REFERENCES CATEGORIES(CategoryId) on delete cascade on update cascade ");
//
//        }
//        catch (Exception e)
//        {
//         e.printStackTrace();
//        }

        Category_Item_Icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ActivityCompat.requestPermissions
                        (
                                Add_Item_Category.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALLERY
                        );
            }
        });




        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);

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




        sp_category_popup = findViewById(R.id.sp_category);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(Add_Item_Category.this, android.R.layout.simple_spinner_item, Categor_Names_List);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category_popup.setAdapter(categoriesAdapter);

        sp_category_popup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selected_Category_id = Categor_Ids_List.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Save_Item_Category.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Item_Name_text , Item_Price_text , Item_icon_text;
                Item_Name_text = Category_Item_Name.getText().toString();
                Item_Price_text = Category_Item_Price.getText().toString();
                Item_icon_text = Item_Icon_View.toString();

                if(TextUtils.isEmpty(Item_Name_text))
                {
                    Toast.makeText(Add_Item_Category.this, "please enter item name before saving it !", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Item_Price_text))
                {
                    Toast.makeText(Add_Item_Category.this, "please enter item price before saving it !", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Item_icon_text))
                {
                    Toast.makeText(Add_Item_Category.this, "please select item Icon before saving it !", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    loadingBar.setTitle("complete Add new Item setup");
                    loadingBar.setMessage("please wait until Adding new Item setup complete...");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);

                    try
                    {
                        sqlHelper.inserNewItemCategorytData
                                (
                                        Category_Item_Name.getText().toString().trim(),
                                        Category_Item_Price.getText().toString().trim(),
                                        imageViewToByte(Item_Icon_View),
                                        selected_Category_id
                                );
                        loadingBar.dismiss();
                        Toast.makeText(Add_Item_Category.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                        Category_Item_Name.setText("");
                        Category_Item_Price.setText("");
                        Item_Icon_View.setImageResource(R.mipmap.ic_launcher);
                    }
                    catch (Exception e)
                    {
                        loadingBar.dismiss();
                        e.printStackTrace();
                    }
                }

            }
        });



    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 , stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == REQUEST_CODE_GALLERY)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent , REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(this, "sorry, you don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Item_Icon_View.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init()
    {
        loadingBar = new ProgressDialog(this);
        Category_Item_Name = findViewById(R.id.AddCategory_Item_NameText);
        Category_Item_Price = findViewById(R.id.AddCategory_Item_PriceText);
        Category_Item_Icon = findViewById(R.id.ItemSelectIcon);
        Save_Item_Category = findViewById(R.id.SaveItemBtn);
        Item_Icon_View = findViewById(R.id.ItemIconView);


    }
}
