package com.iita.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void labOne(View view) {
        startActivity(new Intent(MainActivity.this, LabOneActivity.class));
    }

    public void labTwo(View view) {
        Toast.makeText(this, "Lab Two Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    public void labThree(View view) {
        Toast.makeText(this, "Lab Three Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    public void labFour(View view) {
        Toast.makeText(this, "Lab Four Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    public void labFive(View view) {
        Toast.makeText(this, "Lab Five Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    public void labSix(View view) {
        Toast.makeText(this, "Lab Six Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
