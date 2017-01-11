package com.example.milek.android3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milek on 2016-12-11.
 */

public class ProductAdapter {
    private static final String DEBUG_TAG = "SqLite";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String TABLE_NAME = "produkty";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nazwa";
    private static final String KEY_DESC = "opis";
    public static final String KEY_IMAGE = "obraz";
    public static final int NAME_COLUMN = 1;
    public static final int IMAGE_COLUMN = 2;
    public static final int DESCRIPTION_COLUMN = 3;

    private SQLiteDatabase db;
    private Context context;
    private Database ProdB;




    public class Database extends SQLiteOpenHelper {


        public Database(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                    + KEY_IMAGE + " TEXT," + KEY_DESC + " TEXT" + ");";
            db.execSQL(CREATE_PRODUCTS_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + TABLE_NAME + " ver." + DB_VERSION + " created");



        }

        @Override

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String DROP_PRODUCTS_TABLE =
                    "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(DROP_PRODUCTS_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + TABLE_NAME + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }







    public ProductAdapter(Context context) {
        this.context = context;
    }

    public ProductAdapter open(){
        ProdB = new Database(context, DB_NAME, null, DB_VERSION);
        try {
            db = ProdB.getWritableDatabase();
        } catch (SQLException e) {
            db = ProdB.getReadableDatabase();
        }
        return this;
    }



    public void close() {
        ProdB.close();
    }

    public long insertProduct(int name,int image, int description) {

        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(KEY_NAME, name);
        newTodoValues.put(KEY_IMAGE, image);
        newTodoValues.put(KEY_DESC, description);
        return db.insert(TABLE_NAME, null, newTodoValues);
    }

    public long insertProduct(ProductModel task) {

        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(KEY_NAME, task.getTitle());
        newTodoValues.put(KEY_IMAGE, task.getImg_url());
        newTodoValues.put(KEY_DESC, task.getDesc());
        return db.insert(TABLE_NAME, null, newTodoValues);
    }





    public boolean updateProduct(ProductModel task) {
        int  id = task.getId();
        int description = task.getDesc();
        int name = task.getTitle();
        int image = task.getImg_url();
        return updateProduct(id, name,image, description);
    }

    public boolean updateProduct(int id,int name,int image, int description) {
        String where = KEY_ID + "=" + id;

        ContentValues updateProductValues = new ContentValues();
        updateProductValues.put(KEY_NAME, name);
        updateProductValues.put(KEY_IMAGE, image);
        updateProductValues.put(KEY_DESC, description);
        return db.update(TABLE_NAME, updateProductValues, where, null) > 0;
    }


    public Cursor getAllTodos() {
        String[] columns = {KEY_ID, KEY_NAME, KEY_IMAGE, KEY_DESC};
        return db.query(TABLE_NAME,new String[] { KEY_ID,
                KEY_NAME, KEY_IMAGE , KEY_DESC }, null, null, null, null, null);
    }



    public List<ProductModel> GetListOfProducts() {

        List<ProductModel> pom =new ArrayList<ProductModel>();
        String[] columns = {KEY_ID, KEY_NAME, KEY_IMAGE, KEY_DESC};
        Cursor cursor=  db.query(TABLE_NAME,new String[] { KEY_ID,
                KEY_NAME, KEY_IMAGE , KEY_DESC }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModel item = new ProductModel();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setTitle(Integer.parseInt(cursor.getString(1)));
                item.setImg_url(Integer.parseInt(cursor.getString(2)));
                item.setDesc(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                pom.add(item);
            } while (cursor.moveToNext());
        }
        return pom;
    }



    public ProductModel getProduct(int  id) {
        String[] columns = {KEY_ID, KEY_NAME,KEY_IMAGE, KEY_DESC};
        String where = KEY_ID + "="+id;
        Cursor cursor = db.query(TABLE_NAME, columns, where, null, null, null, null);
        ProductModel task = null;
        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(NAME_COLUMN);
            String image = cursor.getString(IMAGE_COLUMN);
            String description = cursor.getString(DESCRIPTION_COLUMN);

         //   task = new ProductModel(id,name,image, description);
            task = new ProductModel(id, Integer.parseInt(name), Integer.parseInt(image),  Integer.parseInt(description));
        }
        return task;
    }


    public boolean deleteProduct(long id){

            String where = KEY_ID + "=" + id;
            return db.delete(TABLE_NAME, where, null) > 0;



        //String[] argumenty={""+id};
        //db.delete("telefony", "nr=?", argumenty);
    }









}
