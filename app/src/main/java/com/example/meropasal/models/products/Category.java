package com.example.meropasal.models.products;

import java.util.List;

public class Category {

    private String _id;
    private String category_name;
    private String image;

    private boolean success;
    private String message;
    private List<Category> categories;

    //Empty Constructor
    public Category(){}

    public Category(String _id, String category_name, String image){
        this._id = _id;
        this.category_name = category_name;
        this.image = image;
    }

    //For Get Categories Response
    public Category(boolean success, String message, List<Category> categories){
        this.success  = success;
        this.message = message;
        this.categories = categories;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String get_id() {
        return _id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getImage() {
        return image;
    }
}
