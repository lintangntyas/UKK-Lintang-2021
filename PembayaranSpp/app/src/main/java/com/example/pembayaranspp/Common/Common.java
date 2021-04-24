package com.example.pembayaranspp.Common;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pembayaranspp.Model.Kelas.KelasItem;
import com.example.pembayaranspp.Model.Pembayaran.PembayaranItem;
import com.example.pembayaranspp.Model.Petugas.PetugasItem;
import com.example.pembayaranspp.Model.Siswa.SiswaItem;
import com.example.pembayaranspp.Model.Spp.SppItem;
import com.example.pembayaranspp.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void selectrole(Context c, final EditText editText){
        String[] role ={"administrator","petugas","siswa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                role);
        new LovelyChoiceDialog(c)
                .setTopColorRes(android.R.color.holo_green_dark)
                .setTitle("Pilih Level")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the level.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> editText.setText(item))
                .show();
    }

    public static void selectlevel(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] stars ={"Admin","Petugas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                stars);
        new LovelyChoiceDialog(c)
                .setTopColorRes(android.R.color.holo_green_dark)
                .setTitle("Pilih Level")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the level.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void selectpetugas(Context c, final EditText editText){
        String[] petugas ={"1","2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                petugas);
        new LovelyChoiceDialog(c)
                .setTopColorRes(android.R.color.holo_green_dark)
                .setTitle("Pilih Nisn")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("1. Admin | 2. Petugas")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> editText.setText(item))
                .show();
    }

    public static void selectnisn(Context c, final EditText editText){
        String[] nisn ={"123456787","123456788","123456789","1234567810","12345678911","12345678912"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                nisn);
        new LovelyChoiceDialog(c)
                .setTopColorRes(android.R.color.holo_green_dark)
                .setTitle("Pilih Nisn")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the nisn.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> editText.setText(item))
                .show();
    }

    public static void selectidspp(Context c, final EditText editText){
        String[] spp ={"1","2","3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                spp);
        new LovelyChoiceDialog(c)
                .setTopColorRes(android.R.color.holo_green_dark)
                .setTitle("Pilih Nominal Spp")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("1 => 100.000 | 2 => 200.000 | 3 => 300.000" )
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> editText.setText(item))
                .show();
    }

    public static void selectkelas(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] kelas ={"Rekayasa perangkat lunak","Multimedia","Teknik komputer dan jaringan", "Bisnis daring dan pemasaran", "Otomatisasi tata kelola dan perkantoran"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                kelas);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Kelas")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the skill competence.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void selectclass(Context c, final EditText editText){
        String[] spp ={"10","11","12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                spp);
        new LovelyChoiceDialog(c)
                .setTopColorRes(android.R.color.holo_green_dark)
                .setTitle("Pilih Kelas")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the class. " )
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> editText.setText(item))
                .show();
    }

    public static void selecttahun(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] tahun ={"2020","2021","2022","2023","2024","2025"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                tahun);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Tahun")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select year.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void select_bulan(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] bulan ={"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                bulan);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Bulan")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select month.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    public static boolean validatepetugas(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        MaterialAutoCompleteTextView usernameTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView passwordTxt = materialAutoCompleteTextViews[1];
        MaterialAutoCompleteTextView nama_petugasTxt = materialAutoCompleteTextViews[2];

        if(usernameTxt.getText() == null || usernameTxt.getText().toString().isEmpty()){
            usernameTxt.setError("Username is Required Please!");
            return false;
        }
        if(passwordTxt.getText() == null || passwordTxt.getText().toString().isEmpty()){
            passwordTxt.setError("Password is Required Please!");
            return false;
        }
        if(nama_petugasTxt.getText() == null || nama_petugasTxt.getText().toString().isEmpty()){
            nama_petugasTxt.setError("Nama petugas is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatepembayaran(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){

        MaterialAutoCompleteTextView nominalTxt = materialAutoCompleteTextViews[0];

        if(nominalTxt.getText() == null || nominalTxt.getText().toString().isEmpty()){
            nominalTxt.setError("Nominal is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatekelas(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){

        MaterialAutoCompleteTextView nama_kelasTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView kompetensi_keahlianTxt = materialAutoCompleteTextViews[1];

        if(nama_kelasTxt.getText() == null || nama_kelasTxt.getText().toString().isEmpty()){
            nama_kelasTxt.setError("Kelas is Required Please!");
            return false;
        }
        if(kompetensi_keahlianTxt.getText() == null || kompetensi_keahlianTxt.getText().toString().isEmpty()){
            kompetensi_keahlianTxt.setError("Kompetensi Keahlian is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatespp(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        MaterialAutoCompleteTextView tahunTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView nominalTxt = materialAutoCompleteTextViews[1];

        if(tahunTxt.getText() == null || tahunTxt.getText().toString().isEmpty()){
            tahunTxt.setError("Tahun is Required Please!");
            return false;
        }
        if(nominalTxt.getText() == null || nominalTxt.getText().toString().isEmpty()){
            nominalTxt.setError("Nominal is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatesiswa(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){

        MaterialAutoCompleteTextView nisTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView namaTxt = materialAutoCompleteTextViews[1];
        MaterialAutoCompleteTextView alamatTxt = materialAutoCompleteTextViews[2];
        MaterialAutoCompleteTextView notelpTxt = materialAutoCompleteTextViews[3];

        if(nisTxt.getText() == null || nisTxt.getText().toString().isEmpty()){
            nisTxt.setError("Nis is Required Please!");
            return false;
        }
        if(namaTxt.getText() == null || namaTxt.getText().toString().isEmpty()){
            namaTxt.setError("Nama is Required Please!");
            return false;
        }
        if(alamatTxt.getText() == null || alamatTxt.getText().toString().isEmpty()){
            alamatTxt.setError("Alamat is Required Please!");
            return false;
        }
        if(notelpTxt.getText() == null || notelpTxt.getText().toString().isEmpty()){
            notelpTxt.setError("No. Telp is Required Please!");
            return false;
        }

        return true;

    }

    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }

    public static void show(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }

    public  static PetugasItem receivePetugas(Intent intent, Context c){
        try {
            PetugasItem petugasItem= (PetugasItem) intent.getSerializableExtra("Petugas Item");
            return petugasItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-PETUGAS ERROR: "+e.getMessage());
        }
        return null;
    }

    public  static PembayaranItem receivePembayaran(Intent intent, Context c){
        try {
            PembayaranItem pembayaranItem= (PembayaranItem) intent.getSerializableExtra("Pembayaran Item");
            return pembayaranItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-PEMBAYARAN ERROR: "+e.getMessage());
        }
        return null;
    }

    public  static KelasItem receiveKelas(Intent intent,Context c){
        try {
            KelasItem kelasItem= (KelasItem) intent.getSerializableExtra("Kelas Item");
            return kelasItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-KELAS ERROR: "+e.getMessage());
        }
        return null;
    }

    public  static SppItem receiveSpp(Intent intent,Context c){
        try {
            SppItem sppItem= (SppItem) intent.getSerializableExtra("Spp Item");
            return sppItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SPP ERROR: "+e.getMessage());
        }
        return null;
    }

    public  static SiswaItem receiveSiswa(Intent intent,Context c){
        try {
            SiswaItem siswaItem= (SiswaItem) intent.getSerializableExtra("Siswa Item");
            return siswaItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SISWA ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void sendPetugasToActivity(Context c, PetugasItem petugasItem,
                                             Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Petugas Item", petugasItem);
        c.startActivity(i);
    }

    public static void sendPembayaranToActivity(Context c, PembayaranItem pembayaranItem,
                                                Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Pembayaran Item", pembayaranItem);
        c.startActivity(i);
    }

    public static void sendKelasToActivity(Context c, KelasItem kelasItem,
                                           Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Kelas Item", kelasItem);
        c.startActivity(i);
    }

    public static void sendSppToActivity(Context c, SppItem sppItem,
                                         Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Spp Item", sppItem);
        c.startActivity(i);
    }

    public static void sendSiswaToActivity(Context c, SiswaItem siswaItem,
                                         Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Siswa Item", siswaItem);
        c.startActivity(i);
    }

}
