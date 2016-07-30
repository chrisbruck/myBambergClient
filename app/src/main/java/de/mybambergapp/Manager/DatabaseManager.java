package de.mybambergapp.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.mybambergapp.dto.SettingsDTO;
import de.mybambergapp.dto.UserDTO;
import de.mybambergapp.entities.Category;

/**
 * Created by christian on 10.05.16.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usersettings.db";


    public static final String CATEGORY_TABLE_NAME = "category";
    //public static final String COLUMN_ID_CATEGORY="id_category";
    public static final String COLUMN_CATEGORY_NAME = "category_name";
    public static final String TABLE_CATEGORY_CREATE = "create table category(" +
            //       "id_category integer primary key not null," +
            "category_name text not null)";

    public static final String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;


    public static final String USER_TABLE_NAME = "user";
    public static final String COLUMN_ANDROID_ID = "android_id";
    public static final String COLUMN_STARTDATE = "startdate";
    public static final String COLUMN_ENDDATE = "enddate";
    public static final String TABLE_USER_CREATE = "create table user(" +
            "android_id string not null," +
            "startdate date not null," +
            "enddate date not null)";
    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;

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
        // String query5 = "DROP TABLE IF EXISTS" + USER_TABLE_NAME;
        // db.execSQL(query4);

    }


    public void insertUserAndCategories(UserDTO userDTO) {

        db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE_CATEGORY);
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(TABLE_CATEGORY_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        ContentValues values = new ContentValues();


        int count = userDTO.getCategoryList().size();
        List<Category> categoryList = userDTO.getCategoryList();
        for (int i = 0; i < count; i++) {
            values.put(COLUMN_CATEGORY_NAME, categoryList.get(i).getCategoryname());
            db.insert(CATEGORY_TABLE_NAME, null, values);
        }
        values.clear();
        values.put(COLUMN_ANDROID_ID, userDTO.getAndroidId());
        values.put(COLUMN_STARTDATE, persistDate(userDTO.getStartdate()));
        values.put(COLUMN_ENDDATE, persistDate(userDTO.getEnddate()));
        db.insert(USER_TABLE_NAME, null, values);
        db.close();

    }

    private static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public UserDTO giveUserSettingsBack() {
        UserDTO userDTO = new UserDTO();
        List<String> categoryStrings = new ArrayList<>();
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String category = cursor.getString(0);
            categoryStrings.add(category);

        }

        String query2 = "SELECT * FROM " + USER_TABLE_NAME;
        Cursor cursor2 = db.rawQuery(query2, null);
        Long startdateLong = null;
        Long enddateLong = null;
        while (cursor2.moveToNext()) {
            startdateLong = cursor2.getLong(1);
            enddateLong = cursor2.getLong(2);
        }

        DateTime startdateTime = new DateTime(startdateLong);
        DateTime enddateTime = new DateTime(enddateLong);

        userDTO.setStartdate(startdateTime.toDate());
        userDTO.setEnddate(enddateTime.toDate());
        userDTO.setCategoryList(fromStringToCategory(categoryStrings));

        db.close();

        return userDTO;

    }
    private List<Category> fromStringToCategory(List<String> catList) {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < catList.size(); i++) {
            String catName = catList.get(i);

            Category category = new Category();
            category.setCategoryname(catName);
           // category.setId((Long.valueOf(i)));
            categoryList.add(category);
           // categoryList.set(i+1, category);

        }
        return categoryList;
    }


    /*private List<Category> fromStringToCategory(List<String> catList) {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < catList.size(); i++) {
            String catName = catList.get(i);
            if (catName.equalsIgnoreCase("museum")) {
                Category category = new Category();
                category.setCategoryname("museum");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("informationsvernastaltung")) {
                Category category = new Category();
                category.setCategoryname("informationsvernastaltung");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("kirche_kloster")) {
                Category category = new Category();
                category.setCategoryname("kirche_kloster");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("kunst")) {
                Category category = new Category();
                category.setCategoryname("kunst");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("musikfest")) {
                Category category = new Category();
                category.setCategoryname("musikfest");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("sport")) {
                Category category = new Category();
                category.setCategoryname("sport");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("straßenfest")) {
                Category category = new Category();
                category.setCategoryname("straßenfest");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("univeranstaltung")) {
                Category category = new Category();
                category.setCategoryname("univeranstaltung");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("nachtleben")) {
                Category category = new Category();
                category.setCategoryname("nachtleben");
                categoryList.set(i, category);
            }
            if (catName.equalsIgnoreCase("theater")) {
                Category category = new Category();
                category.setCategoryname("theater");
                categoryList.set(i, category);
            }


        }

        return  categoryList;
    }*/
}
