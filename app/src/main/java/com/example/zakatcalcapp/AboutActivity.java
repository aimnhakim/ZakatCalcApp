package com.example.zakatcalcapp;

import android.os.Bundle;
import android.view.MenuItem; // <-- Import ini
import androidx.appcompat.app.ActionBar; // <-- Import ini
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // -------- KOD UNTUK BUTANG BACK (MULA) --------

        // 1. Dapatkan ActionBar
        ActionBar actionBar = getSupportActionBar();

        // 2. Semak jika ActionBar wujud dan paparkan butang "Up" (anak panah ke kiri)
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // -------- KOD UNTUK BUTANG BACK (TAMAT) --------
    }

    // -------- TAMBAH KAEDAH INI DI LUAR ONCREATE --------
    // Kaedah ini akan dipanggil apabila butang "Up" ditekan.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Semak jika butang yang ditekan ialah butang "home" (butang "Up")
        if (item.getItemId() == android.R.id.home) {
            // Kembali ke aktiviti sebelumnya
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
