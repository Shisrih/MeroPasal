package com.example.meropasal.data.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.meropasal.R;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.products.FavModel;
import com.example.meropasal.utiils.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private  static final String DB_NAME = "MeroPasal";
    private static final int DB_VERSION = 2;
    private static final String TAG = "DbHelper";

    Context context;

    String tbl_create = "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, productid TEXT, name TEXT , imgurl TEXT, price FLOAT ,quantity INTEGER, totalprice FLOAT )";
    String fav_tbl_create = "CREATE TABLE favourite (id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, productid TEXT, name TEXT , imgurl TEXT, price FLOAT  )";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tbl_create);
        db.execSQL(fav_tbl_create);
    }


    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS cart");
            db.execSQL("DROP TABLE IF EXISTS favourite");
            onCreate(db);
        }
    }

    public boolean addToCart(CartModel cartModel){
        try{
            SQLiteDatabase db = getWritableDatabase();
            SQLiteDatabase readdb = this.getReadableDatabase();
            int quantity = 1;

            Cursor cursor = readdb.rawQuery("SELECT * from cart WHERE productid = ? AND userid = ?", new String[]{String.valueOf(cartModel.getProductId()), String.valueOf(cartModel.getUserid())});

            if(cursor.moveToFirst()){

                quantity = cursor.getInt(6) + quantity;
                float newprice = cartModel.getPrice() * quantity;

            ContentValues cv = new ContentValues();
            cv.put("quantity", quantity);
            cv.put("totalprice", newprice);

            db.update("cart", cv, "productid=?", new String[]{String.valueOf(cursor.getString(2))});


            } else {


                String imgurl = Constants.IMAGE_URL + "products/" + cartModel.getProductId() + "/" + cartModel.getSingleImg();
                db.execSQL("INSERT INTO cart (userid, productid, name, imgurl, price, quantity, totalprice) values ('" + cartModel.getUserid()+ "','"+ cartModel.getProductId()+"', '"+ cartModel.getName()+"', '"+imgurl+"', '"+ cartModel.getPrice() +"' , '"+quantity+"','" + cartModel.getTotalPrice() + "')");
            }


            return  true;
        }catch (Exception e){
            Log.d(TAG, "addToCart: " + e.toString());
            return false;
        }
    }

    public List<CartModel> getFromCart(String id){
        List<CartModel> cartModelList = new ArrayList<>();
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor =db.rawQuery("SELECT * from cart WHERE userid = ?", new String[]{String.valueOf(id)});
            if(cursor !=null){
                while(cursor.moveToNext()){
                    cartModelList.add(new CartModel(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getFloat(5), cursor.getInt(6), cursor.getFloat(7)));
                }

            }
            return cartModelList;

        }catch(Exception e){
            Log.d(TAG, "getCart: " + e.toString());

        }
        return null;
    }


    public void deleteFromCart(int id) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.delete("cart", "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d("delete_error", e.toString());
        }

    }

    public void deleteAllCart(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.delete("cart",null , null);
        } catch (Exception e) {
            Log.d("delete_error", e.toString());
        }

    }


    public boolean updateCart(int id, float price, int quantity){
        try{
            SQLiteDatabase db = getWritableDatabase();
            SQLiteDatabase readdb = this.getReadableDatabase();


            Cursor cursor = readdb.rawQuery("SELECT * from cart WHERE id = ?", new String[]{String.valueOf(id)});


            if(cursor.moveToFirst()) {
                ContentValues cv = new ContentValues();


                cv.put("quantity", quantity);
                cv.put("totalprice", price);

                db.update("cart", cv, "id=?", new String[]{String.valueOf(id)});

                return true;

            }else{
                Log.d("update_error", "Not Found");
            }
            }catch (Exception e){
            Log.d("update_error", e.toString());
            return false;
        }
        return false;
    }


    public boolean addToFav(FavModel favModel){
        try{
            SQLiteDatabase db = getWritableDatabase();
            SQLiteDatabase readdb = this.getReadableDatabase();

            String imgurl = Constants.IMAGE_URL + "products/" + favModel.getProductId() + "/" + favModel.getSingleImg();
            db.execSQL("INSERT INTO favourite (userid, productid, name, imgurl, price) values ('" + favModel.getUserid()+ "','"+ favModel.getProductId()+"', '"+ favModel.getName()+"', '"+imgurl+"', '"+ favModel.getPrice() +"' )");



            return  true;
        }catch (Exception e){
            Log.d(TAG, "addToFav: " + e.toString());
            return false;
        }
    }


    public boolean checkFav(FavModel favModel){

        try{
            SQLiteDatabase readdb = this.getReadableDatabase();



            Cursor cursor = readdb.rawQuery("SELECT * from favourite WHERE productid = ? AND userid = ?", new String[]{String.valueOf(favModel.getProductId()), String.valueOf(favModel.getUserid())});
            if(cursor.moveToFirst()) {
            return true;
            }
        }catch (Exception e){
            Log.d(TAG, "checkFav: " + e.toString());
        return false;
        }
        return  false;
    }


    public List<FavModel> getFromFav(String id){
        List<FavModel> favModelList = new ArrayList<>();
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor =db.rawQuery("SELECT * from favourite WHERE userid = ?", new String[]{String.valueOf(id)});
            if(cursor !=null){
                while(cursor.moveToNext()){
                    favModelList.add(new FavModel(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getFloat(5)));
                }

            }
            return favModelList;

        }catch(Exception e){
            Log.d(TAG, "getFav: " + e.toString());

        }
        return null;
    }


    public void unFavourite(FavModel favModel){
        try{
            SQLiteDatabase readdb = this.getReadableDatabase();


                readdb.delete("favourite", "productid=? AND userid = ?", new String[]{String.valueOf(favModel.getProductId()), String.valueOf(favModel.getUserid())});

        }catch (Exception e){
            Log.d(TAG, "unFavourite: " + e.toString());
        }
    }

    public void deleteFromFav(int id) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.delete("favourite", "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d("delete_error", e.toString());
        }

    }

    public void deleteAllFav(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.delete("favourites",null , null);
        } catch (Exception e) {
            Log.d("delete_error", e.toString());
        }

    }
}
