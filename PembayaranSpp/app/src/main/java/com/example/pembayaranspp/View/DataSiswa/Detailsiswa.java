package com.example.pembayaranspp.View.DataSiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Siswa.SiswaItem;
import com.example.pembayaranspp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Detailsiswa extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView nisnTV, nisTV, namaTV, idkelasTV, alamatTV, notelpTV, idsppTV;
    private FloatingActionButton editFAB;
    private SiswaItem receivedSiswaitem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets(){
        nisnTV = findViewById(R.id.nisnTV);
        nisTV= findViewById(R.id.nisTV);
        namaTV= findViewById(R.id.namaTV);
        idkelasTV = findViewById(R.id.idkelasTV);
        alamatTV = findViewById(R.id.alamatTV);
        notelpTV= findViewById(R.id.notelpTV);
        idsppTV= findViewById(R.id.idsppTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    private void receiveAndShowData(){
        receivedSiswaitem = Common.receiveSiswa(getIntent(), Detailsiswa.this);

        if(receivedSiswaitem != null){
            nisnTV.setText(receivedSiswaitem.getNisn());
            nisTV.setText(receivedSiswaitem.getNis());
            namaTV.setText(receivedSiswaitem.getNama());
            idkelasTV.setText(receivedSiswaitem.getIdKelas());
            alamatTV.setText(receivedSiswaitem.getAlamat());
            notelpTV.setText(receivedSiswaitem.getNoTelp());
            idsppTV.setText(receivedSiswaitem.getIdSpp());

            mCollapsingToolbarLayout.setTitle(receivedSiswaitem.getNama());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(android.R.color.holo_green_dark));
        }
    }


    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Common.sendSiswaToActivity(this, receivedSiswaitem, CrudsiswaActivity.class);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_siswa);

        initializeWidgets();
        receiveAndShowData();
    }
}