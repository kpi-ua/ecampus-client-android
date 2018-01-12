package ua.kpi.ecampus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import ua.kpi.ecampus.database.table.TokenTable;

/**
 * Manage database creation and version management.
 *
 * Created by Administrator on 09.02.2016.
 */
public class SqlHelper extends SQLiteOpenHelper {

    public static final String CONTENT_AUTHORITY = "ua.kpi.ecampus.database.contentprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String DATABASE_NAME = "api.db";

    private static final int DATABASE_VERSION = 1;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TokenTable.Requests.CREATION_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TokenTable.Requests.DROP_REQUEST);
        onCreate(sqLiteDatabase);
    }
}
