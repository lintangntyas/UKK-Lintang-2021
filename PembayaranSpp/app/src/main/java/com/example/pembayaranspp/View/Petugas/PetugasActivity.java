package com.example.pembayaranspp.View.Petugas;

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
import com.example.pembayaranspp.View.DataPembayaran.DatapembayaranActivity;
import com.example.pembayaranspp.View.Login.LoginActivity;

public class PetugasActivity extends AppCompatActivity {

    CardView cv_pembayaran;
    TextView tvEmail, tvUsername, lihat_semua;
    String username, email;
    ImageView browser_btn;
    LinearLayout browser_petugas;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);

        sessionManager = new SessionManager(PetugasActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        lihat_semua = findViewById(R.id.lihatsemua);
        cv_pembayaran = findViewById(R.id.pt_pembayaran);

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

        browser_petugas = findViewById(R.id.browser_linear);
        browser_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.smkti.net/"));
                startActivity(intent);
            }
        });

//        cv_pembayaran.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent dataIntent = new Intent(PetugasActivity.this, DatapembayaranActivity.class);
//                startActivity(dataIntent);
//            }
//        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(PetugasActivity.this, LoginActivity.class);
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