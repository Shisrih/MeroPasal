package com.example.meropasal.data.interactors;

import android.util.Log;

import com.example.meropasal.models.User;
import com.example.meropasal.network.API.UserApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.views.ProfileContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileInteractor {

    private ProfileContract.Presenter presenter;
    private static final String TAG = "ProfileInteractor";


    public ProfileInteractor(ProfileContract.Presenter presenter){
        this.presenter = presenter;
    }

    public void getUser(String token){
        UserApi api = RetrofitIniti.initialize().create(UserApi.class);
        Call<User> responseCall = api.getUser(token);

        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.body().isSuccess()){
                    String fname = response.body().getFirstname();
                    String lname = response.body().getLastname();
                    String location = response.body().getLocation();
                    String phone = response.body().getPhone();
                    String email = response.body().getEmail();

                    presenter.getUser(new User(fname,lname,location,phone,email));
                }else{
                    presenter.onFailed("User Not Logged in!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Connection Error!");
            }
        });

    }
}
