package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Geocoder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class SQLHelper extends SQLiteOpenHelper {
    private Context context;
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "GeoLocationDB";
    // Table name
    private static final String TABLE_NAME = "geoLocationDBTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_ADDRESS = "full_address";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE="longitude";
    double[] latitude = {44.5,
            39,
            44,
            31,
            44.5,
            41.700001,
            44,
            43,
            44,
            41.5,
            38.5,
            33,
            40,
            39,
            41.599998,
            34.799999,
            40.273502,
            38.573936,
            27.994402,
            39.876019,
            45.367584,
            44.182205,
            33.247875,
            19.741755,
            66.160507,
            35.860119,
            37.926868,
            39.833851,
            37.839333,
            47.650589,
            46.39241,
            36.084621,
            46.96526,
            47.751076,
            39.41922,
            39.113014,
            40.367474,
            32.31823,
            42.032974,
            34.307144,
            33.836082,
            41.203323,
            34.048927,
            39.045753,
            42.407211,
            36.778259,
            44.068203,
            43.07597,
            35.782169,
            30.39183};
    double[] longitude = {-89.5,
            -80.5,
            -72.699997,
            -100,
            -100,
            -71.5,
            -120.5,
            -75,
            -71.5,
            -100,
            -98,
            -90,
            -89,
            -75.5,
            -72.699997,
            -92.199997,
            -86.126976,
            -92.60376,
            -81.760254,
            -117.224121,
            -68.972168,
            -84.506836,
            -83.441162,
            -155.844437,
            -153.369141,
            -86.660156,
            -78.024902,
            -74.871826,
            -84.27002,
            -100.437012,
            -94.63623,
            -96.921387,
            -109.533691,
            -120.740135,
            -111.950684,
            -105.358887,
            -82.996216,
            -86.902298,
            -93.581543,
            -106.018066,
            -81.163727,
            -77.194527,
            -111.093735,
            -76.641273,
            -71.382439,
            -119.417931,
            -114.742043,
            -107.290283,
            -80.793457,
            -92.329102};

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getReadableDatabase();
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ADDRESS + " TEXT,"
                + KEY_LATITUDE +" TEXT,"+ KEY_LONGITUDE +" TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void createAddresses(Geocoder geocoder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        for( int i = 0; i < longitude.length; i++ ){
            ContentValues c = new ContentValues();
            try {
                c.put(KEY_ADDRESS, geocoder.getFromLocation(latitude[i], longitude[i], 1).get(0).getAdminArea());
                c.put(KEY_LATITUDE, latitude[i]);
                c.put(KEY_LONGITUDE, longitude[i]);
                db.insertWithOnConflict(TABLE_NAME,null, c, SQLiteDatabase.CONFLICT_REPLACE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createLocation(String address, String latitude, String longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put(KEY_ADDRESS,address);
        c.put(KEY_LATITUDE,latitude);
        c.put(KEY_LONGITUDE,longitude);
        db.insert(TABLE_NAME,null,c);

    }

    public void updateLocation(String id, String address, String latitude, String longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put(KEY_ID,id);
        c.put(KEY_ADDRESS,address);
        c.put(KEY_LATITUDE,latitude);
        c.put(KEY_LONGITUDE,longitude);

        db.update(TABLE_NAME, c, "_id = ?",new String[]{id});
    }

    public void deleteLocation(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues c = new ContentValues();

        db.delete(TABLE_NAME, "_id = ?" ,new String[]{id});
    }

    public Cursor getLatLong(String address)
    {
        String query = "SELECT " + KEY_LATITUDE+ ", "+ KEY_LONGITUDE+ " FROM " + TABLE_NAME+ " WHERE " + KEY_ADDRESS + " = " + address;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
    public Cursor readAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }




}
