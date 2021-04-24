package com.example.pembayaranspp.View.DataPembayaran;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import android.helper.DateTimePickerEditText;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Pembayaran.PembayaranItem;
import com.example.pembayaranspp.Model.Pembayaran.ResponsePembayaran;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudpembayaranActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView id_petugasTxt, nisnTxt, bulanTxt, tahunTxt, idsppTxt, jmlhTxt;
    private String id_pembayaran = null;
    private PembayaranItem receivedpembayaranitem;
    private Context c = CrudpembayaranActivity.this;
    private DateTimePickerEditText tglTxt;
    ActionBar actionBar;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {

        id_petugasTxt = findViewById(R.id.idpetugasTxt);
        nisnTxt = findViewById(R.id.idnisnTxt);
        bulanTxt = findViewById(R.id.idbulanTxt);
        tahunTxt = findViewById(R.id.idtahunTxt);
        idsppTxt = findViewById(R.id.id_sppTxt);
        jmlhTxt = findViewById(R.id.id_jumlahbayar);

        tglTxt = findViewById(R.id.idtanggalTxt);
        tglTxt.setFormat(Common.DATE_FORMAT);
    }

    /**
     * The following method will allow us insert data typed in this page into th
     * e database
     */
    private void insertData() {
        String id_petugas, nisn, tanggal, bulan, tahun, idspp, jmlh;
        if (Common.validatepembayaran(jmlhTxt)) {
            id_petugas = id_petugasTxt.getText().toString();
            nisn = nisnTxt.getText().toString();
            bulan = bulanTxt.getText().toString();
            tahun = tahunTxt.getText().toString();
            idspp = idsppTxt.getText().toString();
            jmlh = jmlhTxt.getText().toString();

            if (tglTxt.getDate() != null) {
                tanggal = tglTxt.getDate().toString();
            } else {
                tglTxt.setError("Invalid Date");
                tglTxt.requestFocus();
                return;
            }

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponsePembayaran> insertData = api.insertDataPembayaran("INSERT",
                    id_petugas, nisn, tanggal, bulan, tahun, idspp, jmlh);

            insertData.enqueue(new Callback<ResponsePembayaran>() {
                @Override
                public void onResponse(Call<ResponsePembayaran> call,
                                       Response<ResponsePembayaran> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Common.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        Common.openActivity(c, DatapembayaranActivity.class);
                    }
                }

                @Override
                public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                }
            });
        }
    }

    /**
     * The following method will allow us update the current scientist's data in the database
     */
    private void updateData() {
        String id_petugas, nisn, tanggal, bulan, tahun, idspp, jmlh;
        if (Common.validatepembayaran(jmlhTxt)) {
            id_petugas = id_petugasTxt.getText().toString();
            nisn = nisnTxt.getText().toString();
            bulan = bulanTxt.getText().toString();
            tahun = tahunTxt.getText().toString();
            idspp = idsppTxt.getText().toString();
            jmlh = jmlhTxt.getText().toString();

            if (tglTxt.getDate() != null) {
                tanggal = tglTxt.getDate().toString();
            } else {
                tglTxt.setError("Invalid Date");
                tglTxt.requestFocus();
                return;
            }

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponsePembayaran> update = api.updateDataPembayaran("UPDATE",
                    id_pembayaran ,id_petugas, nisn, tanggal, bulan,
                    tahun, idspp, jmlh);
            update.enqueue(new Callback<ResponsePembayaran>() {
                @Override
                public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Common.show(c, response.body().getMessage());
                        Common.openActivity(c, DatapembayaranActivity.class);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
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

        Call<ResponsePembayaran> del = api.removepembayaran("DELETE", id_pembayaran);

        del.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Common.show(c, response.body().getMessage());
                    Common.openActivity(c, DatapembayaranActivity.class);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
            }
        });
    }

    /**
     * Show selected star in our edittext
     */
    private void showSelectedStarInEditText() {
        id_petugasTxt.setOnClickListener(v -> Common.selectpetugas(c, id_petugasTxt));
        nisnTxt.setOnClickListener(v -> Common.selectnisn(c, nisnTxt));
        bulanTxt.setOnClickListener(v -> Common.select_bulan(c, bulanTxt));
        tahunTxt.setOnClickListener(v -> Common.selecttahun(c, tahunTxt));
        idsppTxt.setOnClickListener(v -> Common.selectidspp(c, idsppTxt));
    }

    /**
     * Let's inflate our menu based on the role this page has been opened for.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedpembayaranitem == null) {
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
                if (receivedpembayaranitem != null) {
                    updateData();
                } else {
                    Common.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedpembayaranitem != null) {
                    deleteData();
                } else {
                    Common.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Common.openActivity(this, DatapembayaranActivity.class);
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
        Object o = Common.receivePembayaran(getIntent(), c);
        if (o != null) {
            receivedpembayaranitem = (PembayaranItem) o;
            id_pembayaran = receivedpembayaranitem.getIdPembayaran();
            id_petugasTxt.setText(receivedpembayaranitem.getIdPetugas());
            nisnTxt.setText(receivedpembayaranitem.getNisn());
            bulanTxt.setText(receivedpembayaranitem.getBulanDibayar());
            tahunTxt.setText(receivedpembayaranitem.getTahunDibayar());
            idsppTxt.setText(receivedpembayaranitem.getIdSpp());
            jmlhTxt.setText(receivedpembayaranitem.getJumlahBayar());
            Object tanggal = receivedpembayaranitem.getTglBayar();
            if (tanggal != null) {
                String t = tanggal.toString();
                tglTxt.setDate(Common.giveMeDate(t));
            }
        }
    }

    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_pembayaran);

        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Data Pembayaran");

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