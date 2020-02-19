package com.iita.testing.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TestContract {

    private TestContract() {}

    public static final String CONTENT_AUTHORITY = "com.iita.testing.test";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_LAB_ONE = "lab_one_tests";

    public final static class LabOneTestEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_LAB_ONE);

        public final static String TABLE_NAME = "lab_one_tests";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_LABEL = "product_label";
        public final static String COLUMN_SAMPLE_WEIGHT = "sample_weight";
        public final static String COLUMN_LUTEIN = "lutein";
        public final static String COLUMN_LUTEIN_RESULT = "lutein_result";
        public final static String COLUMN_ZEAXANTHIN = "zeaxanthin";
        public final static String COLUMN_ZEAXANTHIN_RESULT = "zeaxanthin_result";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LAB_ONE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LAB_ONE;
    }
}
