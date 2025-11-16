package com.example.zakatcalcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Deklarasi komponen UI
    TextInputEditText etWeight, etPrice;
    Spinner spType;
    Button btnCalculate, btnReset;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        etWeight = findViewById(R.id.etWeight);
        etPrice = findViewById(R.id.etPrice);
        spType = findViewById(R.id.spType);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvResult = findViewById(R.id.tvResult);

        // Listener untuk butang
        btnCalculate.setOnClickListener(v -> calculateZakat());
        btnReset.setOnClickListener(v -> resetFields());
    }

    /**
     * Fungsi untuk mengira zakat emas.
     * Logik ini telah dikemas kini untuk:
     * 1. Mengira 'Zakat Payable' (nilai sebenar, boleh jadi negatif).
     * 2. Menentukan nilai untuk paparan (tunjuk 0 jika nilai sebenar negatif).
     * 3. Hanya mengira 'Total Zakat' jika nilai sebenar adalah positif.
     */
    private void calculateZakat() {
        String weightStr = etWeight.getText().toString();
        String priceStr = etPrice.getText().toString();

        if (weightStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double price = Double.parseDouble(priceStr);
            String type = spType.getSelectedItem().toString();

            double uruf = type.toLowerCase().contains("keep") ? 85.0 : 200.0;

            // 1. Kira nilai emas keseluruhan
            double totalValue = weight * price;

            // 2. Kira nilai boleh zakat sebenar (mungkin negatif)
            double zakatPayableValueActual = (weight - uruf) * price;

            // 3. Tentukan nilai yang akan dipaparkan. Jika negatif, paparkan 0.
            double zakatPayableForDisplay = zakatPayableValueActual > 0 ? zakatPayableValueActual : 0;

            // 4. Kira jumlah zakat berdasarkan nilai sebenar
            double totalZakat = 0; // Set kepada 0 secara lalai
            if (zakatPayableValueActual > 0) {
                totalZakat = zakatPayableValueActual * 0.025;
            }

            // Paparkan hasil menggunakan nilai untuk paparan
            String resultText = "Total Gold Value: RM " + String.format("%.2f", totalValue) + "\n" +
                    "Zakat Payable (Uruf > " + (int) uruf + "g): RM " + String.format("%.2f", zakatPayableForDisplay) + "\n" +
                    "Total Zakat (2.5%): RM " + String.format("%.2f", totalZakat);

            tvResult.setText(resultText);
            tvResult.setVisibility(View.VISIBLE);

            // Beri maklum balas kepada pengguna
            Toast.makeText(this, "Result has been calculated", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Fungsi untuk mengosongkan semua medan input dan hasil.
     */
    private void resetFields() {
        etWeight.setText("");
        etPrice.setText("");
        spType.setSelection(0);
        tvResult.setText("");
        tvResult.setVisibility(View.GONE);
        etWeight.requestFocus();
        Toast.makeText(this, "Fields have been reset", Toast.LENGTH_SHORT).show();
    }

    /**
     * Fungsi untuk mencipta menu tiga titik pada Action Bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Fungsi untuk mengendalikan tindakan apabila item menu dipilih.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "Check out this Zakat Calculator App! Made by Aiman Hakim.";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share using"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
