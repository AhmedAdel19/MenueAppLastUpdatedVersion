package com.example.resturantmenuapp;

public class Category
{
    private int Category_id;
    private String Category_name;
    private byte [] Category_Icon;


    public Category(int category_id, String category_name, byte[] category_Icon)
    {
        Category_id = category_id;
        Category_name = category_name;
        Category_Icon = category_Icon;
    }

    public int getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(int category_id) {
        Category_id = category_id;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public byte[] getCategory_Icon() {
        return Category_Icon;
    }

    public void setCategory_Icon(byte[] category_Icon) {
        Category_Icon = category_Icon;
    }
}

