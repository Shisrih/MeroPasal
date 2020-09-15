package com.example.meropasal.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.adapters.FavouritesAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.FavModel;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;

import java.util.List;

public class Favourites extends Fragment {

    public static LinearLayout emptyfav, notsignin;
    public static RelativeLayout favlayout;
    private DbHelper db;
    private SharedPreferences sharedPreferences;
    private List<FavModel> favs;
    private RecyclerView favView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourites,container,false);

        setRetainInstance(true);
        viewInit(root);


        return root;


    }


    private void viewInit(View root){
        emptyfav = root.findViewById(R.id.emptyfav);
        notsignin = root.findViewById(R.id.notsignin);
        favlayout = root.findViewById(R.id.favlayout);
        favView = root.findViewById(R.id.favview);



        db = new DbHelper(getContext());

    }


    private void showNotSignedIn() {
        emptyfav.setVisibility(View.GONE);
        favlayout.setVisibility(View.GONE);
        notsignin.setVisibility(View.VISIBLE);
    }

    public static void setEmptyfav(){
        emptyfav.setVisibility(View.VISIBLE);
        favlayout.setVisibility(View.GONE);
        notsignin.setVisibility(View.GONE);
    }


    private void showFav(){
        emptyfav.setVisibility(View.GONE);
        notsignin.setVisibility(View.GONE);
        favlayout.setVisibility(View.VISIBLE);


        FavouritesAdapter adapter = new FavouritesAdapter(getContext(), favs);
        favView.setLayoutManager(new LinearLayoutManager(getContext()));
        favView.setAdapter(adapter);



    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString(Constants.USER_ID, null);

        favs =  db.getFromFav(userid);

        String token = sharedPreferences.getString(Constants.TOKEN, null);

        if(Authenticator.checkLoginStatus(sharedPreferences)){
            if(!favs.isEmpty()){
                showFav();
            }else{
                setEmptyfav();
            }
        }else{
            showNotSignedIn();
        }
    }
}

