package com.example.pembayaranspp.View.DataPetugas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Petugas.PetugasItem;
import com.example.pembayaranspp.Model.Petugas.ResponsePetugas;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPetugasActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,MenuItem.OnActionExpandListener {

    private RecyclerView recyclerView;
    private AdapterPetugas adapterPetugas;
    private LinearLayoutManager layoutManager;
    public ArrayList<PetugasItem> allPagespetugasData = new ArrayList();
    private List<PetugasItem> currentPagepetugasData;
    private Boolean isScrolling = false;
    private int currentpetugasData, totalpetugasData, scrolledOutpetugasData;
    private ProgressBar mProgressBar;
    ActionBar actionBar;

    private void initializeViews() {
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        Common.showProgressBar(mProgressBar);
        recyclerView = findViewById(R.id.rv_petugas);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapterPetugas = new AdapterPetugas(this, allPagespetugasData);
        recyclerView.setAdapter(adapterPetugas);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {

        adapterPetugas.searchString = queryString;
        ApiClient config = new ApiClient();
        ApiInterface api = config.service;
        Call<ResponsePetugas> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.searchpetugas("GET_PAGINATED", queryString, start, limit);
            Common.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            Common.showProgressBar(mProgressBar);
            retrievedData = api.searchpetugas("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            Common.showProgressBar(mProgressBar);
            retrievedData = api.getAllDataPetugas();
        }
        retrievedData.enqueue(new Callback<ResponsePetugas>() {
            @Override
            public void onResponse(Call<ResponsePetugas> call, Response<ResponsePetugas>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPagepetugasData = response.body().getResult();

                if (currentPagepetugasData != null && currentPagepetugasData.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagespetugasData.clear();
                    }
                    for (int i = 0; i < currentPagepetugasData.size(); i++) {
                        allPagespetugasData.add(currentPagepetugasData.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagespetugasData.clear();
                    }
                }
                adapterPetugas.notifyDataSetChanged();
                Common.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                Common.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
            }
        });
    }

    private void listenToRecyclerViewScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView rv, int newState) {
                //when scrolling starts
                super.onScrollStateChanged(rv, newState);
                //check for scroll state
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                // When the scrolling has stopped
                super.onScrolled(rv, dx, dy);
                currentpetugasData = layoutManager.getChildCount();
                totalpetugasData = layoutManager.getItemCount();
                scrolledOutpetugasData = ((LinearLayoutManager) rv.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentpetugasData + scrolledOutpetugasData ==
                        totalpetugasData)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        retrieveAndFillRecyclerView("GET_PAGINATED",
                                adapterPetugas.searchString,
                                String.valueOf(totalpetugasData), "5");

                    } else {
                        // Scrolling down
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.petugas_page_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                Common.openActivity(this, CrudPetugasActivity.class);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        retrieveAndFillRecyclerView("GET_PAGINATED_SEARCH", query, "0", "5");
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_petugas);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Data Petugas");

        initializeViews();
        this.listenToRecyclerViewScroll();
        setupRecyclerView();
        retrieveAndFillRecyclerView("GET_PAGINATED", "", "0", "5");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}