package com.example.meropasal.models.products;

public class Category {

    private String _id;
    private String category_name;


    public Category(String _id, String category_name){
        this._id = _id;
        this.category_name = category_name;
    }

    public String get_id() {
        return _id;
    }

    public String getCategory_name() {
        return category_name;
    }
}
