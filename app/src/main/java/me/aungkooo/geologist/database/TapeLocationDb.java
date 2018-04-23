package me.aungkooo.geologist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.aungkooo.geologist.model.TapeLocation;

/**
 * Created by Ko Oo on 14/4/2018.
 */

public class TapeLocationDb extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tape_location.db";
    public static final String TABLE_NAME = "location";

    private String COMMA = ", ";
    private String TEXT = " TEXT";
    private String INT = " INT";
    private String NOT_NULL = " NOT NULL";
    private String AUTO = " INTEGER PRIMARY KEY AUTOINCREMENT";

    // Columns
    public static final String KEY_ID = "id";
    public static final String KEY_TRAVERSE_ID = "traverse_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_STATION_NO = "station_no";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    public static final String KEY_SLOPE_DISTANCE = "slope_distance";
    public static final String KEY_BEARING_SLOPE = "bearing_slope";
    public static final String KEY_HORIZONTAL_DISTANCE = "horizontal_distance";
    public static final String KEY_LITHOLOGY = "lithology";
    public static final String KEY_PHOTO_PATH = "photo_path";
    public static final String KEY_PHOTO_NAME = "photo_name";

    public static final String KEY_BEDDING_FOLIATION = "bedding_foliation";
    public static final String KEY_J1 = "j1";
    public static final String KEY_J2 = "j2";
    public static final String KEY_J3 = "j3";
    public static final String KEY_FOLD_AXIS = "fold_axis";
    public static final String KEY_LINEATION = "lineation";

    public static final String KEY_NOTE = "note";

    private String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + KEY_ID + AUTO + COMMA +
                    KEY_TRAVERSE_ID + INT + NOT_NULL + COMMA +
                    KEY_TITLE + TEXT + NOT_NULL + COMMA +
                    KEY_STATION_NO + INT + NOT_NULL + COMMA +
                    KEY_TIME + TEXT + NOT_NULL + COMMA +
                    KEY_DATE + TEXT + NOT_NULL + COMMA +
                    KEY_LATITUDE + TEXT + COMMA +
                    KEY_LONGITUDE + TEXT + COMMA +

                    KEY_SLOPE_DISTANCE + " DEC" + COMMA +
                    KEY_BEARING_SLOPE + TEXT + COMMA +
                    KEY_HORIZONTAL_DISTANCE + " DEC" + COMMA +
                    KEY_LITHOLOGY + TEXT + COMMA +
                    KEY_PHOTO_PATH + TEXT + COMMA +
                    KEY_PHOTO_NAME + TEXT + COMMA +

                    KEY_BEDDING_FOLIATION + TEXT + COMMA +
                    KEY_J1 + TEXT + COMMA +
                    KEY_J2 + TEXT + COMMA +
                    KEY_J3 + TEXT + COMMA +
                    KEY_FOLD_AXIS + TEXT + COMMA +
                    KEY_LINEATION + TEXT + COMMA +

                    KEY_NOTE + TEXT + ")";

    public TapeLocationDb(Context context) {
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

    public long insertLocation(TapeLocation location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TRAVERSE_ID, location.getTraverseId());
        values.put(KEY_TITLE, location.getTitle());
        values.put(KEY_STATION_NO, location.getStationNo());
        values.put(KEY_TIME, location.getTime());
        values.put(KEY_DATE, location.getDate());
        values.put(KEY_LATITUDE, location.getLatitude());
        values.put(KEY_LONGITUDE, location.getLongitude());

        values.put(KEY_SLOPE_DISTANCE, location.getSlopeDistance());
        values.put(KEY_BEARING_SLOPE, location.getBearingSlope());
        values.put(KEY_HORIZONTAL_DISTANCE, location.getHorizontalDistance());
        values.put(KEY_LITHOLOGY, location.getLithology());
        values.put(KEY_PHOTO_PATH, location.getPhotoPath());
        values.put(KEY_PHOTO_NAME, location.getPhotoName());

        values.put(KEY_NOTE, location.getNote());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteLocation(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = KEY_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(id)};

        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public void deleteAllLocation(int traverseId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public TapeLocation getLocation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(id)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        TapeLocation location = new TapeLocation(
                cursor.getInt(cursor.getColumnIndex(KEY_STATION_NO)),
                cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                cursor.getString(cursor.getColumnIndex(KEY_TIME)),
                cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),

                cursor.getString(cursor.getColumnIndex(KEY_BEARING_SLOPE)),
                cursor.getDouble(cursor.getColumnIndex(KEY_HORIZONTAL_DISTANCE)),
                cursor.getDouble(cursor.getColumnIndex(KEY_SLOPE_DISTANCE)),
                cursor.getString(cursor.getColumnIndex(KEY_LITHOLOGY)),
                cursor.getString(cursor.getColumnIndex(KEY_PHOTO_PATH)),
                cursor.getString(cursor.getColumnIndex(KEY_PHOTO_NAME)),

                cursor.getString(cursor.getColumnIndex(KEY_BEDDING_FOLIATION)),
                cursor.getString(cursor.getColumnIndex(KEY_J1)),
                cursor.getString(cursor.getColumnIndex(KEY_J2)),
                cursor.getString(cursor.getColumnIndex(KEY_J3)),
                cursor.getString(cursor.getColumnIndex(KEY_FOLD_AXIS)),
                cursor.getString(cursor.getColumnIndex(KEY_LINEATION)),

                cursor.getString(cursor.getColumnIndex(KEY_NOTE))
        );

        cursor.close();

        return location;
    }

    public ArrayList<TapeLocation> getAllLocation(int traverseId)
    {
        ArrayList<TapeLocation> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            locationList.add(new TapeLocation(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndex(KEY_TIME))
                    )
            );

            cursor.moveToNext();
        }

        cursor.close();

        return locationList;
    }

    public ArrayList<TapeLocation> printAllLocation(int traverseId)
    {
        ArrayList<TapeLocation> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            locationList.add(new TapeLocation(
                    cursor.getInt(cursor.getColumnIndex(KEY_STATION_NO)),
                    cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_TIME)),
                    cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),

                    cursor.getString(cursor.getColumnIndex(KEY_BEARING_SLOPE)),
                    cursor.getString(cursor.getColumnIndex(KEY_LITHOLOGY)),
                    cursor.getString(cursor.getColumnIndex(KEY_PHOTO_NAME)),

                    cursor.getString(cursor.getColumnIndex(KEY_BEDDING_FOLIATION)),
                    cursor.getString(cursor.getColumnIndex(KEY_J1)),
                    cursor.getString(cursor.getColumnIndex(KEY_J2)),
                    cursor.getString(cursor.getColumnIndex(KEY_J3)),
                    cursor.getString(cursor.getColumnIndex(KEY_FOLD_AXIS)),
                    cursor.getString(cursor.getColumnIndex(KEY_LINEATION)),

                    cursor.getString(cursor.getColumnIndex(KEY_NOTE)),

                    cursor.getDouble(cursor.getColumnIndex(KEY_HORIZONTAL_DISTANCE)),
                    cursor.getDouble(cursor.getColumnIndex(KEY_SLOPE_DISTANCE))
                    )
            );

            cursor.moveToNext();
        }

        cursor.close();

        return locationList;
    }
}
