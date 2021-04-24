package com.example.pembayaranspp.View.DataSiswa;

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
import com.example.pembayaranspp.Model.Siswa.ResponseSiswa;
import com.example.pembayaranspp.Model.Siswa.SiswaItem;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatasiswaActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    //We define our instance fields
    private RecyclerView recyclerView;
    private AdapterSiswa adapterSiswa;
    private LinearLayoutManager layoutManager;
    public ArrayList<SiswaItem> allPagesSiswaItem = new ArrayList();
    private List<SiswaItem> currentPageSiswaItem;
    private Boolean isScrolling = false;
    private int currentSiswa, totalSiswa, scrolledOutSiswa;
    private ProgressBar mProgressBar;
    ActionBar actionBar;

    private void initializeViews() {
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        Common.showProgressBar(mProgressBar);
        recyclerView = findViewById(R.id.rv_siswa);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapterSiswa = new AdapterSiswa(this, allPagesSiswaItem);
        recyclerView.setAdapter(adapterSiswa);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {

        adapterSiswa.searchString = queryString;

        ApiClient config = new ApiClient();
        ApiInterface api = config.service;

        Call<ResponseSiswa> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.searchSiswa("GET_PAGINATED", queryString, start, limit);
            Common.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            Common.showProgressBar(mProgressBar);
            retrievedData = api.searchSiswa("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            Common.showProgressBar(mProgressBar);
            retrievedData = api.getAllDataSiswa();
        }
        retrievedData.enqueue(new Callback<ResponseSiswa>() {
            @Override
            public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPageSiswaItem = response.body().getResult();

                if (currentPageSiswaItem != null && currentPageSiswaItem.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesSiswaItem.clear();
                    }
                    for (int i = 0; i < currentPageSiswaItem.size(); i++) {
                        allPagesSiswaItem.add(currentPageSiswaItem.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesSiswaItem.clear();
                    }
                }
                adapterSiswa.notifyDataSetChanged();
                Common.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponseSiswa> call, Throwable t) {
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
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // When the scrolling has stopped
                super.onScrolled(recyclerView, dx, dy);
                currentSiswa = layoutManager.getChildCount();
                totalSiswa = layoutManager.getItemCount();
                scrolledOutSiswa = ((LinearLayoutManager) recyclerView.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentSiswa + scrolledOutSiswa ==
                        totalSiswa)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        retrieveAndFillRecyclerView("GET_PAGINATED",
                                adapterSiswa.searchString,
                                String.valueOf(totalSiswa), "5");

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
                Common.openActivity(this, CrudsiswaActivity.class);
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
        setContentView(R.layout.activity_data_siswa);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Data Siswa");

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