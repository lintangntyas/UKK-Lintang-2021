package com.example.pembayaranspp.View.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pembayaranspp.R;
import com.example.pembayaranspp.SessionManager.SessionManager;
import com.example.pembayaranspp.View.DataKelas.AdapterKelas;
import com.example.pembayaranspp.View.DataKelas.DatakelasActivity;
import com.example.pembayaranspp.View.DataPembayaran.DatapembayaranActivity;
import com.example.pembayaranspp.View.DataPetugas.DataPetugasActivity;
import com.example.pembayaranspp.View.DataSiswa.DatasiswaActivity;
import com.example.pembayaranspp.View.DataSpp.DatasppActivity;
import com.example.pembayaranspp.View.Login.LoginActivity;

public class AdminActivity extends AppCompatActivity {

    CardView cv_petugas, cv_kelas, cv_spp, cv_pembayaran, cv_siswa, cv_laporan;
    TextView tvEmail, tvUsername, lihat_semua;
    String username, email;
    ImageView browser_btn;
    LinearLayout browser_linear;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sessionManager = new SessionManager(AdminActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        cv_petugas = findViewById(R.id.adm_petugas);
        cv_kelas = findViewById(R.id.adm_kelas);
        cv_spp = findViewById(R.id.adm_spp);
        cv_pembayaran = findViewById(R.id.adm_pembayaran);
        cv_siswa = findViewById(R.id.adm_siswa);
        cv_laporan = findViewById(R.id.adm_laporan);

        lihat_semua = findViewById(R.id.lihatsemua);

        browser_btn = findViewById(R.id.browser_button);
        browser_linear = findViewById(R.id.browser_linear);

        //username ambil dari string, USERNAME ambil dari session string
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email= sessionManager.getUserDetail().get(SessionManager.EMAIL);

        //etUsername di ambil dari textview, username diambil dari string diatas
        tvEmail = findViewById(R.id.etMainEmail);
        tvUsername = findViewById(R.id.etMainUsername);

        tvEmail.setText(email);
        tvUsername.setText(username);

        browser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.smkti.net/"));
                startActivity(intent);
            }
        });

        browser_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.smkti.net/"));
                startActivity(intent);
            }
        });

        cv_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datapetugasIntent = new Intent(AdminActivity.this, DataPetugasActivity.class);
                startActivity(datapetugasIntent);
            }
        });

        cv_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datapembayaranIntent = new Intent(AdminActivity.this, DatapembayaranActivity.class);
                startActivity(datapembayaranIntent);
            }
        });

        cv_spp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datasppIntent = new Intent(AdminActivity.this, DatasppActivity.class);
                startActivity(datasppIntent);
            }
        });

        cv_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datakelasIntent = new Intent(AdminActivity.this, DatakelasActivity.class);
                startActivity(datakelasIntent);
            }
        });

        cv_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datasiswaIntent = new Intent(AdminActivity.this, DatasiswaActivity.class);
                startActivity(datasiswaIntent);
            }
        });

        cv_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent datalaporanIntent = new Intent(admin_item.this, ListpetugasActivity.class);
//                startActivity(datalaporanIntent);
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                sessionManager.logoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

}