package com.example.pembayaranspp.View.DataPetugas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Petugas.PetugasItem;
import com.example.pembayaranspp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailPetugas extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView usernameTV, passwordTV, nama_petugasTV, levelTV;
    private FloatingActionButton editFAB;
    private PetugasItem receivedPetugasItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    ActionBar actionBar;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        usernameTV= findViewById(R.id.usernameTV);
        passwordTV= findViewById(R.id.passwordTV);
        nama_petugasTV= findViewById(R.id.nama_petugasTV);
        levelTV= findViewById(R.id.levelTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedPetugasItem= Common.receivePetugas(getIntent(),DetailPetugas.this);

        if(receivedPetugasItem != null){
            usernameTV.setText(receivedPetugasItem.getUsername());
            passwordTV.setText(receivedPetugasItem.getPassword());
            nama_petugasTV.setText(receivedPetugasItem.getNamaPetugas());
            levelTV.setText(receivedPetugasItem.getLevel());

            mCollapsingToolbarLayout.setTitle(receivedPetugasItem.getUsername());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(android.R.color.holo_green_dark));
        }
    }
    /**
     * Let's inflate our menu for the detail page
     */

    /**
     * When FAB button is clicked we want to go to the editing page
     */
    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Common.sendPetugasToActivity(this, receivedPetugasItem, CrudPetugasActivity.class);
            finish();
        }
    }
    /**
     * Our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_petugas);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Petugas");

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