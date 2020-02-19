package com.iita.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LabOneActivity extends AppCompatActivity {

    private EditText productLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_one);

        productLabel = findViewById(R.id.et_product_label);
    }

    public void computeTestResult(View view) {
        String productLabelText = productLabel.getText().toString();
        Intent intent = new Intent(LabOneActivity.this, ComputeTestActivity.class);
        if (!productLabelText.isEmpty()) {
            intent.putExtra("productLabel", productLabelText);
            startActivity(intent);
        }
    }

    public void viewTestResult(View view) {
        startActivity(new Intent(LabOneActivity.this, TestResultActivity.class));
    }

    public void goBack(View view) {
        startActivity(new Intent(LabOneActivity.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LabOneActivity.this, MainActivity.class));
    }
}
