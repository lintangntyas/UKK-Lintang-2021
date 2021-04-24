package com.example.pembayaranspp.View.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Login.LoginData;
import com.example.pembayaranspp.Model.Login.ResponseLogin;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.example.pembayaranspp.SessionManager.SessionManager;
import com.example.pembayaranspp.View.Admin.AdminActivity;
import com.example.pembayaranspp.View.Petugas.PetugasActivity;
import com.example.pembayaranspp.View.Register.RegisterActivity;
import com.example.pembayaranspp.View.Siswa.SiswaActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmail, edPassword, edRole;
    Button login;
    String email, password, role;
    TextView btnRegister;
    SessionManager sessionManager;
    private final Context c = LoginActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        edEmail = findViewById(R.id.lg_email);
        edPassword = findViewById(R.id.lg_password);

        edRole = findViewById(R.id.lg_role);
        edRole.setOnClickListener(this);

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edEmail.getText().toString();
                password = edPassword.getText().toString();
                role = edRole.getText().toString();
                login(email, password, role);
            }
        });

        btnRegister = findViewById(R.id.tv_register);
        btnRegister.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.lg_role:
                this.showSelectedRoleInEditText();
                break;
        }
    }

    private void showSelectedRoleInEditText() {
        edRole.setOnClickListener(v -> Common.selectrole(c, edRole));
    }

    private void login(String email, String password, String role) {
        ApiClient config = new ApiClient();
        ApiInterface api = config.service;//klik alt + enter pilih configRetrofit

        api.loginResponse(email , password , role).enqueue(new Callback<ResponseLogin>() {

            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {

                    // Ini untuk menyimpan sesi
                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);

                    //Ini untuk pindah
                    Toast.makeText(LoginActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
                    //Ini untuk pindah
                    switch (response.body().getData().getRole()) {
                        case ("Siswa"):
                            Intent intent = new Intent(LoginActivity.this, SiswaActivity.class);
                            LoginActivity.this.startActivity(intent);
                            break;
                        case ("Petugas"):
                            Intent intent2 = new Intent(LoginActivity.this, PetugasActivity.class);
                            LoginActivity.this.startActivity(intent2);
                            break;
                        case ("Administrator"):
                            Intent intent3 = new Intent(LoginActivity.this, AdminActivity.class);
                            LoginActivity.this.startActivity(intent3);
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

//                Log.i("debug sukses",response.message());
//
//                // Ini untuk menyimpan sesi
//                sessionManager = new SessionManager(LoginActivity.this);
//                LoginData loginData = response.body().getData();
//                sessionManager.createLoginSession(loginData);
//
//                //Ini untuk pindah
//                switch (response.body().getData().getRole()) {
//                    case ("Siswa"):
//                        Intent intent = new Intent(LoginActivity.this, SiswaActivity.class);
//                        LoginActivity.this.startActivity(intent);
//                        break;
//                    case ("Petugas"):
//                        Intent intent2 = new Intent(LoginActivity.this, PetugasActivity.class);
//                        LoginActivity.this.startActivity(intent2);
//                        break;
//                    case ("Administrator"):
//                        Intent intent3 = new Intent(LoginActivity.this, AdminActivity.class);
//                        LoginActivity.this.startActivity(intent3);
//                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}