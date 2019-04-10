package com.ahmed.quiz.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ahmed.quiz.data.QuizContract.CategoryTable;
import com.ahmed.quiz.data.QuizContract.QuizTable;

public class QuizProvider extends ContentProvider {

    public static final int CODE_CATEGORY = 100;
    public static final int CODE_QUIZ = 101;
    private static QuizDbHelper dbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(QuizContract.CONTENT_AUTHORITY, QuizTable.TABLE_NAME, CODE_QUIZ);
        matcher.addURI(QuizContract.CONTENT_AUTHORITY, CategoryTable.TABLE_NAME, CODE_CATEGORY);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new QuizDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowInserted = 0;
        switch (sUriMatcher.match(uri)) {
            case CODE_QUIZ:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long insert = db.insert(QuizTable.TABLE_NAME, null, value);
                        if (insert != -1) {
                            rowInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;

            case CODE_CATEGORY:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long insert = db.insert(CategoryTable.TABLE_NAME, null, value);
                        if (insert != -1) {
                            rowInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return rowInserted;

    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_CATEGORY:
                cursor = db.query(CategoryTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;

            case CODE_QUIZ:
                cursor = db.query(QuizTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
