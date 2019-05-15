package ua.kpi.ecampus.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import ua.kpi.ecampus.database.SqlHelper;
import ua.kpi.ecampus.model.pojo.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Token table entity in SQliteDB.
 */
public class TokenTable {

    public static final Uri URI = SqlHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull Token token) {
        context.getContentResolver().insert(URI, toContentValues(token));
    }

    public static void save(Context context, @NonNull List<Token> tokens) {
        ContentValues[] values = new ContentValues[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            values[i] = toContentValues(tokens.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Token token) {
        ContentValues values = new ContentValues();
        values.put(Columns.ACCESS_TOKEN, token.getAccessToken());
//        values.put(Columns.TOKEN_TYPE, token.getTokenType());
//        values.put(Columns.EXPIRES_IN, token.getExpiresIn());
        return values;
    }

    @NonNull
    public static Token fromCursor(@NonNull Cursor cursor) {
        String token = cursor.getString(cursor.getColumnIndex(Columns.ACCESS_TOKEN));
        return new Token(token);
    }

    @NonNull
    public static List<Token> listFromCursor(@NonNull Cursor cursor) {
        List<Token> tokens = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return tokens;
        }
        try {
            do {
                tokens.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return tokens;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String ACCESS_TOKEN = "token";
    }

    public interface Requests {

        String TABLE_NAME = TokenTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ACCESS_TOKEN + " VARCHAR);";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
