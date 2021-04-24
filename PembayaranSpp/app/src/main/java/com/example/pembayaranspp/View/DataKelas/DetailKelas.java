package com.example.pembayaranspp.View.DataKelas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Kelas.KelasItem;
import com.example.pembayaranspp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailKelas extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView nama_kelasTV, kompetensi_keahlianTV;
    private FloatingActionButton editFAB;
    private KelasItem receivedKelasItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        nama_kelasTV= findViewById(R.id.namakelasTV);
        kompetensi_keahlianTV= findViewById(R.id.kompetensiTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedKelasItem = Common.receiveKelas(getIntent(),DetailKelas.this);

        if(receivedKelasItem != null){
            nama_kelasTV.setText(receivedKelasItem.getNamaKelas());
            kompetensi_keahlianTV.setText(receivedKelasItem.getKompetensiKeahlian());

            mCollapsingToolbarLayout.setTitle(receivedKelasItem.getNamaKelas());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(android.R.color.holo_green_dark));
        }
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Common.sendKelasToActivity(this, receivedKelasItem, CrudkelasActivity.class);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    /**
     * Our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kelas);

        initializeWidgets();
        receiveAndShowData();
    }

}
//end