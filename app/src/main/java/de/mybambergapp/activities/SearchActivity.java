package de.mybambergapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import de.mybambergapp.R;
import de.mybambergapp.dto.PreferencesDTO;

import de.mybambergapp.manager.DatabaseManager;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends FragmentActivity {


    // der Anwendungskontext hat die mehode getPreferences die die SharedPrefs zurueckgibt
    // SharedPreferences formularwerte = this.getPreferences(MODE_PRIVATE);

    DatabaseManager db = new DatabaseManager(this);


    private CheckBox checkbox_culture, checkbox_art, checkbox_history, checkbox_sport, checkbox_concert, checkbox_party;
    private Button BerstelleRoute;
    private Button BreadPrefFromDB;
    // PreferencesDTO preferencesDTO = new PreferencesDTO();
    PreferencesDTO prefferncesDTO = new PreferencesDTO();
    TextView tvdatepicker;
     int lastday;
    int lastmonth;
    int lastyear;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        addListenerOnButton();
        final PreferencesDTO preferencesDTO = new PreferencesDTO();

        final Calendar c = Calendar.getInstance();

        int hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
        int minute = c.get(c.MINUTE); //Current Minute
     //   int second = c.get(c.SECOND); //Current Second

        //Get the widgets reference from XML layout
        final TextView tvStart = (TextView) findViewById(R.id.tv1);
        final TextView tvEnde = (TextView) findViewById(R.id.tv2);

        TimePicker tpStart = (TimePicker) findViewById(R.id.tp1);
        TimePicker tpEnde = (TimePicker) findViewById(R.id.tp2);

        //Display the TimePicker initial time
        // tv.setText("Initial Time\nH:M:S | " + hourOfDay + ":" + minute + ":" + second);
        tvStart.setText("Gebe die Startzeit an ");
        tvEnde.setText("Gebe die Endzeit an ");

        //Set a TimeChangedListener for TimePicker widget:: ist ein IF und muss implementiert werden
        tpStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                tvStart.setText("Startzeit ist : " + hourOfDay + " : " + minute);
                //save start Hour in PrefDTO
                preferencesDTO.setStarthour(hourOfDay);
                preferencesDTO.setStarthour(minute);


            }
        });

        tpEnde.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                tvEnde.setText("Endzeit ist : " + hourOfDay + " : " + minute);
                //save start Hour in PrefDTO
                preferencesDTO.setEndhour(hourOfDay);
                preferencesDTO.setEndminute(minute);


            }
        });

         tvdatepicker = (TextView) findViewById(R.id.tvdatepicker);

        // jetzt noch den datepicker1*************************************      ********************


        // ersma aus dem kalender aktuelles jahr tag monat auslesen
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int dayOfMonth = c.get(c.DAY_OF_MONTH);

        // widgets zuweisen
        // text

        // datepicker verknüpfung zwischen funktionalität und design
        final DatePicker dp = (DatePicker) findViewById(R.id.dp);

        tvdatepicker.setText("Geb den Tag an ");

        //was macht init ?--> setzt die neuen werte in den datepicker/calendar
        dp.init(year,month,dayOfMonth,dateSetListener);

        // gib mir die werte des calenders raus


       // tvdatepicker.setText("day is :"+day1);



        //*****************************************************************     ********************






    }
        // listener fuer den datepicker erstellen--> der die neuen werte des kalender ausliest und dann TEST ausgibt
    private DatePicker.OnDateChangedListener dateSetListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            int newmonth = monthOfYear+1;
            System.out.println ("TEST"+year+"--"+newmonth+"--"+dayOfMonth+"");

            lastday= dayOfMonth;
            lastmonth=newmonth;
            lastyear= year;

            tvdatepicker.setText(" Dein  Datum ist: "+lastyear+"/ "+lastmonth+"/ "+lastday);


        }

    };


    public void addListenerOnButton() {

        checkbox_culture = (CheckBox) findViewById(R.id.checkbox_culture);
        checkbox_history = (CheckBox) findViewById(R.id.checkbox_history);
        checkbox_art = (CheckBox) findViewById(R.id.checkbox_art);
        checkbox_sport = (CheckBox) findViewById(R.id.checkbox_sport);
        checkbox_concert = (CheckBox) findViewById(R.id.checkbox_concert);
        checkbox_party = (CheckBox) findViewById(R.id.checkbox_party);


        BerstelleRoute = (Button) findViewById(R.id.BerstelleRoute);


        BerstelleRoute.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();

                result.append("Art ist ").append((checkbox_art.isChecked()));
                result.append("\nHistory ist ").append((checkbox_history.isChecked()));
                result.append("\nCulture ist ").append((checkbox_culture.isChecked()));



               // result.append();

                // PreferencesDTO prefferncesDTO = new PreferencesDTO();

                prefferncesDTO.setCulture(checkbox_culture.isChecked());
                prefferncesDTO.setHistory(checkbox_history.isChecked());
                prefferncesDTO.setArt(checkbox_art.isChecked());

                db.insertPrefrences(prefferncesDTO);


                Toast.makeText(SearchActivity.this, result.toString()
                        , Toast.LENGTH_LONG).show();


            }


        });
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

    }


    public void onClickSavePreferences(View v){
        if(v.getId()==R.id.BsavePreferences){
            /*Calendar c = Calendar.getInstance();
            int year = c.get(c.YEAR);
            int month = c.get(c.MONTH);
            int dayOfMonth = c.get(c.DAY_OF_MONTH);*/

            System.out.println("year is: "+lastyear+"month is : "+lastmonth+" day is : "+lastday);


            // int day1= dp.getDayOfMonth();
            //int month1 = dp.getMonth();
           // int year1= dp.getYear();

        }

    }


    public void onClickReadDB(View v) {

        if (v.getId() == R.id.BreadPrefFromDB) {
            int result = db.givePrefrences();
            Toast temp = Toast.makeText(SearchActivity.this, "Das Ergebniss ist :" + result + "!!", Toast.LENGTH_SHORT);
            temp.show();

        }

    }


}














