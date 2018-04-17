package me.aungkooo.geologist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.aungkooo.geologist.model.StratigraphyLocation;

/**
 * Created by Ko Oo on 13/4/2018.
 */

public class StratigraphyLocationDb extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stratigraphy_location.db";
    public static final String TABLE_NAME = "location";

    private String COMMA = ", ";
    private String NOT_NULL = " NOT NULL";
    private String TEXT = " TEXT";

    // Columns
    public static final String KEY_ID = "id";
    public static final String KEY_TRAVERSE_ID = "traverse_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    public static final String KEY_FORMATION = "formation";
    public static final String KEY_LITHOLOGY = "lithology";
    public static final String KEY_FOSSIL = "fossil";
    public static final String KEY_AGE = "age";
    public static final String KEY_FM_PATH = "fm_path";
    public static final String KEY_FM_NAME = "fm_name";

    public static final String KEY_BEDDING_PLANE = "bedding_plane";
    public static final String KEY_FOLD_AXIS = "fold_axis";
    public static final String KEY_FAULT = "fault";
    public static final String KEY_JOINT = "joint";

    public static final String KEY_R_PATH = "r_path";
    public static final String KEY_R_NAME = "r_name";
    public static final String KEY_F_PATH = "f_path";
    public static final String KEY_F_NAME = "f_name";

    public static final String KEY_MIN = "mineralization";
    public static final String KEY_ORE = "ore";
    public static final String KEY_MIN_NATURE = "mineralization_nature";
    public static final String KEY_O_PATH = "o_path";
    public static final String KEY_O_NAME = "o_name";

    public static final String KEY_NOTE = "note";

    private String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
                    KEY_TRAVERSE_ID + " INT" + NOT_NULL + COMMA +
                    KEY_TITLE + TEXT + NOT_NULL + COMMA +
                    KEY_DATE + TEXT + NOT_NULL + COMMA +
                    KEY_TIME + TEXT + NOT_NULL + COMMA +
                    KEY_LATITUDE + TEXT + COMMA +
                    KEY_LONGITUDE + TEXT + COMMA +

                    KEY_FORMATION + TEXT + COMMA +
                    KEY_LITHOLOGY + TEXT + COMMA +
                    KEY_FOSSIL + TEXT + COMMA +
                    KEY_AGE + TEXT + COMMA +
                    KEY_FM_PATH + TEXT + COMMA +
                    KEY_FM_NAME + TEXT + COMMA +

                    KEY_BEDDING_PLANE + TEXT + COMMA +
                    KEY_FOLD_AXIS + TEXT + COMMA +
                    KEY_FAULT + TEXT + COMMA +
                    KEY_JOINT + TEXT + COMMA +

                    KEY_R_PATH + TEXT + COMMA +
                    KEY_R_NAME + TEXT + COMMA +
                    KEY_F_PATH + TEXT + COMMA +
                    KEY_F_NAME + TEXT + COMMA +

                    KEY_MIN + TEXT + COMMA +
                    KEY_ORE + TEXT + COMMA +
                    KEY_MIN_NATURE + TEXT + COMMA +
                    KEY_O_PATH + TEXT + COMMA +
                    KEY_O_NAME + TEXT + COMMA +

                    KEY_NOTE + TEXT + ")";

    public StratigraphyLocationDb(Context context) {
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

    public long insertLocation(StratigraphyLocation location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TRAVERSE_ID, location.getTraverseId());
        values.put(KEY_TITLE, location.getTitle());
        values.put(KEY_TIME, location.getTime());
        values.put(KEY_DATE, location.getDate());
        values.put(KEY_LATITUDE, location.getLatitude());
        values.put(KEY_LONGITUDE, location.getLongitude());

        values.put(KEY_FORMATION, location.getFormation());
        values.put(KEY_LITHOLOGY, location.getLithology());
        values.put(KEY_FOSSIL, location.getFossil());
        values.put(KEY_AGE, location.getAge());
        values.put(KEY_FM_PATH, location.getFmPath());
        values.put(KEY_FM_NAME, location.getFmName());

        values.put(KEY_BEDDING_PLANE, location.getBeddingPlane());
        values.put(KEY_FOLD_AXIS, location.getFoldAxis());
        values.put(KEY_FAULT, location.getFault());
        values.put(KEY_JOINT, location.getJoint());

        values.put(KEY_R_PATH, location.getrPath());
        values.put(KEY_R_NAME, location.getrName());
        values.put(KEY_F_PATH, location.getfPath());
        values.put(KEY_F_NAME, location.getfName());

        values.put(KEY_MIN, location.getMineralization());
        values.put(KEY_ORE, location.getOre());
        values.put(KEY_MIN_NATURE, location.getMineralizationNature());
        values.put(KEY_O_PATH, location.getoPath());
        values.put(KEY_O_NAME, location.getoName());

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

    public Cursor getLocation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(id)};

        return db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
    }

    public ArrayList<StratigraphyLocation> getAllLocation(int traverseId)
    {
        ArrayList<StratigraphyLocation> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            locationList.add(new StratigraphyLocation(
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

    public ArrayList<StratigraphyLocation> printAllLocation(int traverseId)
    {
        ArrayList<StratigraphyLocation> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            locationList.add(new StratigraphyLocation(
                    cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(KEY_TIME)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),

                    cursor.getString(cursor.getColumnIndex(KEY_FORMATION)),
                    cursor.getString(cursor.getColumnIndex(KEY_LITHOLOGY)),
                    cursor.getString(cursor.getColumnIndex(KEY_FOSSIL)),
                    cursor.getString(cursor.getColumnIndex(KEY_AGE)),
                    cursor.getString(cursor.getColumnIndex(KEY_FM_NAME)),

                    cursor.getString(cursor.getColumnIndex(KEY_BEDDING_PLANE)),
                    cursor.getString(cursor.getColumnIndex(KEY_FOLD_AXIS)),
                    cursor.getString(cursor.getColumnIndex(KEY_FAULT)),
                    cursor.getString(cursor.getColumnIndex(KEY_JOINT)),

                    cursor.getString(cursor.getColumnIndex(KEY_R_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_F_NAME)),

                    cursor.getString(cursor.getColumnIndex(KEY_MIN)),
                    cursor.getString(cursor.getColumnIndex(KEY_ORE)),
                    cursor.getString(cursor.getColumnIndex(KEY_MIN_NATURE)),
                    cursor.getString(cursor.getColumnIndex(KEY_O_NAME)),

                    cursor.getString(cursor.getColumnIndex(KEY_NOTE))
                    )
            );

            cursor.moveToNext();
        }

        cursor.close();

        return locationList;
    }
}
