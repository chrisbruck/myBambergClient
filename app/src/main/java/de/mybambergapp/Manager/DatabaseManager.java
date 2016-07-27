package de.mybambergapp.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.mybambergapp.dto.SettingsDTO;

/**
 * Created by christian on 10.05.16.
 */
@Deprecated
public class DatabaseManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bambergevents.db";

    /*public static final String EVENTS_TABLE_NAME="evenets";
    public static final String COLUMN_ID_EVENT="id_event";
    public static final String COLUMN_DATE_EVENT = "date_event";
    public static final String COLUMN_NAME_EVENT="name_event";
    public static final String COLUMN_LOCATIONID_EVENT="location_id";
    public static final String COLUMN_TAGID_EVENT="tag_id";
    public static final String COLUMN_WEATHERID_EVENT="weather_id";
    public  static final String TABLE_EVENTS_CREATE="create table events(" +
            "id_event integer primary key not null," +
            "name_event text not null," +
            "date_event date not null," +
            " FOREIGN KEY (location_id) REFERENCES locations(id_location)"+
            "FOREIGN KEY(tag_id) REFERENCES tags(id_tag)"+
            "FOREIGN KEY(weather_id) REFERENCES weather(id_weather)";



    public static final String LOCATIONS_TABLE_NAME="locations";
    public static final String COLUMN_ID_LOCATION="id_location";
    public static final String COLUMN_NAME_LOCATION="name_location";
    public static final String COLUMN_LAT_LOCATION = "lat_location";
    public static final String COLUMN_LON_LOCATION="lon_location";
    public  static final String TABLE_LOCATIONS_CREATE="create table locations(" +
            "id_location integer primary key not null," +
            "name_location text not null," +
            "lat_location double not null," +
            "lon_location double not null)";



    public static final String TAGS_TABLE_NAME="tags";
    public static final String COLUMN_ID_TAGS="id_tag";
    public static final String COLUMN_NAME_TAGS="name_tag";
    public  static final String TABLE_TAGS_CREATE="create table tags(" +
            "id_tag integer primary key not null," +
            "name_tag text not null)";


    public static final String WEATHER_TABLE_NAME="weather";
    public static final String COLUMN_ID_WEATHER="id_weather";
    public static final String COLUMN_NAME_WEATHER="name_weather";
    public  static final String TABLE_WEATHER_CREATE="create table weather(" +
            "id_weather integer primary key not null," +
            "name_weather text not null)";
*/

    public static final String PREFERENCES_TABLE_NAME = "preferences";
    public static final String COLUMN_ID_PREFERENCES = "id_preferences";

    public static final String COLUMN_CULTURE = "culture";
    public static final String COLUMN_ART = "art";
    public static final String COLUMN_HISTORY = "history";

    public static final String TABLE_PREFERENCES_CREATE = "create table preferences(" +
            "id_preferences integer primary key not null," +
            "culture text not null," +
            "art text not null," +
            "history text not null)";


    SQLiteDatabase db;


    // Constructor
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL(TABLE_LOCATIONS_CREATE);
        // db.execSQL(TABLE_TAGS_CREATE);
        // db.execSQL(TABLE_WEATHER_CREATE);
        //db.execSQL(TABLE_EVENTS_CREATE);
        db.execSQL(TABLE_PREFERENCES_CREATE);
        this.db = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // String query1 = "DROP TABLE IF EXISTS"+ EVENTS_TABLE_NAME;
        // String query2 = "DROP TABLE IF EXISTS"+ LOCATIONS_TABLE_NAME;
        // String query3 = "DROP TABLE IF EXISTS"+ TAGS_TABLE_NAME;
        // String query4 = "DROP TABLE IF EXISTS"+ WEATHER_TABLE_NAME;
        String query5 = "DROP TABLE IF EXISTS" + PREFERENCES_TABLE_NAME;

    }

    /*public void insertTags(TagDTO tagDTO){
        // da reingeschrieben wird---getWritable
        db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // gib mir die Anzahl schon existierenden tags
        String query= "SElECT * FROM tags";
        Cursor cursor= db.rawQuery(query,null);


        // wieviele sinds?
        int count= cursor.getCount();

        values.put(COLUMN_ID_TAGS,count);

        values.put(COLUMN_NAME_TAGS, tagDTO.getTagName());


        db.insert(TAGS_TABLE_NAME, null, values);

        db.close();

    }*/
    public void insertPrefrences(SettingsDTO preferencesDTO) {
        // da reingeschrieben wird---getWritable
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // gib mir die Anzahl schon existierenden tags
        String query = "SElECT * FROM preferences";
        Cursor cursor = db.rawQuery(query, null);


        // wieviele sinds?
        int count = cursor.getCount();

        values.put(COLUMN_ID_PREFERENCES, count);

       // values.put(COLUMN_CULTURE, preferencesDTO.isCulture());
       // values.put(COLUMN_ART, preferencesDTO.isArt());
       // values.put(COLUMN_HISTORY, preferencesDTO.isHistory());


        db.insert(PREFERENCES_TABLE_NAME, null, values);

        db.close();

    }

    public int givePrefrences() {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PREFERENCES_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);


        int result=cursor.getCount();
        return  result;


    }
}
