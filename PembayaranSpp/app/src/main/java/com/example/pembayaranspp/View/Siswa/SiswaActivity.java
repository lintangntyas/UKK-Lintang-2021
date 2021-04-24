package com.example.pembayaranspp.View.Siswa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pembayaranspp.R;
import com.example.pembayaranspp.SessionManager.SessionManager;
import com.example.pembayaranspp.View.DataPembayaran.DatapembayaranActivity;
import com.example.pembayaranspp.View.Laporan.LaporanActivity;
import com.example.pembayaranspp.View.Login.LoginActivity;
import com.example.pembayaranspp.View.Petugas.PetugasActivity;

public class SiswaActivity extends AppCompatActivity {

    CardView cv_history;
    TextView tvEmail, tvUsername, lihat_semua;
    String username, email;
    ImageView browser_btn;
    LinearLayout browser_siswa;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa);

        sessionManager = new SessionManager(SiswaActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        lihat_semua = findViewById(R.id.lihatsemua);
        cv_history = findViewById(R.id.siswa_histori);

        //username ambil dari string, USERNAME ambil dari session string
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email= sessionManager.getUserDetail().get(SessionManager.EMAIL);

        //etUsername di ambil dari textview, username diambil dari string diatas
        tvEmail = findViewById(R.id.etMainEmail);
        tvUsername = findViewById(R.id.etMainUsername);

        tvEmail.setText(email);
        tvUsername.setText(username);

        browser_btn = findViewById(R.id.browser_button);
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

        browser_siswa = findViewById(R.id.browser_linear);
        browser_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.smkti.net/"));
                startActivity(intent);
            }
        });

        cv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dataIntent = new Intent(SiswaActivity.this, LaporanActivity.class);
                startActivity(dataIntent);
            }
        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(SiswaActivity.this, LoginActivity.class);
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