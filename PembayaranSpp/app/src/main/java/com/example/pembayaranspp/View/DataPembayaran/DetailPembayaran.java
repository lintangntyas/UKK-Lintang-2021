package com.example.pembayaranspp.View.DataPembayaran;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Pembayaran.PembayaranItem;
import com.example.pembayaranspp.Model.Petugas.PetugasItem;
import com.example.pembayaranspp.R;
import com.example.pembayaranspp.View.DataPetugas.CrudPetugasActivity;
import com.example.pembayaranspp.View.DataPetugas.DetailPetugas;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailPembayaran extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView idpetugasTV, nisnTV, tglTV, blnTV, thnTV, sppTV, jmlhTV;
    private FloatingActionButton editFAB;
    private PembayaranItem receivedPembayaranItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    ActionBar actionBar;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        idpetugasTV= findViewById(R.id.IdpetugasTV);
        nisnTV= findViewById(R.id.nisnTV);
        tglTV= findViewById(R.id.TanggalTV);
        blnTV= findViewById(R.id.BulanTV);
        thnTV= findViewById(R.id.TahunTV);
        sppTV= findViewById(R.id.IdsppTV);
        jmlhTV= findViewById(R.id.JumlahTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedPembayaranItem= Common.receivePembayaran(getIntent(), DetailPembayaran.this);

        if(receivedPembayaranItem != null){
            idpetugasTV.setText(receivedPembayaranItem.getIdPetugas());
            nisnTV.setText(receivedPembayaranItem.getNisn());
            tglTV.setText(receivedPembayaranItem.getTglBayar());
            blnTV.setText(receivedPembayaranItem.getBulanDibayar());
            thnTV.setText(receivedPembayaranItem.getTahunDibayar());
            sppTV.setText(receivedPembayaranItem.getIdSpp());
            jmlhTV.setText(receivedPembayaranItem.getJumlahBayar());

            mCollapsingToolbarLayout.setTitle(receivedPembayaranItem.getNisn());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(android.R.color.holo_green_dark));
        }
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Common.sendPembayaranToActivity(this, receivedPembayaranItem, CrudpembayaranActivity.class);
            finish();
        }
    }
    /**
     * Our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pembayaran);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Pembayaran");

        initializeWidgets();
        receiveAndShowData();
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