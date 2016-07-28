package de.mybambergapp.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.mybambergapp.R;

import de.mybambergapp.dto.UserDTO;
import de.mybambergapp.entities.Category;
import de.mybambergapp.exceptions.MyWrongJsonException;
import de.mybambergapp.manager.DatabaseManager;
import de.mybambergapp.manager.Repository;
import de.mybambergapp.manager.RepositoryImpl;
import de.mybambergapp.manager.RequestManager;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends AppCompatActivity  {




    private CheckBox checkbox_kirche_kloster, checkbox_museum, checkbox_straßenfest, checkbox_musikfest, checkbox_nachtleben;
    private CheckBox checkbox_theater, checkbox_kunst, checkbox_univeranstaltung, checkbox_informationsveranstaltung, checkbox_sport;
    private boolean kirche_kloster, museum, straßenfest, musikfest, nachtleben, theater, kunst, univeranstaltung, informationsveranstaltung, sport;


    TextView tvdatepicker, answerText;
    int lastday, lastmonth, lastyear;
    int starthour, startminute, endhour, endminute;


    DatabaseManager db = new DatabaseManager(this);
    Repository repository = new RepositoryImpl();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setAttributes();
        setDatePicker();
        setStartTimePicker();
        setEndTimePicker();
    }

    /**
     * todo: richtige kategorien definitiv festlegen
     */
    public void setAttributes() {
        checkbox_kirche_kloster = (CheckBox) findViewById(R.id.checkbox_kirche_kloster);
        checkbox_museum = (CheckBox) findViewById(R.id.checkbox_museum);

        checkbox_straßenfest = (CheckBox) findViewById(R.id.checkbox_straßenfest);
        checkbox_musikfest = (CheckBox) findViewById(R.id.checkbox_musikfest);
        checkbox_nachtleben = (CheckBox) findViewById(R.id.checkbox_nachtleben);

        checkbox_theater = (CheckBox) findViewById(R.id.checkbox_theater);
        checkbox_kunst = (CheckBox) findViewById(R.id.checkbox_kunst);

        checkbox_univeranstaltung = (CheckBox) findViewById(R.id.checkbox_univeranstaltung);
        checkbox_informationsveranstaltung = (CheckBox) findViewById(R.id.checkbox_informationsvernastaltung);


        checkbox_sport = (CheckBox) findViewById(R.id.checkbox_sport);
    }


    public void setDatePicker() {
        tvdatepicker = (TextView) findViewById(R.id.tvdatepicker);
        final Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
        int minute = c.get(c.MINUTE); //Current Minute
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int dayOfMonth = c.get(c.DAY_OF_MONTH);
        final DatePicker dp = (DatePicker) findViewById(R.id.dp);
        tvdatepicker.setText("Geb den Tag an ");
        dp.init(year, month, dayOfMonth, dateSetListener);
    }


    public void setEndTimePicker() {
        TimePicker tpEnde = (TimePicker) findViewById(R.id.tp2);
        final TextView tvEnde = (TextView) findViewById(R.id.tv2);
        tvEnde.setText("Gebe die Endzeit an ");
        tpEnde.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                tvEnde.setText("Endzeit ist : " + hourOfDay + " : " + minute);
                endhour = hourOfDay;
                endminute = minute;
            }
        });
    }


    public void setStartTimePicker() {
        final TextView tvStart = (TextView) findViewById(R.id.tv1);
        TimePicker tpStart = (TimePicker) findViewById(R.id.tp1);
        tvStart.setText("Gebe die Startzeit an ");
        tpStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                tvStart.setText("Startzeit ist : " + hourOfDay + " : " + minute);
                starthour = hourOfDay;
                startminute = minute;
            }
        });
    }

    private DatePicker.OnDateChangedListener dateSetListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            int newmonth = monthOfYear + 1;
            lastday = dayOfMonth;
            lastmonth = newmonth;
            lastyear = year;
            tvdatepicker.setText(" Dein  Datum ist: " + lastyear + "/ " + lastmonth + "/ " + lastday);
        }
    };

    private UserDTO fillUserDTO(UserDTO userDTO){
      userDTO.setCategoryList(saveCategorysInList());
        setStartAndEndDate(userDTO);
      return userDTO;

    }
    private UserDTO setStartAndEndDate(UserDTO userDTO){
        Date startdate = new Date();
        startdate.setYear(lastyear);
        startdate.setMonth(lastmonth);
        startdate.setDate(lastday);
        startdate.setHours(starthour);
        startdate.setMinutes(startminute);

        Date enddate = new Date();
        enddate.setYear(lastyear);
        enddate.setMonth(lastmonth);
        enddate.setDate(lastday);
        enddate.setHours(endhour);
        enddate.setMinutes(endminute);
        userDTO.setStartdate(startdate);
        userDTO.setEnddate(enddate);


        return userDTO;
    }
    private List<Category> saveCategorysInList(){

        List<Category> categoryList = new ArrayList<>();

        kirche_kloster = checkbox_kirche_kloster.isChecked();
        museum = checkbox_museum.isChecked();
        straßenfest = checkbox_straßenfest.isChecked();
        musikfest = checkbox_musikfest.isChecked();
        nachtleben = checkbox_nachtleben.isChecked();
        theater = checkbox_theater.isChecked();
        kunst = checkbox_kunst.isChecked();
        univeranstaltung = checkbox_univeranstaltung.isChecked();
        informationsveranstaltung = checkbox_informationsveranstaltung.isChecked();
        sport = checkbox_sport.isChecked();

        if(kirche_kloster){
           Category category = new Category();
            category.setCategoryname("kirche_kloster");
            categoryList.add(category);
        }
        if(museum){
            Category category = new Category();
            category.setCategoryname("museum");
            categoryList.add(category);
        }

        if(straßenfest){
            Category category = new Category();
            category.setCategoryname("straßenfest");
            categoryList.add(category);
        }

        if(musikfest){
            Category category = new Category();
            category.setCategoryname("musikfest");
            categoryList.add(category);
        }

        if(nachtleben){
            Category category = new Category();
            category.setCategoryname("nachtlebenr");
            categoryList.add(category);
        }

        if(theater){
            Category category = new Category();
            category.setCategoryname("theater");
            categoryList.add(category);
        }

        if(kunst){
            Category category = new Category();
            category.setCategoryname("kunst");
            categoryList.add(category);
        }

        if(univeranstaltung){
            Category category = new Category();
            category.setCategoryname("univeranstaltung");
            categoryList.add(category);
        }

        if(informationsveranstaltung){
            Category category = new Category();
            category.setCategoryname("informationsveranstaltung");
            categoryList.add(category);
        }

        if(sport){
            Category category = new Category();
            category.setCategoryname("sport");
            categoryList.add(category);
        }





        return categoryList;
    }

    public void onClickPostAndSaveUserSettings(View v) {
        if (v.getId() == R.id.BpostAndSaveUserSettings) {

            UserDTO userDTO = new UserDTO();
            fillUserDTO(userDTO);


            userDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

            try {
                RequestManager.postUser(this,userDTO);
            } catch (MyWrongJsonException e) {
                Log.e("ERROR",e.getMessage());
            }

        }

    }



    public void onClickgetRoute(View v) {

        if (v.getId() == R.id.BgetRoute) {
          String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            RequestManager.getRoute(this,androidId);
        }
    }



}














