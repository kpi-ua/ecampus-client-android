package ua.kpi.ecampus.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import ua.kpi.ecampus.database.table.TokenTable;

/**
 * Manages access to a central repository of data that is returned from API calls.
 */
public class ApiContentProvider extends ContentProvider {

    private static final int TOKEN_TABLE = 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(SqlHelper.CONTENT_AUTHORITY, TokenTable.Requests.TABLE_NAME, TOKEN_TABLE);
    }

    private SqlHelper mSqliteHelper;

    @Override
    public boolean onCreate() {
        mSqliteHelper = new SqlHelper(getContext());
        return true;
    }

    @Override
    @NonNull
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case TOKEN_TABLE:
                return TokenTable.Requests.TABLE_NAME;
            default:
                return "";
        }
    }

    @Override
    @NonNull
    public Cursor query(@NonNull Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        } else {
            return database.query(table,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
        }
    }

    @Override
    @NonNull public Uri insert(@NonNull Uri uri, @NonNull ContentValues values) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            long id = database.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            return ContentUris.withAppendedId(uri, id);
        }
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            int numInserted = 0;
            database.beginTransaction();
            try {
                for (ContentValues contentValues : values) {
                    long id = database.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    if (id > 0) {
                        numInserted++;
                    }
                }
                database.setTransactionSuccessful();
            }
            finally {
                database.endTransaction();
            }
            return numInserted;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            return database.delete(table, selection, selectionArgs);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @NonNull ContentValues values,
                      String selection, String[] selectionArgs) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            return database.update(table, values, selection, selectionArgs);
        }
    }

}
