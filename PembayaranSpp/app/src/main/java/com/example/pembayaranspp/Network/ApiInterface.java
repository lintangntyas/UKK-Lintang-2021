package com.example.pembayaranspp.Network;

import com.example.pembayaranspp.Model.Kelas.ResponseKelas;
import com.example.pembayaranspp.Model.Login.ResponseLogin;
import com.example.pembayaranspp.Model.Pembayaran.ResponsePembayaran;
import com.example.pembayaranspp.Model.Petugas.ResponsePetugas;
import com.example.pembayaranspp.Model.Register.ResponseRegister;
import com.example.pembayaranspp.Model.Siswa.ResponseSiswa;
import com.example.pembayaranspp.Model.Spp.ResponseSpp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginResponse(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseRegister> registerResponse(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role
    );

    @GET("petugas.php")
    Call<ResponsePetugas> getAllDataPetugas();

    @FormUrlEncoded
    @POST("petugas.php")
    Call<ResponsePetugas> insertData(
            @Field("action") String action,
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_petugas") String nama_petugas,
            @Field("level") String level
    );

    @FormUrlEncoded
    @POST("petugas.php")
    Call<ResponsePetugas> updateData(
            @Field("action") String action,
            @Field("id_petugas") String id_petugas,
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_petugas") String nama_petugas,
            @Field("level") String level
    );

    @FormUrlEncoded
    @POST("petugas.php")
    Call<ResponsePetugas> remove(
            @Field("action") String action,
            @Field("id_petugas") String id_petugas
    );

    @FormUrlEncoded
    @POST("petugas.php")
    Call<ResponsePetugas> searchpetugas(
            @Field("action") String action,
            @Field("query") String query,
            @Field("start") String start,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("pembayaran.php")
    Call<ResponsePembayaran> insertDataPembayaran(
            @Field("action") String action,
            @Field("id_petugas") String id_petugas,
            @Field("nisn") String nisn,
            @Field("tgl_bayar") String tgl_bayar,
            @Field("bulan_dibayar") String bulan_dibayar,
            @Field("tahun_dibayar") String tahun_dibayar,
            @Field("id_spp") String id_spp,
            @Field("jumlah_bayar") String jumlah_bayar
    );

    @FormUrlEncoded
    @POST("pembayaran.php")
    Call<ResponsePembayaran> updateDataPembayaran(
            @Field("action") String action,
            @Field("id_pembayaran") String id_pembayaran,
            @Field("id_petugas") String id_petugas,
            @Field("nisn") String nisn,
            @Field("tgl_bayar") String tgl_bayar,
            @Field("bulan_dibayar") String bulan_dibayar,
            @Field("tahun_dibayar") String tahun_dibayar,
            @Field("id_spp") String id_spp,
            @Field("jumlah_bayar") String jumlah_bayar

    );

    @FormUrlEncoded
    @POST("pembayaran.php")
    Call<ResponsePembayaran> searchpembayaran(
            @Field("action") String action,
            @Field("query") String query,
            @Field("start") String start,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("pembayaran.php")
    Call<ResponsePembayaran> removepembayaran(
            @Field("action") String action,
            @Field("id_pembayaran") String id_pembayaran
    );

    @GET("pembayaran.php")
    Call<ResponsePembayaran> getAllDataPembayaran();

    @GET("kelas.php")
    Call<ResponseKelas> getAllDataKelas();

    @FormUrlEncoded
    @POST("kelas.php")
    Call<ResponseKelas> insertDataKelas(
            @Field("action") String action,
            @Field("nama_kelas") String nama_kelas,
            @Field("kompetensi_keahlian") String kompetensi_keahlian
    );

    @FormUrlEncoded
    @POST("kelas.php")
    Call<ResponseKelas> updateDataKelas(
            @Field("action") String action,
            @Field("id_kelas") String id_kelas,
            @Field("nama_kelas") String nama_kelas,
            @Field("kompetensi_keahlian") String kompetensi_keahlian

    );

    @FormUrlEncoded
    @POST("kelas.php")
    Call<ResponseKelas> searchkelas(
            @Field("action") String action,
            @Field("query") String query,
            @Field("start") String start,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("kelas.php")
    Call<ResponseKelas> removekelas(
            @Field("action") String action,
            @Field("id_kelas") String id_kelas
    );

    @FormUrlEncoded
    @POST("spp.php")
    Call<ResponseSpp> insertDataSpp(
            @Field("action") String action,
            @Field("tahun") String tahun,
            @Field("nominal") String nominal
    );

    @FormUrlEncoded
    @POST("spp.php")
    Call<ResponseSpp> updateDataSpp(
            @Field("action") String action,
            @Field("id_spp") String id_spp,
            @Field("tahun") String tahun,
            @Field("nominal") String nominal

    );

    @FormUrlEncoded
    @POST("spp.php")
    Call<ResponseSpp> searchSpp(
            @Field("action") String action,
            @Field("query") String query,
            @Field("start") String start,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("spp.php")
    Call<ResponseSpp> removeSpp(
            @Field("action") String action,
            @Field("id_spp") String id_spp
    );

    @GET("spp.php")
    Call<ResponseSpp> getAllDataSpp();

    @FormUrlEncoded
    @POST("siswa.php")
    Call<ResponseSiswa> insertDataSiswa(
            @Field("action") String action,
            @Field("nisn") String nisn,
            @Field("nis") String nis,
            @Field("nama") String nama,
            @Field("id_kelas") String id_kelas,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("id_spp") String id_spp
    );

    @FormUrlEncoded
    @POST("siswa.php")
    Call<ResponseSiswa> updateDataSiswa(
            @Field("action") String action,
            @Field("id_siswa") String id_siswa,
            @Field("nisn") String nisn,
            @Field("nis") String nis,
            @Field("nama") String nama,
            @Field("id_kelas") String id_kelas,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("id_spp") String id_spp

    );

    @FormUrlEncoded
    @POST("siswa.php")
    Call<ResponseSiswa> searchSiswa(
            @Field("action") String action,
            @Field("query") String query,
            @Field("start") String start,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("siswa.php")
    Call<ResponseSiswa> removeSiswa(
            @Field("action") String action,
            @Field("id_siswa") String id_siswa
    );

    @GET("siswa.php")
    Call<ResponseSiswa> getAllDataSiswa();



}
