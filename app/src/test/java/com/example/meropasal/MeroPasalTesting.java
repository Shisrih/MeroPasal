package com.example.meropasal;

import com.example.meropasal.models.User;
import com.example.meropasal.network.API.AuthApi;
import com.example.meropasal.network.RetrofitIniti;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class MeroPasalTesting {
    AuthApi authInterface = RetrofitIniti.initialize().create(AuthApi.class);
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
}
