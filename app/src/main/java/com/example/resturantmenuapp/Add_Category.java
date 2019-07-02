package com.example.resturantmenuapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add_Category extends AppCompatActivity
{
    private EditText Category_Name;
    private Button Category_Icon;
    private Button Save_Category;
    private ImageView Icon_View;
    final int REQUEST_CODE_GALLERY = 999;
    private ProgressDialog loadingBar;


    public  static sqliteHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        init();

        sqlHelper = new sqliteHelper(this , "MenueDB.sqlite" , null ,1);
        sqlHelper.queryData("CREATE TABLE IF NOT EXISTS CATEGORIES (CategoryId INTEGER PRIMARY KEY AUTOINCREMENT , CategoryName VARCHAR , CategoryImage BLOG)");

        Category_Icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ActivityCompat.requestPermissions
                        (
                                Add_Category.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALLERY
                        );
            }
        });

        Save_Category.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Category_Name_text , Category_icon_text;
                Category_Name_text = Category_Name.getText().toString();
                Category_icon_text = Icon_View.toString();

                if(TextUtils.isEmpty(Category_Name_text))
                {
                    Toast.makeText(Add_Category.this, "please enter Category name before saving it !", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Category_icon_text))
                {
                    Toast.makeText(Add_Category.this, "please select Category Icon before saving it !", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    loadingBar.setTitle("complete Category setup");
                    loadingBar.setMessage("please wait until Creating Category setup complete...");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);

                    try
                    {
                        sqlHelper.inserNewCategorytData
                                (
                                        Category_Name.getText().toString().trim(),
                                        imageViewToByte(Icon_View)
                                );
                        loadingBar.dismiss();

                        Toast.makeText(Add_Category.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        Category_Name.setText("");
                        Icon_View.setImageResource(R.mipmap.ic_launcher);
                    }
                    catch (Exception e)
                    {
                        loadingBar.dismiss();
                        e.printStackTrace();
                    }

                }



            }
        });

//        Items_Intent = findViewById(R.id.ItemsActivity);
//        Items_Intent.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent GoToItemsActivity = new Intent(Add_Category.this , Add_Item_Category.class );
//                startActivity(GoToItemsActivity);
//            }
//        });
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
                Icon_View.setImageBitmap(bitmap);
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
        Category_Name = findViewById(R.id.AddCategoryNameText);
        Category_Icon = findViewById(R.id.CategorySelectIcon);
        Save_Category = findViewById(R.id.SaveCategoryBtn);
        Icon_View = findViewById(R.id.CategoryIconView);

    }
}
