package com.example.meropasal.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.meropasal.R;
import com.example.meropasal.adapters.HomeProductsAdapter;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.presenters.search.SearchPresenter;
import com.example.meropasal.views.SearchContract;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private EditText searchbar;
    private RecyclerView searchproductview;
    private String search;
    private SearchPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewInit();
    }

    private void viewInit(){
        searchbar = findViewById(R.id.searchbar);
        searchproductview = findViewById(R.id.searchproductview);

        Intent intent = getIntent();
        search = intent.getStringExtra("search");
        presenter  = new SearchPresenter(this);

        presenter.getProductsBySearch(search);


    }


    @Override
    public void getSearchProducts(List<ProductRes> products) {
        HomeProductsAdapter adapter = new HomeProductsAdapter(SearchActivity.this, products);
        searchproductview.setLayoutManager(new StaggeredGridLayoutManager(2  , LinearLayoutManager.VERTICAL));

        searchproductview.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(searchproductview, false);
    }

    @Override
    public void onFailed(String message) {

    }
}