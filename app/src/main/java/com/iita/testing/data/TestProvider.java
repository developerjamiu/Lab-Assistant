package com.iita.testing.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.iita.testing.data.TestContract.LabOneTestEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestProvider extends ContentProvider {

    private static final String TAG = "TestProvider";
    private TestDbHelper dbHelper;

    private static final int TESTS = 100;
    private static final int TEST_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TestContract.CONTENT_AUTHORITY, TestContract.PATH_LAB_ONE, TESTS);
        sUriMatcher.addURI(TestContract.CONTENT_AUTHORITY, TestContract.PATH_LAB_ONE + "/#", TEST_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new TestDbHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                cursor = database.query(LabOneTestEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TEST_ID:
                selection = LabOneTestEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(LabOneTestEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                return LabOneTestEntry.CONTENT_LIST_TYPE;
            case TEST_ID:
                return LabOneTestEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                return insertTest(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertTest(Uri uri, ContentValues values) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = database.insert(LabOneTestEntry.TABLE_NAME, null, values);
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri );
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                // Delete all rows that match the selection and selection args
                return database.delete(LabOneTestEntry.TABLE_NAME, selection, selectionArgs);
            case TEST_ID:
                // Delete a single row given by the ID in the URI
                selection = LabOneTestEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(LabOneTestEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                return updateTest(uri, values, selection, selectionArgs);
            case TEST_ID:
                selection = LabOneTestEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateTest(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateTest(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        return database.update(LabOneTestEntry.TABLE_NAME, values, selection, selectionArgs);    }
}
