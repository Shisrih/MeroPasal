package com.example.meropasal.data.interactors.user;

import android.util.Log;

import com.example.meropasal.models.user.User;
import com.example.meropasal.network.API.UserApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.views.UpdateContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInteractor {

    private UpdateContract.Presenter presenter;
    private static final String TAG = "UpdateInteractor";

    public UpdateInteractor(UpdateContract.Presenter presenter) {
        this.presenter = presenter;
    }



    public void updateUser(String token, User user){
        UserApi api = RetrofitIniti.initialize().create(UserApi.class);
        Call<User> responseCall = api.updateUserDetails(token, user);

        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.onUpdateUser(response.body().getUser());
                    }else{
                        presenter.onFailed("Couldn't update user");
                    }
                }else{
                    presenter.onFailed("Couldn't update user");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Something is wrong");
            }
        });
    }
}
