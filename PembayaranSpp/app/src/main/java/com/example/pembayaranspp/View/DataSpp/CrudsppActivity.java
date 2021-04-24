package com.example.pembayaranspp.View.DataSpp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Spp.ResponseSpp;
import com.example.pembayaranspp.Model.Spp.SppItem;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudsppActivity extends AppCompatActivity {

    private MaterialAutoCompleteTextView tahunTxt, nominalTxt;
    private String id_spp = null;
    private SppItem receivedSppitem;
    private Context c = CrudsppActivity.this;
    ActionBar actionBar;


    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    private void initializeWidgets() {

        tahunTxt = findViewById(R.id.id_tahun);
        nominalTxt = findViewById(R.id.id_nominal);

        nominalTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String harga = nominalTxt.getText().toString();
                if (TextUtils.isEmpty(harga)){
                    Toast.makeText(CrudsppActivity.this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    String resultRupiah = "Nominal : " + formatRupiah(Double.parseDouble(harga));
                    nominalTxt.setText(resultRupiah);
                }
            }
        });

    }

    private void insertData() {
        String tahun, nominal;
        if (Common.validatespp(tahunTxt, nominalTxt)) {
            tahun = tahunTxt.getText().toString();
            nominal = nominalTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponseSpp> insertDataspp = api.insertDataSpp("INSERT", tahun, nominal);

            insertDataspp.enqueue(new Callback<ResponseSpp>() {
                @Override
                public void onResponse(Call<ResponseSpp> call,
                                       Response<ResponseSpp> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Common.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        Common.openActivity(c, DatasppActivity.class);
                    }
                }
                @Override
                public void onFailure(Call<ResponseSpp> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                }
            });
        }
    }

    private void updateData() {
        String tahun, nominal;
        if (Common.validatespp(tahunTxt, nominalTxt)) {
            tahun = tahunTxt.getText().toString();
            nominal = nominalTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponseSpp> updateDataSpp = api.updateDataSpp("UPDATE", id_spp, tahun, nominal);
            updateDataSpp.enqueue(new Callback<ResponseSpp>() {
                @Override
                public void onResponse(Call<ResponseSpp> call, Response<ResponseSpp> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Common.show(c, response.body().getMessage());
                        Common.openActivity(c, DatasppActivity.class);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseSpp> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                }
            });
        }
    }

    private void deleteData() {

        ApiClient config = new ApiClient();
        ApiInterface api = config.service;

        Call<ResponseSpp> removeSpp = api.removeSpp("DELETE", id_spp);

        removeSpp.enqueue(new Callback<ResponseSpp>() {
            @Override
            public void onResponse(Call<ResponseSpp> call, Response<ResponseSpp> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());

                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Common.show(c, response.body().getMessage());
                    Common.openActivity(c, DatasppActivity.class);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseSpp> call, Throwable t) {
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
            }
        });
    }

    private void showSelectedStarInEditText() {
        tahunTxt.setOnClickListener(v -> Common.selecttahun(c, tahunTxt));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedSppitem == null) {
            getMenuInflater().inflate(R.menu.petugas_activity, menu);
//            headerTxt.setText("Add New Scientist");
        } else {
            getMenuInflater().inflate(R.menu.petugas_crud, menu);
//            headerTxt.setText("Edit Existing Scientist");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                insertData();
                return true;
            case R.id.editMenuItem:
                if (receivedSppitem != null) {
                    updateData();
                } else {
                    Common.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedSppitem != null) {
                    deleteData();
                } else {
                    Common.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Common.openActivity(this, DatasppActivity.class);
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
        Object o = Common.receiveSpp(getIntent(), c);
        if (o != null) {
            receivedSppitem = (SppItem) o;
            id_spp = receivedSppitem.getIdSpp();
            tahunTxt.setText(receivedSppitem.getTahun());
            nominalTxt.setText(receivedSppitem.getNominal());
        } else {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_spp);

        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Data Spp");

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }

}
