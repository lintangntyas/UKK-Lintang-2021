package com.example.pembayaranspp.View.DataPetugas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Petugas.PetugasItem;
import com.example.pembayaranspp.Model.Petugas.ResponsePetugas;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudPetugasActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView usernameTxt, passwordTxt, nama_petugasTxt, levelTxt;
    private String id_petugas = null;
    private PetugasItem receivedpetugasData;
    private Context c = CrudPetugasActivity.this;
    ActionBar actionBar;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {

        usernameTxt = findViewById(R.id.id_username);
        passwordTxt = findViewById(R.id.id_password);
        nama_petugasTxt = findViewById(R.id.id_namapetugas);
        levelTxt = findViewById(R.id.id_level);
    }

    /**
     * The following method will allow us insert data typed in this page into th
     * e database
     */
    private void insertData() {
        String username, password, nama_petugas, level;
        if (Common.validatepetugas(usernameTxt, passwordTxt, nama_petugasTxt)) {
            username = usernameTxt.getText().toString();
            password = passwordTxt.getText().toString();
            nama_petugas = nama_petugasTxt.getText().toString();
            level = levelTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponsePetugas> insertData = api.insertData("INSERT", username, password, nama_petugas, level);

            insertData.enqueue(new Callback<ResponsePetugas>() {
                @Override
                public void onResponse(Call<ResponsePetugas> call,
                                       Response<ResponsePetugas> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Common.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        Common.openActivity(c, DataPetugasActivity.class);
                    }
                }

                @Override
                public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                }
            });
        }
    }

    /**
     * The following method will allow us update the current scientist's data in the database
     */
    private void updateData() {
        String username, password, nama_petugas, level;
        if (Common.validatepetugas(usernameTxt, passwordTxt, nama_petugasTxt)) {
            username = usernameTxt.getText().toString();
            password = passwordTxt.getText().toString();
            nama_petugas = nama_petugasTxt.getText().toString();
            level = levelTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponsePetugas> update = api.updateData("UPDATE", id_petugas, username, password, nama_petugas,
                    level);
            update.enqueue(new Callback<ResponsePetugas>() {
                @Override
                public void onResponse(Call<ResponsePetugas> call, Response<ResponsePetugas> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Common.show(c, response.body().getMessage());
                        Common.openActivity(c, DataPetugasActivity.class);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                }
            });
        }
    }

    /**
     * The following method will allow us delete data from database
     */
    private void deleteData() {

        ApiClient config = new ApiClient();
        ApiInterface api = config.service;

        Call<ResponsePetugas> del = api.remove("DELETE", id_petugas);

        del.enqueue(new Callback<ResponsePetugas>() {
            @Override
            public void onResponse(Call<ResponsePetugas> call, Response<ResponsePetugas> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Common.show(c, response.body().getMessage());
                    Common.openActivity(c, DataPetugasActivity.class);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
            }
        });
    }

    /**
     * Show selected star in our edittext
     */
    private void showSelectedStarInEditText() {
        levelTxt.setOnClickListener(v -> Common.selectlevel(c, levelTxt));
    }

    /**
     * Let's inflate our menu based on the role this page has been opened for.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedpetugasData == null) {
            getMenuInflater().inflate(R.menu.petugas_activity, menu);
        } else {
            getMenuInflater().inflate(R.menu.petugas_crud, menu);
//            headerTxt.setText("Edit Existing Data petugas");
        }
        return true;
    }

    /**
     * Let's listen to menu action events and perform appropriate function
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                insertData();
                return true;
            case R.id.editMenuItem:
                if (receivedpetugasData != null) {
                    updateData();
                } else {
                    Common.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedpetugasData != null) {
                    deleteData();
                } else {
                    Common.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Common.openActivity(this, DataPetugasActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Object o = Common.receivePetugas(getIntent(), c);
        if (o != null) {
            receivedpetugasData = (PetugasItem) o;
            id_petugas = receivedpetugasData.getIdPetugas();
            usernameTxt.setText(receivedpetugasData.getUsername());
            passwordTxt.setText(receivedpetugasData.getPassword());
            nama_petugasTxt.setText(receivedpetugasData.getNamaPetugas());
            levelTxt.setText(receivedpetugasData.getLevel());
        }
    }

    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_petugas);

        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Data Petugas");

        this.initializeWidgets();
        this.showSelectedStarInEditText();
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