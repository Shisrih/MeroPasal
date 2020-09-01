package com.example.meropasal.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.meropasal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
BottomNavigationView bottomNavigationItemView;
Homescreen home = new Homescreen();
Cart cart = new Cart();
Favourites fav = new Favourites();
Account account = new Account();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setFragment(home);

        bottomNavigationItemView = findViewById(R.id.dashboardnavigation);

        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        setFragment(home);
                        break;
                    case R.id.navigation_cart:
                        setFragment(cart);
                        break;
                    case R.id.navigation_favourites:
                        setFragment(fav);
                        break;
                    case R.id.navigation_account:
                        setFragment(account);
                        break;

                }


                return true;
            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Fragment,fragment);
        fragmentTransaction.commit();
    }
}