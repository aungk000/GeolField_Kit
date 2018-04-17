package me.aungkooo.geologist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 14/4/2018.
 */

public class MyNotesTraverseDb extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mynotes_traverse.db";
    public static final String TABLE_NAME = "traverse";

    private String COMMA = ", ";
    private String TEXT = " TEXT";
    private String NOT_NULL = " NOT NULL";
    private String AUTO = " INTEGER PRIMARY KEY AUTOINCREMENT";

    // Columns
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";

    private String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + KEY_ID + AUTO + COMMA +
                    KEY_TITLE + TEXT + NOT_NULL + COMMA +
                    KEY_DATE + TEXT + NOT_NULL + ")";

    public MyNotesTraverseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertTraverse(Traverse traverse)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, traverse.getTitle());
        values.put(KEY_DATE, traverse.getDate());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteTraverse(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = KEY_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(id)};

        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public Cursor getTraverse(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(id)};

        return db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
    }

    public ArrayList<Traverse> getAllTraverse()
    {
        ArrayList<Traverse> traverseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            traverseList.add(new Traverse(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE))
            ));

            cursor.moveToNext();
        }

        cursor.close();

        return traverseList;
    }
}
