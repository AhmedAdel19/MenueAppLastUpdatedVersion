package com.example.resturantmenuapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class sqliteHelper extends SQLiteOpenHelper
{

    public sqliteHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }


    public void queryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void inserNewCategorytData(String name  , byte [] image)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CATEGORIES VALUES (NULL , ? , ? )";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1 , name);
        statement.bindBlob(2 , image);

        statement.executeInsert();

    }


    public void inserNewItemCategorytData(String name , String price  , byte [] image , int Item_Fk)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CATEGORY_ITEMS VALUES (NULL , ? , ? , ? ,  "+Item_Fk+" ) ";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1 , name);
        statement.bindString(2, price);
        statement.bindBlob(3 , image);
        statement.executeInsert();

    }

    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql , null);

    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
