package com.iita.testing.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.iita.testing.data.TestContract.LabOneTestEntry;

import com.iita.testing.R;

public class LabOneTestCursorAdapter extends CursorAdapter {
    public LabOneTestCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView productLabel = view.findViewById(R.id.tv_product_label);
        TextView sampleWeight = view.findViewById(R.id.tv_sample_weight);
        TextView lutein = view.findViewById(R.id.tv_lutein);
        TextView luteinResult = view.findViewById(R.id.tv_lutein_result);
        TextView zeaxanthin = view.findViewById(R.id.tv_zeaxanthin);
        TextView zeaxanthinResult = view.findViewById(R.id.tv_zeaxanthin_result);

        String productLabelV = cursor.getString(cursor.getColumnIndexOrThrow(LabOneTestEntry.COLUMN_PRODUCT_LABEL));
        double sampleWeightV = cursor.getDouble(cursor.getColumnIndexOrThrow(LabOneTestEntry.COLUMN_SAMPLE_WEIGHT));
        double luteinV = cursor.getDouble(cursor.getColumnIndexOrThrow(LabOneTestEntry.COLUMN_LUTEIN));
        double luteinResultV = cursor.getDouble(cursor.getColumnIndexOrThrow(LabOneTestEntry.COLUMN_LUTEIN_RESULT));
        double zeaxanthinV = cursor.getDouble(cursor.getColumnIndexOrThrow(LabOneTestEntry.COLUMN_ZEAXANTHIN));
        double zeaxanthinResultV = cursor.getDouble(cursor.getColumnIndexOrThrow(LabOneTestEntry.COLUMN_ZEAXANTHIN_RESULT));

        productLabel.setText(productLabelV);
        sampleWeight.setText(Double.toString(sampleWeightV));
        lutein.setText(Double.toString(luteinV));
        luteinResult.setText(Double.toString(luteinResultV));
        zeaxanthin.setText(Double.toString(zeaxanthinV));
        zeaxanthinResult.setText(Double.toString(zeaxanthinResultV));
    }
}
