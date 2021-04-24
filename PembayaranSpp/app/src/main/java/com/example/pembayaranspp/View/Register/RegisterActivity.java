package com.example.pembayaranspp.View.Register;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.pembayaranspp.Model.Register.RegisterData;
import com.example.pembayaranspp.Model.Register.ResponseRegister;
import com.example.pembayaranspp.Network.ApiClient;
import com.example.pembayaranspp.Network.ApiInterface;
import com.example.pembayaranspp.R;
import com.example.pembayaranspp.SessionManager.SessionManager;
import com.example.pembayaranspp.View.Admin.AdminActivity;
import com.example.pembayaranspp.View.Login.LoginActivity;
import com.example.pembayaranspp.View.Petugas.PetugasActivity;
import com.example.pembayaranspp.View.Siswa.SiswaActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edEmail, edUsername, edPassword, edRole;
    Button Register;
    String email, username, password, role;
    TextView Login;
    private Context c = RegisterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        edEmail = (EditText) findViewById(R.id.rg_email);
        edUsername = (EditText) findViewById(R.id.rg_username);
        edPassword = (EditText) findViewById(R.id.rg_password);

        edRole = (EditText) findViewById(R.id.rg_role);
        edRole.setOnClickListener(this);

        Register = (Button) findViewById(R.id.btn_regis);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edEmail.getText().toString();
                username = edUsername.getText().toString();
                password = edPassword.getText().toString();
                role = edRole.getText().toString();
                registration(email, username, password, role);
            }
        });

        Login = (TextView) findViewById(R.id.tv_login);
        Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.rg_role:
                this.showSelectedRoleInEditText();
                break;
        }
    }

    private void showSelectedRoleInEditText() {
        edRole.setOnClickListener(v -> Common.selectrole(c, edRole));
    }

    private void registration(String email, String username, String password, String role) {

        ApiClient config = new ApiClient();
        ApiInterface api = config.service;

        Call<ResponseRegister> call = api.registerResponse(email, username, password, role);
        call.enqueue(new Callback<ResponseRegister>() {

            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}