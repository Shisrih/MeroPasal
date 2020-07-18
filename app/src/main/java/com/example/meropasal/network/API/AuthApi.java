package com.example.meropasal.network.API;

import com.example.meropasal.models.user.User;
import com.example.meropasal.models.user.VerificationResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<User> login(@Body User user);

    @POST("auth/register")
    Call<User> register(@Body User user);

    @FormUrlEncoded
    @POST("auth/checkemail")
    Call<User> checkEmail(@Field("email") String email);


    @FormUrlEncoded
    @POST("auth/sendverificationcode")
    Call<VerificationResponse> sendVerificationCode(@Field("phone") String phone);


    @FormUrlEncoded
    @POST("auth/verifycode")
    Call<VerificationResponse> verifyCode(@Field("sid") String sid,
                                          @Field("phone") String phone,
                                          @Field("code")String code);


}
