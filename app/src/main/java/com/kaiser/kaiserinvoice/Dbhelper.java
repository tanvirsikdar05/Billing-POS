package com.kaiser.kaiserinvoice;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;


    private static final String PRODUCT_TABLE_NAME = "PRODUCT_TABLE";
    public static final String PRODUCT_ID = "ID";
    public static final String PRODUCT_NAME_KEY = "PRODUCT_NAME";
    public static final String PRODUCT_PRICE_KEY = "PRODUCT_PRICE";
    public static final String PRODUCT_UOM_KEY = "PRODUCT_UOM";
    public static final String PRODUCT_CATEGROY_KEY = "PRODUCT_CATEGROY";


    private static final String CREATE_PRODUCT_TABLE =
            "CREATE TABLE " + PRODUCT_TABLE_NAME + "(" +
                    PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    PRODUCT_NAME_KEY + " TEXT NOT NULL, " +
                    PRODUCT_PRICE_KEY + " TEXT NOT NULL, " +
                    PRODUCT_UOM_KEY + " TEXT NOT NULL, " +
                    PRODUCT_CATEGROY_KEY + " TEXT NOT NULL);";

    private static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME;
    private static final String SELECT_PRODUCT_TABLE = "SELECT * FROM " + PRODUCT_TABLE_NAME;


    //INVOICE TABLE
    private static final String INVOICE_TABLE_NAME = "INVOICE_TABLE";
    public static final String INVOICE_ID = "ID";
    public static final String INVOICE_DATE_KEY = "DATE";
    public static final String INVOICE_NAME_KEY = "PRODUCT_NAME";
    public static final String INVOICE_PRICE_KEY = "PRODUCT_PRICE";
    public static final String INVOICE_TOTAL_PRICE_KEY = "PRODUCT_PRICE_TOTAL";
    public static final String INVOICE_UOM_KEY = "INVOICE_UOM";
    public static final String INVOICE_DISCOUNT_KEY = "DISCOUNT";
    public static final String INVOICE_UNIQ_INV = "UNIQ_ID_INV";

    private static final String CREATE_INVOICE_TABLE =
            "CREATE TABLE " + INVOICE_TABLE_NAME + "(" +
                    INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    INVOICE_NAME_KEY + " TEXT NOT NULL, " +
                    INVOICE_DATE_KEY + " TEXT NOT NULL, " +
                    INVOICE_PRICE_KEY + " TEXT NOT NULL, " +
                    INVOICE_TOTAL_PRICE_KEY + " TEXT NOT NULL, " +
                    INVOICE_UOM_KEY + " TEXT NOT NULL, " +
                    INVOICE_DISCOUNT_KEY + " TEXT NOT NULL, " +
                    INVOICE_UNIQ_INV + " TEXT NOT NULL);";

    private static final String DROP_INVOICE_TABLE = "DROP TABLE IF EXISTS " + INVOICE_TABLE_NAME;
    private static final String SELECT_INVOICE_TABLE = "SELECT * FROM " + INVOICE_TABLE_NAME;


    //card table
    private static final String CARD_TABLE_NAME = "CARD_TABLE";
    public static final String CARD_ID = "ID";
    public static final String CARD_NAME_KEY = "CARD_NAME";
    public static final String CARD_PRICE_KEY = "CARD_PRICE";
    public static final String CARD_PRODUCT_PRICE_KEY = "CARD_PRODUCT_PRICE";
    public static final String CARD_UOM_KEY = "CARD_UOM";
    public static final String CARD_ID_p_KEY = "PRODUCT_ID_SAVE";
    public static final String CARD_DISCOUNT_KEY = "CARD_DISCOUNT";

    private static final String CREATE_CARD_TABLE =
            "CREATE TABLE " + CARD_TABLE_NAME + "(" +
                    CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CARD_NAME_KEY + " TEXT NOT NULL, " +
                    CARD_PRICE_KEY + " TEXT NOT NULL, " +
                    CARD_PRODUCT_PRICE_KEY + " TEXT NOT NULL, " +
                    CARD_UOM_KEY + " TEXT NOT NULL, " +
                    CARD_ID_p_KEY + " TEXT NOT NULL, " +
                    CARD_DISCOUNT_KEY + " TEXT NOT NULL);";

    private static final String DROP_CARD_TABLE = "DROP TABLE IF EXISTS " + CARD_TABLE_NAME;
    private static final String SELECT_CARD_TABLE = "SELECT * FROM " + CARD_TABLE_NAME;



    //create shop details

    private static final String SHOP_TABLE_NAME = "SHOP_TABLE";
    public static final String SHOP_ID = "ID";
    public static final String SHOP_NAME_KEY = "SHOP_NAME";
    public static final String SHOP_ADDRESS_KEY = "SHOP_ADDRESS";
    public static final String SHOP_VAT_KEY= "SHOP_VAT";

    private static final String CREATE_SHOP_TABLE =
            "CREATE TABLE " + SHOP_TABLE_NAME+ "(" +
                    SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    SHOP_NAME_KEY + " TEXT NOT NULL, " +
                    SHOP_ADDRESS_KEY + " TEXT NOT NULL, " +
                    SHOP_VAT_KEY + " TEXT NOT NULL);";

    private static final String DROP_SHOP_TABLE = "DROP TABLE IF EXISTS " + SHOP_TABLE_NAME;
    private static final String SELECT_SHOP_TABLE = "SELECT * FROM " + SHOP_TABLE_NAME;

    //CREATE CATEGROY TABLE
    private static final String CATEGROY_TABLE_NAME = "CATEGROY_TABLE";
    public static final String CATEGROY_ID = "ID";
    public static final String CATEGROY_NAME_KEY = "CATEGROY_NAME";
    private static final String CREATE_CATEGROY_TABLE =
            "CREATE TABLE " + CATEGROY_TABLE_NAME+ "(" +
                    CATEGROY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CATEGROY_NAME_KEY + " TEXT NOT NULL);";

    private static final String DROP_CATEGROY_TABLE = "DROP TABLE IF EXISTS " + CATEGROY_TABLE_NAME;
    private static final String SELECT_CATEGROY_TABLE = "SELECT * FROM " + CATEGROY_TABLE_NAME;

    //PER INVOICE
    private static final String PERINVOICE_TABLE_NAME = "PERINVOICE_TABLE";
    public static final String PERINVOICE_ID = "ID";
    public static final String PERINVOICE_DATE_KEY = "PERINVOCIE_DATE";
    public static final String PERINVOICE_TOTAL_KEY = "PERINVOCIE_TOTAL";
    private static final String CREATE_PERINVOICE_TABLE =
            "CREATE TABLE " + PERINVOICE_TABLE_NAME+ "(" +
                    PERINVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    PERINVOICE_DATE_KEY + " TEXT NOT NULL, " +
                    PERINVOICE_TOTAL_KEY + " TEXT NOT NULL);";


    private static final String DROP_PERINVOICE_TABLE = "DROP TABLE IF EXISTS " + PERINVOICE_TABLE_NAME;
    private static final String SELECT_PERINVOICE_TABLE = "SELECT * FROM " + PERINVOICE_TABLE_NAME;


    //create uom table

    private static final String UOM_TABLE_NAME = "UOM_TABLE";
    public static final String UOM_ID = "ID";
    public static final String UOM_NAME_KEY = "UOM_NAME";
    private static final String CREATE_UOM_TABLE =
            "CREATE TABLE " + UOM_TABLE_NAME+ "(" +
                    UOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    UOM_NAME_KEY + " TEXT NOT NULL);";

    private static final String DROP_UOM_TABLE = "DROP TABLE IF EXISTS " + UOM_TABLE_NAME;
    private static final String SELECT_UOM_TABLE = "SELECT * FROM " + UOM_TABLE_NAME;


    public Dbhelper(@Nullable Context context) {
        super(context, "shop_details.db", null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
            sqLiteDatabase.execSQL(CREATE_SHOP_TABLE);
            sqLiteDatabase.execSQL(CREATE_CATEGROY_TABLE);
            sqLiteDatabase.execSQL(CREATE_UOM_TABLE);
            sqLiteDatabase.execSQL(CREATE_CARD_TABLE);
            sqLiteDatabase.execSQL(CREATE_PERINVOICE_TABLE);
            sqLiteDatabase.execSQL(CREATE_INVOICE_TABLE);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }



    ///////////////database upgrade

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(DROP_PRODUCT_TABLE);
        sqLiteDatabase.execSQL(DROP_SHOP_TABLE);
        sqLiteDatabase.execSQL(DROP_CATEGROY_TABLE);
        sqLiteDatabase.execSQL(DROP_UOM_TABLE);
        sqLiteDatabase.execSQL(DROP_CARD_TABLE);
        sqLiteDatabase.execSQL(DROP_PERINVOICE_TABLE);
        sqLiteDatabase.execSQL(DROP_INVOICE_TABLE);


    }
   // ADD product here
    long add_product_data(String name,String uom,String price,String categroy){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME_KEY,name);
        values.put(PRODUCT_UOM_KEY,uom);
        values.put(PRODUCT_PRICE_KEY,price);
        values.put(PRODUCT_CATEGROY_KEY,categroy);

        return database.insert(PRODUCT_TABLE_NAME,null,values);
    }
    // add card data here
    long add_card_data(String name,String uom,String price,String discount,String total_tk,String productid){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_NAME_KEY,name);
        values.put(CARD_UOM_KEY,uom);
        values.put(CARD_PRICE_KEY,price);
        values.put(CARD_PRODUCT_PRICE_KEY,total_tk);
        values.put(CARD_DISCOUNT_KEY,discount);
        values.put(CARD_ID_p_KEY,productid);
        return database.insert(CARD_TABLE_NAME,null,values);
    }
    //get all card data
    Cursor get_all_card_data()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_CARD_TABLE,null);
    }
    //update product here
    int update_product(String id,String name,String uom,String price,String categroy){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME_KEY,name);
        values.put(PRODUCT_UOM_KEY,uom);
        values.put(PRODUCT_PRICE_KEY,price);
        values.put(PRODUCT_CATEGROY_KEY,categroy);

        return database.update(PRODUCT_TABLE_NAME,values,PRODUCT_ID + "=?",new String[]{id});
    }
    //delete product here

    long delete_product(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(PRODUCT_TABLE_NAME,PRODUCT_ID+"=?",new String[]{id});
    }

    //get all product
    Cursor get_all_product()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_PRODUCT_TABLE,null);
    }



    //  ADD CATEGROY NAME
    long add_categroy(String ct_name){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGROY_NAME_KEY,ct_name);
        return database.insert(CATEGROY_TABLE_NAME,null,values);
    }

    //update categroy
    int update_categroy(String id,String categroy){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGROY_NAME_KEY,categroy);
        return database.update(CATEGROY_TABLE_NAME,values,CATEGROY_ID + "=?",new String[]{id});
    }
    //delete categroy
    long delete_categroy(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(CATEGROY_TABLE_NAME,CATEGROY_ID+"=?",new String[]{id});
    }
    //get all categroy
    Cursor get_all_categroy()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_CATEGROY_TABLE,null);
    }

    // add uom
    long add_uom(String uom){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UOM_NAME_KEY,uom);
        return database.insert(UOM_TABLE_NAME,null,values);
    }

    //update uom
    int update_uom(String id,String uom){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UOM_NAME_KEY,uom);
        return database.update(UOM_TABLE_NAME,values,UOM_ID + "=?",new String[]{id});
    }
    long delete_uom(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(UOM_TABLE_NAME,UOM_ID+"=?",new String[]{id});
    }
    //get all uom
    Cursor get_all_uom()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_UOM_TABLE,null);

    }

    long card_numbers(){
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, CARD_TABLE_NAME);
    }
    //update card
    int update_card(String id,String name,String uom,String tk,String totaltk,String discount){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_NAME_KEY,name);
        values.put(CARD_DISCOUNT_KEY,discount);
        values.put(CARD_PRICE_KEY,tk);
        values.put(CARD_UOM_KEY,uom);
        values.put(CARD_PRODUCT_PRICE_KEY,totaltk);
        return database.update(CARD_TABLE_NAME,values,CARD_ID + "=?",new String[]{id});
    }
    long delete_one_data_card(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(CARD_TABLE_NAME,CARD_ID+"=?",new String[]{id});
    }
    void clear_card(){
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(CARD_TABLE_NAME,null,null);
    }
    long add_data_per_invoice(String date,String total){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERINVOICE_DATE_KEY,date);
        values.put(PERINVOICE_TOTAL_KEY,total);
        return database.insert(PERINVOICE_TABLE_NAME,null,values);
    }
    long add_invoice_data(String date,String name,String total_uom,String p_price,String discount,String total_tk,
                          String per_invoice_id){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INVOICE_DATE_KEY,date);
        values.put(INVOICE_NAME_KEY,name);
        values.put(INVOICE_UOM_KEY,total_uom);
        values.put(INVOICE_PRICE_KEY,p_price);
        values.put(INVOICE_DISCOUNT_KEY,discount);
        values.put(INVOICE_TOTAL_PRICE_KEY,total_tk);
        values.put(INVOICE_UNIQ_INV,per_invoice_id);
        return database.insert(INVOICE_TABLE_NAME,null,values);
    }
    Cursor get_all_invoice()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_INVOICE_TABLE,null);

    }
    Cursor get_all_perinvoice()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_PERINVOICE_TABLE,null);

    }
    long add_shop_details(String name,String address,String vat){
        SQLiteDatabase database=this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOP_NAME_KEY,name);
        values.put(SHOP_ADDRESS_KEY,address);
        values.put(SHOP_VAT_KEY,vat);
        return database.insert(SHOP_TABLE_NAME,null,values);
    }

    int update_shop_details(String id,String name,String address,String vat){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOP_NAME_KEY,name);
        values.put(SHOP_ADDRESS_KEY,address);
        values.put(SHOP_VAT_KEY,vat);
        return database.update(SHOP_TABLE_NAME,values,SHOP_ID + "=?",new String[]{id});
    }

    Cursor get_shop_data()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_SHOP_TABLE,null);
    }

    long delete_invoice(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(INVOICE_TABLE_NAME,INVOICE_ID+"=?",new String[]{id});
    }

    long delete_per_invoice(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(PERINVOICE_TABLE_NAME,PERINVOICE_ID+"=?",new String[]{id});
    }




}
