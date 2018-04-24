package me.aungkooo.geologist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.aungkooo.geologist.model.MyNotesLocation;

/**
 * Created by Ko Oo on 14/4/2018.
 */

public class MyNotesLocationDb extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mynotes_location.db";
    public static final String TABLE_NAME = "location";

    private String COMMA = ", ";
    private String TEXT = " TEXT";
    private String NOT_NULL = " NOT NULL";
    private String AUTO = " INTEGER PRIMARY KEY AUTOINCREMENT";

    // Columns
    public static final String KEY_ID = "id";
    public static final String KEY_TRAVERSE_ID = "traverse_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_MAP = "map";

    public static final String KEY_ROCK_TYPE = "rock_type";
    public static final String KEY_ROCK_UNIT = "rock_unit";
    public static final String KEY_OUTCROP_PATH = "outcrop_path";
    public static final String KEY_OUTCROP_NAME = "outcrop_name";
    public static final String KEY_OUTCROP_FACING = "outcrop_facing";

    public static final String KEY_TEXTURE = "texture";
    public static final String KEY_WEATHERING_COLOR = "weathering_color";
    public static final String KEY_FRESH_COLOR = "fresh_color";
    public static final String KEY_GRAIN_SIZE = "grain_size";
    public static final String KEY_MINERAL_COMPOSITION = "mineral_composition";
    public static final String KEY_LITHOLOGY_NOTE = "lithology_note";
    public static final String KEY_SAMPLE_PATH = "sample_path";
    public static final String KEY_SAMPLE_NAME = "sample_name";
    public static final String KEY_SAMPLE_FACING = "sample_facing";

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
                    KEY_TRAVERSE_ID + " INT" + NOT_NULL + COMMA +
                    KEY_TITLE + TEXT + NOT_NULL + COMMA +
                    KEY_TIME + TEXT + NOT_NULL + COMMA +
                    KEY_DATE + TEXT + NOT_NULL + COMMA +
                    KEY_LATITUDE + TEXT + COMMA +
                    KEY_LONGITUDE + TEXT + COMMA +
                    KEY_MAP + TEXT + COMMA +

                    KEY_ROCK_TYPE + TEXT + COMMA +
                    KEY_ROCK_UNIT + TEXT + COMMA +
                    KEY_OUTCROP_PATH + TEXT + COMMA +
                    KEY_OUTCROP_NAME + TEXT + COMMA +
                    KEY_OUTCROP_FACING + TEXT + COMMA +

                    KEY_TEXTURE + TEXT + COMMA +
                    KEY_WEATHERING_COLOR + TEXT + COMMA +
                    KEY_FRESH_COLOR + TEXT + COMMA +
                    KEY_GRAIN_SIZE + TEXT + COMMA +
                    KEY_MINERAL_COMPOSITION + TEXT + COMMA +
                    KEY_LITHOLOGY_NOTE + TEXT + COMMA +
                    KEY_SAMPLE_PATH + TEXT + COMMA +
                    KEY_SAMPLE_NAME + TEXT + COMMA +
                    KEY_SAMPLE_FACING + TEXT + COMMA +

                    KEY_BEDDING_FOLIATION + TEXT + COMMA +
                    KEY_J1 + TEXT + COMMA +
                    KEY_J2 + TEXT + COMMA +
                    KEY_J3 + TEXT + COMMA +
                    KEY_FOLD_AXIS + TEXT + COMMA +
                    KEY_LINEATION + TEXT + COMMA +

                    KEY_NOTE + TEXT + ")";

    public MyNotesLocationDb(Context context) {
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

    public long insertLocation(MyNotesLocation location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TRAVERSE_ID, location.getTraverseId());
        values.put(KEY_TITLE, location.getTitle());
        values.put(KEY_TIME, location.getTime());
        values.put(KEY_DATE, location.getDate());
        values.put(KEY_LATITUDE, location.getLatitude());
        values.put(KEY_LONGITUDE, location.getLongitude());
        values.put(KEY_MAP, location.getMap());

        values.put(KEY_ROCK_TYPE, location.getRockType());
        values.put(KEY_ROCK_UNIT, location.getRockUnit());
        values.put(KEY_OUTCROP_PATH, location.getOutcropPath());
        values.put(KEY_OUTCROP_NAME, location.getOutcropName());
        values.put(KEY_OUTCROP_FACING, location.getOutcropFacing());

        values.put(KEY_TEXTURE, location.getTexture());
        values.put(KEY_WEATHERING_COLOR, location.getWeatheringColor());
        values.put(KEY_FRESH_COLOR, location.getFreshColor());
        values.put(KEY_GRAIN_SIZE, location.getGrainSize());
        values.put(KEY_MINERAL_COMPOSITION, location.getMineralComposition());

        values.put(KEY_LITHOLOGY_NOTE, location.getLithologyNote());
        values.put(KEY_SAMPLE_PATH, location.getSamplePath());
        values.put(KEY_SAMPLE_NAME, location.getSampleName());
        values.put(KEY_SAMPLE_FACING, location.getSampleFacing());

        values.put(KEY_BEDDING_FOLIATION, location.getBeddingFoliation());
        values.put(KEY_J1, location.getJ1());
        values.put(KEY_J2, location.getJ2());
        values.put(KEY_J3, location.getJ3());
        values.put(KEY_FOLD_AXIS, location.getFoldAxis());
        values.put(KEY_LINEATION, location.getLineation());

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

    public MyNotesLocation getLocation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(id)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        MyNotesLocation location = new MyNotesLocation(
                cursor.getString(cursor.getColumnIndex(KEY_TIME)),
                cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(KEY_MAP)),

                cursor.getString(cursor.getColumnIndex(KEY_ROCK_TYPE)),
                cursor.getString(cursor.getColumnIndex(KEY_ROCK_UNIT)),
                cursor.getString(cursor.getColumnIndex(KEY_OUTCROP_PATH)),
                cursor.getString(cursor.getColumnIndex(KEY_OUTCROP_NAME)),
                cursor.getString(cursor.getColumnIndex(KEY_OUTCROP_FACING)),

                cursor.getString(cursor.getColumnIndex(KEY_TEXTURE)),
                cursor.getString(cursor.getColumnIndex(KEY_WEATHERING_COLOR)),
                cursor.getString(cursor.getColumnIndex(KEY_FRESH_COLOR)),
                cursor.getString(cursor.getColumnIndex(KEY_GRAIN_SIZE)),
                cursor.getString(cursor.getColumnIndex(KEY_MINERAL_COMPOSITION)),
                cursor.getString(cursor.getColumnIndex(KEY_LITHOLOGY_NOTE)),
                cursor.getString(cursor.getColumnIndex(KEY_SAMPLE_PATH)),
                cursor.getString(cursor.getColumnIndex(KEY_SAMPLE_NAME)),
                cursor.getString(cursor.getColumnIndex(KEY_SAMPLE_FACING)),

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

    public ArrayList<MyNotesLocation> getAllLocation(int traverseId)
    {
        ArrayList<MyNotesLocation> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            locationList.add(new MyNotesLocation(
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

    public ArrayList<MyNotesLocation> printAllLocation(int traverseId)
    {
        ArrayList<MyNotesLocation> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_SYNTAX_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        String whereClause = " WHERE " + KEY_TRAVERSE_ID + " =? ";
        String[] whereArgs = new String[] {String.valueOf(traverseId)};

        Cursor cursor = db.rawQuery(SQL_SYNTAX_SELECT_ALL + whereClause, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            locationList.add(new MyNotesLocation(
                    cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(KEY_TIME)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),
                    cursor.getString(cursor.getColumnIndex(KEY_MAP)),

                    cursor.getString(cursor.getColumnIndex(KEY_ROCK_TYPE)),
                    cursor.getString(cursor.getColumnIndex(KEY_ROCK_UNIT)),
                    cursor.getString(cursor.getColumnIndex(KEY_OUTCROP_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_OUTCROP_FACING)),

                    cursor.getString(cursor.getColumnIndex(KEY_TEXTURE)),
                    cursor.getString(cursor.getColumnIndex(KEY_WEATHERING_COLOR)),
                    cursor.getString(cursor.getColumnIndex(KEY_FRESH_COLOR)),
                    cursor.getString(cursor.getColumnIndex(KEY_GRAIN_SIZE)),
                    cursor.getString(cursor.getColumnIndex(KEY_MINERAL_COMPOSITION)),
                    cursor.getString(cursor.getColumnIndex(KEY_LITHOLOGY_NOTE)),
                    cursor.getString(cursor.getColumnIndex(KEY_SAMPLE_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_SAMPLE_FACING)),

                    cursor.getString(cursor.getColumnIndex(KEY_BEDDING_FOLIATION)),
                    cursor.getString(cursor.getColumnIndex(KEY_J1)),
                    cursor.getString(cursor.getColumnIndex(KEY_J2)),
                    cursor.getString(cursor.getColumnIndex(KEY_J3)),
                    cursor.getString(cursor.getColumnIndex(KEY_FOLD_AXIS)),
                    cursor.getString(cursor.getColumnIndex(KEY_LINEATION)),

                    cursor.getString(cursor.getColumnIndex(KEY_NOTE))
                    )
            );

            cursor.moveToNext();
        }

        cursor.close();

        return locationList;
    }
}
