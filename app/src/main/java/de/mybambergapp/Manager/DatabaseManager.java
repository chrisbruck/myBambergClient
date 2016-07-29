package de.mybambergapp.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.mybambergapp.dto.SettingsDTO;
import de.mybambergapp.dto.UserDTO;
import de.mybambergapp.entities.Category;

/**
 * Created by christian on 10.05.16.
 */
//@Deprecated
public class DatabaseManager extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usersettings.db";


    public static final String CATEGORY_TABLE_NAME="category";
    //public static final String COLUMN_ID_CATEGORY="id_category";
    public static final String COLUMN_CATEGORY_NAME="category_name";
    public  static final String TABLE_CATEGORY_CREATE="create table category(" +
     //       "id_category integer primary key not null," +
            "category_name text not null)";

    public static final String DROP_TABLE_CATEGORY= "DROP TABLE IF EXISTS "+CATEGORY_TABLE_NAME;


    public static final String USER_TABLE_NAME="user";
    public static final String COLUMN_ANDROID_ID="android_id";
    public static final String COLUMN_STARTDATE="startdate";
    public static final String COLUMN_ENDDATE="enddate";
    public  static final String TABLE_USER_CREATE="create table user(" +
            "android_id string not null," +
            "startdate date not null," +
            "enddate date not null)";
    public static final String DROP_TABLE_USER= "DROP TABLE IF EXISTS "+USER_TABLE_NAME;

    // Constructor
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORY_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        this.db = db;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // String query4 = "DROP TABLE IF EXISTS"+ CATEGORY_TABLE_NAME;
       // String query5 = "DROP TABLE IF EXISTS" + USER_TABLE_NAME;
       // db.execSQL(query4);
       // db.execSQL(query5);
    }

/*    public static final String EVENTS_TABLE_NAME="evenets";
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
            "lon_location double not null)";*/


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
    public void insertUserAndCategories(UserDTO userDTO) {
        // da reingeschrieben wird---getWritable
        db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE_CATEGORY);
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(TABLE_CATEGORY_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        ContentValues values = new ContentValues();

        // gib mir die Anzahl schon existierenden tags
        //String query = "SElECT * FROM category";
        //ursor cursor = db.rawQuery(query, null);
        // wieviele sinds?
       // int count = cursor.getCount();
        int count= userDTO.getCategoryList().size();
        List<Category> categoryList =userDTO.getCategoryList();
        for (int i=0; i<count; i++){
            values.put(COLUMN_CATEGORY_NAME,categoryList.get(i).getCategoryname());
            db.insert(CATEGORY_TABLE_NAME, null, values);
        }
        values.clear();
        values.put(COLUMN_ANDROID_ID,userDTO.getAndroidId());
        values.put(COLUMN_STARTDATE,persistDate(userDTO.getStartdate()));
        values.put(COLUMN_ENDDATE,persistDate(userDTO.getEnddate()));
        db.insert(USER_TABLE_NAME,null,values);
        db.close();
        giveUserSettingsBack();
    }

    private static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public void giveUserSettingsBack() {
        UserDTO userDTO = new UserDTO();
        List<String> categoryStrings= new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME;
        String query2 = "SELECT * FROM "+ USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            String category =cursor.getString(0);
            categoryStrings.add(category);

        }

        Cursor cursor2 =db.rawQuery(query2,null);
       // int anzahl = cursor2.getCount();
       String dieter=  cursor2.toString();

        String androidId;
        Long startdateLong = null;
        Long  enddateLong= null;

        while(cursor2.moveToNext()){

           androidId = cursor2.getString(0);
           startdateLong=    cursor2.getLong(1);
           enddateLong=     cursor2.getLong(2);

        }


        Date startdate = new Date(startdateLong);
        Date enddate= new Date(enddateLong);

      //userDTO.setStartdate(((Date ) startdateLong));


        System.out.println(" EINTRAG IST:"+ dieter );
        db.close();

    }
}
