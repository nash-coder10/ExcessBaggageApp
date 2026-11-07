package com.example.excessbaggageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtWeight, edtRate;
    private RadioGroup rgTicketClass;
    private RadioButton rbEconomy;
    private Button btnCalculate, btnAbout;
    private TextView txtOutputWeight, txtOutputCharge, txtOutputTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bind UI components
        edtWeight = findViewById(R.id.edtWeight);
        edtRate = findViewById(R.id.edtRate);
        rgTicketClass = findViewById(R.id.rgTicketClass);
        rbEconomy = findViewById(R.id.rbEconomy); // <--- INI YANG TERTINGGAL TADI
        btnCalculate = findViewById(R.id.btnCalculate);
        btnAbout = findViewById(R.id.btnAbout);

        txtOutputWeight = findViewById(R.id.txtOutputWeight);
        txtOutputCharge = findViewById(R.id.txtOutputCharge);
        txtOutputTotal = findViewById(R.id.txtOutputTotal);

        // Calculation Button Click
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateExcessFee();
            }
        });

        // About Button Click - Opens AboutActivity
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calculateExcessFee() {
        String weightStr = edtWeight.getText().toString().trim();
        String rateStr = edtRate.getText().toString().trim();

        if (weightStr.isEmpty() || rateStr.isEmpty()) {
            Toast.makeText(this, "Error: Please fill in all input fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double baggageWeight = Double.parseDouble(weightStr);
            double ratePerKg = Double.parseDouble(rateStr);

            // Allowance: Economy = 20kg, Business = 40kg
            double xKg = rbEconomy.isChecked() ? 20.0 : 40.0;

            // Excess weight calculation (never negative)
            double excessWeight = Math.max(0.0, baggageWeight - xKg);

            // Excess baggage charge
            double excessCharge = excessWeight * ratePerKg;

            // Total amount payable (including 8% service tax)
            double totalPayable = excessCharge + (excessCharge * 0.08);

            // Update UI outputs
            txtOutputWeight.setText(String.format("Excess Baggage Weight: %.2f kg", excessWeight));
            txtOutputCharge.setText(String.format("Excess Baggage Charge: RM %.2f", excessCharge));
            txtOutputTotal.setText(String.format("Total Amount Payable: RM %.2f", totalPayable));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error: Please enter valid numerical values.", Toast.LENGTH_SHORT).show();
        }
    }

    // Add Options Menu for Share Feature
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my Excess Baggage Fee mobile app project: https://github.com/nash-coder10/ExcessBaggageApp");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}