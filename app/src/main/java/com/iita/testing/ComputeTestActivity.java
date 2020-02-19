package com.iita.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iita.testing.data.TestDbHelper;
import com.iita.testing.data.TestContract.LabOneTestEntry;

import java.util.Locale;

public class ComputeTestActivity extends AppCompatActivity {

    private TextView productLabel;
    private Intent intent;

    private EditText sampleWeight;
    private EditText lutein;
    private EditText zeaxanthin;

    private TextView luteinResult;
    private TextView zeaxanthinResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_test);

        productLabel = findViewById(R.id.tv_product_label);
        intent = getIntent();

        sampleWeight = findViewById(R.id.et_sample_weight);
        lutein = findViewById(R.id.et_lutein);
        zeaxanthin = findViewById(R.id.et_zeaxanthin);

        luteinResult = findViewById(R.id.tv_lutein);
        zeaxanthinResult = findViewById(R.id.tv_zeaxanthin);

        productLabel.setText(intent.getStringExtra("productLabel"));
    }

    public void computeResult(View view) {
        String sampleWeightText = sampleWeight.getText().toString();
        String luteinText = lutein.getText().toString();
        String zeaxanthinText = zeaxanthin.getText().toString();

        if (!sampleWeightText.isEmpty()) {
            double luteinValue = computeLutein(sampleWeightText, luteinText);
            double zeaxanthinValue = computeZeaxanthin(sampleWeightText, zeaxanthinText);
            insertTests((int)luteinValue, (int)zeaxanthinValue);
        } else {
            sampleWeight.setError("Sample Weight is Required");
            sampleWeight.requestFocus();
            return;
        }
    }

    public double computeLutein(String sampleWeightText, String luteinText) {
        boolean isNumeric = luteinText.matches("-?\\d+(\\.\\d+)?");
        if (!luteinText.isEmpty()) {
            if (isNumeric) {
                double sampleWeightValue = Double.parseDouble(sampleWeightText);
                double luteinValue = Double.parseDouble(luteinText);

                double luteinResultDouble = (luteinValue * 0.000084331-1.0224)/sampleWeightValue/50 * 1000/1000 * 1.1;
                luteinResult.setText(String.format(Locale.ENGLISH,"%7.6f", luteinResultDouble));

                return luteinValue;
            } else {
                lutein.setError("Only Accepts Numeric Input");
                lutein.requestFocus();
                return 0;
            }
        }
        return 0;
    }

    public double computeZeaxanthin(String sampleWeightText, String zeaxanthinText) {
        boolean isNumeric = zeaxanthinText.matches("-?\\d+(\\.\\d+)?");
        if (!zeaxanthinText.isEmpty()) {
            if (isNumeric) {
                double sampleWeightValue = Double.parseDouble(sampleWeightText);
                double zeaxanthinValue = Double.parseDouble(zeaxanthinText);

                double zeaxanthinResultDouble = (zeaxanthinValue * 0.000084745-0.50441)/sampleWeightValue/50 * 1000/1000 * 1;

                zeaxanthinResult.setText(String.format(Locale.ENGLISH,"%7.6f", zeaxanthinResultDouble));

                return zeaxanthinValue;
            } else {
                zeaxanthin.setError("Only Accepts Numeric Input");
                zeaxanthin.requestFocus();
                return 0;
            }
        }
        return 0;
    }

    private void insertTests(int luteinValue, int zeaxanthinValue) {
        String productLabelText = productLabel.getText().toString();
        String sampleWeightText = sampleWeight.getText().toString().trim();
        String luteinResultText = luteinResult.getText().toString().trim();
        String zeaxanthinResultText = zeaxanthinResult.getText().toString().trim();

        boolean isSampleWeightNumeric = sampleWeightText.matches("-?\\d+(\\.\\d+)?");
        boolean isLuteinResultNumeric = luteinResultText.matches("-?\\d+(\\.\\d+)?");
        boolean isZeaxanthinResultNumeric = zeaxanthinResultText.matches("-?\\d+(\\.\\d+)?");

        if (isSampleWeightNumeric) {
            double sampleWeightValue = Double.parseDouble(sampleWeightText);
            double luteinResultValue = 0;
            double zeaxanthinResultValue = 0;

            if (!luteinResultText.isEmpty() && isLuteinResultNumeric) {
                luteinResultValue = Double.parseDouble(luteinResultText);
            } else {
                return;
            }

            if (!zeaxanthinResultText.isEmpty() && isZeaxanthinResultNumeric) {
                zeaxanthinResultValue = Double.parseDouble(zeaxanthinResultText);
            } else {
                return;
            }

            ContentValues values = new ContentValues();

            values.put(LabOneTestEntry.COLUMN_PRODUCT_LABEL, productLabelText);
            values.put(LabOneTestEntry.COLUMN_SAMPLE_WEIGHT, sampleWeightValue);
            values.put(LabOneTestEntry.COLUMN_LUTEIN, luteinValue);
            values.put(LabOneTestEntry.COLUMN_LUTEIN_RESULT, luteinResultValue);
            values.put(LabOneTestEntry.COLUMN_ZEAXANTHIN, zeaxanthinValue);
            values.put(LabOneTestEntry.COLUMN_ZEAXANTHIN_RESULT, zeaxanthinResultValue);

            Uri newUri = getContentResolver().insert(LabOneTestEntry.CONTENT_URI, values);
            //long newRowId = db.insert(LabOneTestEntry.TABLE_NAME, null, values);

            if (newUri == null) {
                Toast.makeText(this, "Error with saving test", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Test saved successfully", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(ComputeTestActivity.this, TestResultActivity.class));
            //finish();
        } else {
            sampleWeight.setError("Only Accepts Numeric Value");
            sampleWeight.requestFocus();
        }
    }

    public void goBack(View view) {
        finish();
    }
}
