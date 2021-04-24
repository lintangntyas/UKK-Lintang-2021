package com.example.pembayaranspp.View.DataKelas;

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
import com.example.pembayaranspp.Model.Kelas.KelasItem;
import com.example.pembayaranspp.Model.Kelas.ResponseKelas;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudkelasActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView nama_kelasTxt, kompetensi_keahlianTxt;
    private String id_kelas = null;
    private KelasItem receivedKelasItem;
    private Context c = CrudkelasActivity.this;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {

        nama_kelasTxt = findViewById(R.id.id_kelas);
        kompetensi_keahlianTxt = findViewById(R.id.id_kompetensi);
    }

    private void insertData() {
        String nama_kelas, kompetensi_keahlian;
        if (Common.validatekelas(nama_kelasTxt, kompetensi_keahlianTxt)) {
            nama_kelas = nama_kelasTxt.getText().toString();
            kompetensi_keahlian = kompetensi_keahlianTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponseKelas> insertDatakelas = api.insertDataKelas("INSERT", nama_kelas, kompetensi_keahlian);

            insertDatakelas.enqueue(new Callback<ResponseKelas>() {
                @Override
                public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Common.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        Common.openActivity(c, DatakelasActivity.class);
                    }
                }
                @Override
                public void onFailure(Call<ResponseKelas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                }
            });
        }
    }
    /**
     * The following method will allow us update the current scientist's data in the database
     */
    private void updateData() {
        String nama_kelas, kompetensi_keahlian;
        if (Common.validatekelas(nama_kelasTxt, kompetensi_keahlianTxt)) {
            nama_kelas = nama_kelasTxt.getText().toString();
            kompetensi_keahlian = kompetensi_keahlianTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponseKelas> updateDatakelas = api.updateDataKelas("UPDATE", id_kelas, nama_kelas, kompetensi_keahlian);
            updateDatakelas.enqueue(new Callback<ResponseKelas>() {
                @Override
                public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Common.show(c, response.body().getMessage());
                        Common.openActivity(c, DatakelasActivity.class);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseKelas> call, Throwable t) {
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

        Call<ResponseKelas> removekelas = api.removekelas("DELETE", id_kelas);

        removekelas.enqueue(new Callback<ResponseKelas>() {
            @Override
            public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());

                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Common.show(c, response.body().getMessage());
                    Common.openActivity(c, DatakelasActivity.class);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseKelas> call, Throwable t) {
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
            }
        });
    }
    /**
     * Show selected star in our edittext
     */
    private void showSelectedStarInEditText() {
        kompetensi_keahlianTxt.setOnClickListener(v -> Common.selectkelas(c, kompetensi_keahlianTxt));
        nama_kelasTxt.setOnClickListener(v -> Common.selectclass(c, nama_kelasTxt));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedKelasItem == null) {
            getMenuInflater().inflate(R.menu.petugas_activity, menu);
//            headerTxt.setText("Add New Scientist");
        } else {
            getMenuInflater().inflate(R.menu.petugas_crud, menu);
//            headerTxt.setText("Edit Existing Scientist");
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
                if (receivedKelasItem != null) {
                    updateData();
                } else {
                    Common.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedKelasItem != null) {
                    deleteData();
                } else {
                    Common.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Common.openActivity(this, DatakelasActivity.class);
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
        Object o = Common.receiveKelas(getIntent(), c);
        if (o != null) {
            receivedKelasItem = (KelasItem) o;
            id_kelas = receivedKelasItem.getIdKelas();
            nama_kelasTxt.setText(receivedKelasItem.getNamaKelas());
            kompetensi_keahlianTxt.setText(receivedKelasItem.getKompetensiKeahlian());
        } else {
        }
    }
    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_kelas);

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }
}
//end