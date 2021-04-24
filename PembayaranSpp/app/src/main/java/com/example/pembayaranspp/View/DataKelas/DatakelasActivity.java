package com.example.pembayaranspp.View.DataKelas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Kelas.KelasItem;
import com.example.pembayaranspp.Model.Kelas.ResponseKelas;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatakelasActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    //We define our instance fields
    private RecyclerView recyclerView;
    private AdapterKelas adapterKelas;
    private LinearLayoutManager layoutManager;
    public ArrayList<KelasItem> allPagesKelasItem = new ArrayList();
    private List<KelasItem> currentPageKelasItem;
    private Boolean isScrolling = false;
    private int currentKelas, totalKelas, scrolledOutKelas;
    private ProgressBar mProgressBar;
    ActionBar actionBar;

    /**
     * We initialize our widgets
     */
    private void initializeViews() {
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        Common.showProgressBar(mProgressBar);
        recyclerView = findViewById(R.id.rv_kelas);
    }

    /**
     * This method will setup oir RecyclerView
     */
    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapterKelas = new AdapterKelas(this, allPagesKelasItem);
        recyclerView.setAdapter(adapterKelas);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {

        adapterKelas.searchString = queryString;

        ApiClient config = new ApiClient();
        ApiInterface api = config.service;

        Call<ResponseKelas> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.searchkelas("GET_PAGINATED", queryString, start, limit);
            Common.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            Common.showProgressBar(mProgressBar);
            retrievedData = api.searchkelas("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            Common.showProgressBar(mProgressBar);
            retrievedData = api.getAllDataKelas();
        }
        retrievedData.enqueue(new Callback<ResponseKelas>() {
            @Override
            public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPageKelasItem = response.body().getResult();

                if (currentPageKelasItem != null && currentPageKelasItem.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesKelasItem.clear();
                    }
                    for (int i = 0; i < currentPageKelasItem.size(); i++) {
                        allPagesKelasItem.add(currentPageKelasItem.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesKelasItem.clear();
                    }
                }
                adapterKelas.notifyDataSetChanged();
                Common.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponseKelas> call, Throwable t) {
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
                currentKelas = layoutManager.getChildCount();
                totalKelas = layoutManager.getItemCount();
                scrolledOutKelas = ((LinearLayoutManager) rv.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentKelas + scrolledOutKelas == totalKelas)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        retrieveAndFillRecyclerView("GET_PAGINATED",
                                adapterKelas.searchString,
                                String.valueOf(totalKelas), "5");

                    } else {
                        // Scrolling down
                    }
                }
            }
        });
    }
    /**
     * We inflate our menu. We show SearchView inside the toolbar
     */
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
                Common.openActivity(this, CrudkelasActivity.class);
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
        setContentView(R.layout.activity_data_kelas);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Data Kelas");

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

//end