package de.mybambergapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.mybambergapp.R;
import de.mybambergapp.dto.SettingsDTO;

import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.dto.UserDTO;
import de.mybambergapp.exceptions.MyWrongJsonException;
import de.mybambergapp.manager.DatabaseManager;
import de.mybambergapp.manager.Repository;
import de.mybambergapp.manager.RepositoryImpl;
import de.mybambergapp.manager.RequestManager;
import de.mybambergapp.manager.SingletonRequestQueue;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {




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


    public void onClickSaveAndPostPreferences(View v) {
        if (v.getId() == R.id.BsaveAndPostPreferences) {


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


            UserDTO userDTO = new UserDTO();
            userDTO.setAndroidID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

            try {
                RequestManager.postUser(this,userDTO);
            } catch (MyWrongJsonException e) {
                Log.e("ERROR",e.getMessage());
            }


        }

    }










    public void onClickReadDB(View v) {

        if (v.getId() == R.id.BreadPrefFromDB) {
          String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            RequestManager.getRoute(this,androidId);

        }

    }


    public void onClick(View v) {

    }
    //Toast.makeText(SearchActivity.this, result.toString() , Toast.LENGTH_LONG).show();

}














