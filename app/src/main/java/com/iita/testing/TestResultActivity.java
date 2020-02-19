package com.iita.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iita.testing.data.LabOneTestCursorAdapter;
import com.iita.testing.data.TestContract;
import com.iita.testing.data.TestDbHelper;
import com.iita.testing.data.TestContract.LabOneTestEntry;
import com.iita.testing.data.TestProvider;
import com.iita.testing.external.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestResultActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        displayInfo();
    }

    private void displayInfo() {

        String[] projection = {
                LabOneTestEntry._ID,
                LabOneTestEntry.COLUMN_PRODUCT_LABEL,
                LabOneTestEntry.COLUMN_SAMPLE_WEIGHT,
                LabOneTestEntry.COLUMN_LUTEIN,
                LabOneTestEntry.COLUMN_LUTEIN_RESULT,
                LabOneTestEntry.COLUMN_ZEAXANTHIN,
                LabOneTestEntry.COLUMN_ZEAXANTHIN_RESULT
        };

        Cursor cursor = getContentResolver().query(LabOneTestEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        ListView testListView = findViewById(R.id.list);

        LabOneTestCursorAdapter adapter = new LabOneTestCursorAdapter(this, cursor);

        testListView.setDivider(null);
        testListView.setAdapter(adapter);
    }

    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(TestResultActivity.this);
        TestDbHelper dbHelper;
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
            dbHelper = new TestDbHelper(TestResultActivity.this);
        }

        protected Boolean doInBackground(final String... args) {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "/docs/");
            if (!exportDir.exists())
            {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "csvname.csv");
            try
            {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TestResultActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result == 0) {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    Cursor curCSV = dbHelper.raw();
                    csvWrite.writeNext(curCSV.getColumnNames());
                    while(curCSV.moveToNext()) {
                        String arrStr[]=null;
                        String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
                        for(int i=0;i<curCSV.getColumnNames().length;i++)
                        {
                            mySecondStringArray[i] =curCSV.getString(i);
                        }
                        csvWrite.writeNext(mySecondStringArray);
                    }
                    csvWrite.close();
                    curCSV.close();
                }
                return true;
            } catch (IOException e) {
                Log.e("error", "doInBackground: " + e.getMessage());
                return false;
            }
        }


        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) { this.dialog.dismiss(); }
            if (success) {
                Toast.makeText(TestResultActivity.this, "Saved to phone storage!", Toast.LENGTH_SHORT).show();
                ShareFile();
            } else {
                Toast.makeText(TestResultActivity.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //permission is granted,do your operation
            }else{
                // permission not granted
                //Display your message to let the user know that he requires permission to access the app
                Toast.makeText(this,"You denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ShareFile() {
        String fileName = "csvname.csv";
        File exportDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/docs/");
        File sharingCSVFile = new File(exportDir, fileName);
        Uri apkUrl = FileProvider.getUriForFile(
                TestResultActivity.this,
                "com.iita.testing.fileprovider", sharingCSVFile);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setDataAndType(apkUrl, "application/csv");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_STREAM, apkUrl);
        startActivity(Intent.createChooser(shareIntent, "Share CSV"));
    }

    public void export(View view) {
        new ExportDatabaseCSVTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void goBack(View view) {
        startActivity(new Intent(TestResultActivity.this, LabOneActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestResultActivity.this, LabOneActivity.class));
    }
}
