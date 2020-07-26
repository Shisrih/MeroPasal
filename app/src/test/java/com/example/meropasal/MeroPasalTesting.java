package com.example.meropasal;


import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.user.User;
import com.example.meropasal.network.API.AuthApi;
import com.example.meropasal.network.API.ProductApi;
import com.example.meropasal.network.API.UserApi;
import com.example.meropasal.network.RetrofitIniti;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class MeroPasalTesting {
    AuthApi authInterface = RetrofitIniti.initialize().create(AuthApi.class);
    UserApi userInterface = RetrofitIniti.initialize().create(UserApi.class);
    ProductApi productInterface = RetrofitIniti.initialize().create(ProductApi.class);




    @Test
    public void login_test(){
        Call<User> userCall = authInterface.login(new User("hemantchand32@gmail.com","hemant"));
        try{
            Response<User> loginResponse = userCall.execute();
            assertTrue(loginResponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    @Test
    public void signup_test(){
        Call<User> userCall = authInterface.register(new User("Hemant","Chand","Dillibazar","9841577822","hemantchand72@gmail.com","Account", "password"));
        try{
            Response<User> signUpReponse = userCall.execute();
            assertTrue(signUpReponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void profile_test(){
        Call<User> userCall = userInterface.getUser("");
        try{
            Response<User> profileResponse = userCall.execute();
            assertTrue(profileResponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void product_test(){
        Call<Product> productCall = productInterface.getExclusiveProducts();
        try{
            Response<Product> productResponse = productCall.execute();
            assertTrue(productResponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    @Test
    public void category_test(){
        Call<Category> categoryCall = productInterface.getCategoriesWithLimit("6");
        try{
            Response<Category> categoryResponse = categoryCall.execute();
            assertTrue(categoryResponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }



}
