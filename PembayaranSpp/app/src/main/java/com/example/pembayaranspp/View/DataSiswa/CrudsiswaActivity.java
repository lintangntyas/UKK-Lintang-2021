package com.example.pembayaranspp.View.DataSiswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Siswa.ResponseSiswa;
import com.example.pembayaranspp.Model.Siswa.SiswaItem;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudsiswaActivity extends AppCompatActivity {

    private MaterialAutoCompleteTextView nisnTxt,nisTxt, namaTxt, id_kelasTxt, alamatTxt, notelpTxt, idsppTxt;
    private String id_siswa = null;
    private SiswaItem receivedSiswaItem;
    private Context c = CrudsiswaActivity.this;

    private void initializeWidgets() {

        nisnTxt = findViewById(R.id.idnisn);
        nisTxt = findViewById(R.id.idnis);
        namaTxt = findViewById(R.id.id_nama);
        id_kelasTxt = findViewById(R.id.kelas);
        alamatTxt = findViewById(R.id.idalamat);
        notelpTxt = findViewById(R.id.id_notepl);
        idsppTxt = findViewById(R.id.id_spp);

    }
    private void insertData() {
        String nisn,nis, nama, idkelas, alamat, notelp, idspp;
        if (Common.validatesiswa(nisnTxt,nisTxt, namaTxt, id_kelasTxt, alamatTxt, notelpTxt, idsppTxt)) {
            nisn = nisnTxt.getText().toString();
            nis = nisTxt.getText().toString();
            nama = namaTxt.getText().toString();
            idkelas = id_kelasTxt.getText().toString();
            alamat = alamatTxt.getText().toString();
            notelp = notelpTxt.getText().toString();
            idspp = idsppTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponseSiswa> insertDatasiswa = api.insertDataSiswa("INSERT",
                    nisn,nis, nama, idkelas, alamat, notelp, idspp);

            insertDatasiswa.enqueue(new Callback<ResponseSiswa>() {
                @Override
                public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Common.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        Common.openActivity(c, DatasiswaActivity.class);
                    }
                }
                @Override
                public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                }
            });
        }
    }

    private void updateData() {
        String nisn,nis, nama, idkelas, alamat, notelp, idspp;
        if (Common.validatesiswa(nisnTxt,nisTxt, namaTxt, id_kelasTxt, alamatTxt, notelpTxt, idsppTxt)) {
            nisn = nisnTxt.getText().toString();
            nis = nisTxt.getText().toString();
            nama = namaTxt.getText().toString();
            idkelas = id_kelasTxt.getText().toString();
            alamat = alamatTxt.getText().toString();
            notelp = notelpTxt.getText().toString();
            idspp = idsppTxt.getText().toString();

            ApiClient config = new ApiClient();
            ApiInterface api = config.service;

            Call<ResponseSiswa> updateDatasiswa = api.updateDataSiswa("UPDATE",
                    id_siswa,nisn, nis, nama, idkelas, alamat, notelp, idspp);
            updateDatasiswa.enqueue(new Callback<ResponseSiswa>() {
                @Override
                public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Common.show(c, response.body().getMessage());
                        Common.openActivity(c, DatasiswaActivity.class);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                }
            });
        }
    }

    private void deleteData() {

        ApiClient config = new ApiClient();
        ApiInterface api = config.service;

        Call<ResponseSiswa> removesiswa = api.removeSiswa("DELETE", id_siswa);

        removesiswa.enqueue(new Callback<ResponseSiswa>() {
            @Override
            public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());

                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Common.show(c, response.body().getMessage());
                    Common.openActivity(c, DatasiswaActivity.class);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
            }
        });
    }

    private void showSelectedStarInEditText() {
        id_kelasTxt.setOnClickListener(v -> Common.selectclass(c, id_kelasTxt));
        idsppTxt.setOnClickListener(v -> Common.selectidspp(c, idsppTxt));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedSiswaItem == null) {
            getMenuInflater().inflate(R.menu.petugas_activity, menu);
        } else {
            getMenuInflater().inflate(R.menu.petugas_crud, menu);
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
                if (receivedSiswaItem != null) {
                    updateData();
                } else {
                    Common.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedSiswaItem != null) {
                    deleteData();
                } else {
                    Common.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Common.openActivity(this, DatasiswaActivity.class);
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
        Object o = Common.receiveSiswa(getIntent(), c);
        if (o != null) {
            receivedSiswaItem = (SiswaItem) o;
            id_siswa = receivedSiswaItem.getIdSiswa();
            nisnTxt.setText(receivedSiswaItem.getNisn());
            nisTxt.setText(receivedSiswaItem.getNis());
            namaTxt.setText(receivedSiswaItem.getNama());
            id_kelasTxt.setText(receivedSiswaItem.getIdKelas());
            alamatTxt.setText(receivedSiswaItem.getAlamat());
            notelpTxt.setText(receivedSiswaItem.getNoTelp());
            idsppTxt.setText(receivedSiswaItem.getIdSpp());

        } else {
            //Utils.show(c,"Received Scientist is Null");
        }
    }
    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_siswa);

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }
}
//end