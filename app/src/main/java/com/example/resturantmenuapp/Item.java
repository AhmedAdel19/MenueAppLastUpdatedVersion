package com.example.resturantmenuapp;

public class Item
{
    private int Item_Id;
    private int Category_Item_Id;
    private String Item_Name;
    private String Item_Price;
    private byte [] Item_Icon;


    public Item(int item_Id, int category_Item_Id, String item_Name, String item_Price, byte[] item_Icon)
    {
        Item_Id = item_Id;
        Category_Item_Id = category_Item_Id;
        Item_Name = item_Name;
        Item_Price = item_Price;
        Item_Icon = item_Icon;
    }

    public int getItem_Id() {
        return Item_Id;
    }

    public void setItem_Id(int item_Id) {
        Item_Id = item_Id;
    }

    public int getCategory_Item_Id() {
        return Category_Item_Id;
    }

    public void setCategory_Item_Id(int category_Item_Id) {
        Category_Item_Id = category_Item_Id;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_Price() {
        return Item_Price;
    }

    public void setItem_Price(String item_Price) {
        Item_Price = item_Price;
    }

    public byte[] getItem_Icon() {
        return Item_Icon;
    }

    public void setItem_Icon(byte[] item_Icon) {
        Item_Icon = item_Icon;
    }
}
