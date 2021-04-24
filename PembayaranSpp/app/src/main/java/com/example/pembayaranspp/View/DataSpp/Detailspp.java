package com.example.pembayaranspp.View.DataSpp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Spp.SppItem;
import com.example.pembayaranspp.R;
import com.example.pembayaranspp.View.DataKelas.CrudkelasActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Detailspp extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView tahunTV, nominalTV;
    private FloatingActionButton editFAB;
    private SppItem receivedSppitem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets(){
        tahunTV= findViewById(R.id.tahunTV);
        nominalTV= findViewById(R.id.nominalTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    private void receiveAndShowData(){
        receivedSppitem = Common.receiveSpp(getIntent(),Detailspp.this);

        if(receivedSppitem != null){
            tahunTV.setText(receivedSppitem.getTahun());
            nominalTV.setText(receivedSppitem.getNominal());

            mCollapsingToolbarLayout.setTitle(receivedSppitem.getIdSpp());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(android.R.color.holo_green_dark));
        }
    }


    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Common.sendSppToActivity(this, receivedSppitem, CrudsppActivity.class);
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
        setContentView(R.layout.detail_spp);

        initializeWidgets();
        receiveAndShowData();
    }
}