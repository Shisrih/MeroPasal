package com.example.meropasal.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.utiils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private  static final String DB_NAME = "MeroPasal";
    private static final int DB_VERSION = 1;
    private static final String TAG = "DbHelper";

    Context context;

    String tbl_create = "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, productid TEXT, name TEXT , imgurl TEXT )";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tbl_create);
    }


    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS cart");

            onCreate(db);
        }
    }

    public boolean addToCart(CartModel cartModel){
        try{
            SQLiteDatabase db = getWritableDatabase();
            String imgurl = Constants.IMAGE_URL + "products/" + cartModel.getProductId() + "/" + cartModel.getSingleImg();
            db.execSQL("INSERT INTO cart (userid, productid, name, imgurl) values ('" + cartModel.getUserid()+ "','"+ cartModel.getProductId()+"', '"+ cartModel.getName()+"', '"+imgurl+"')");
            Toast.makeText(context, "Product Added To Cart", Toast.LENGTH_SHORT).show();

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
                    cartModelList.add(new CartModel(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                }

            }
            return cartModelList;

        }catch(Exception e){

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
//    public boolean updateStudentDetails(int id, String name, String email, String phone) {
//        try {
//            SQLiteDatabase db = getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put("name", name);
//            cv.put("email", email);
//            cv.put("phone", phone);
//
//            db.update("students", cv, "id=?", new String[]{String.valueOf(id)});
//            return true;
//        } catch (Exception e) {
//            Log.d("update_error", e.toString());
//            return false;
//        }
//
//    }
}
