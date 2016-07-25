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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.mybambergapp.R;
import de.mybambergapp.dto.SettingsDTO;

import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.manager.DatabaseManager;
import de.mybambergapp.manager.Repository;
import de.mybambergapp.manager.RepositoryImpl;
import de.mybambergapp.manager.SingletonRequestQueue;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


    private CheckBox checkbox_kirche_kloster, checkbox_museum, checkbox_straßenfest, checkbox_musikfest, checkbox_nachtleben;
    private CheckBox checkbox_theater,checkbox_kunst,checkbox_univeranstaltung, checkbox_informationsveranstaltung,checkbox_sport;
    boolean kirche_kloster, museum, straßenfest, musikfest, nachtleben, theater,kunst,univeranstaltung,informationsveranstaltung,sport;


    TextView tvdatepicker, answerText;
    int lastday, lastmonth, lastyear;
    int starthour, startminute, endhour, endminute;

    SettingsDTO settingsDTO = new SettingsDTO();
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


            settingsDTO.setKirche_kloster(kirche_kloster);
            settingsDTO.setMuseum(museum);
            settingsDTO.setStraßenfest(straßenfest);
            settingsDTO.setMusikfest(musikfest);
            settingsDTO.setNachtleben(nachtleben);
            settingsDTO.setTheater(theater);
            settingsDTO.setKunst(kunst);
            settingsDTO.setUniveranstaltung(univeranstaltung);
            settingsDTO.setInformationsvernastaltung(informationsveranstaltung);
            settingsDTO.setSport(sport);

            final String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);



            settingsDTO.setStarthour(starthour);
            settingsDTO.setStartminute(startminute);
            settingsDTO.setEndminute(endminute);
            settingsDTO.setStarthour(endhour);
            settingsDTO.setDay(lastday);
            settingsDTO.setMonth(lastmonth);
            settingsDTO.setYear(lastyear);
            settingsDTO.setAndroidID(android_id);


            System.out.println(settingsDTO.toString());


          //  postPreferences(this, settingsDTO);
             postPreferencesWithJackson(settingsDTO);

        }

    }

    /*public void postPreferences(Context context, final SettingsDTO prefDTO) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.1.8:8080/routen", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.d("TAG", "Response geht ja!! ");
                    answerText = (TextView) findViewById(R.id.tvDisplayAnswer);
                    answerText.setText(response);
                } else {
                    Log.e("TAG", "An error occurred while parsing the data! Stack trace follows:");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", String.valueOf(prefDTO.getAndroidID()));
                params.put("culture", String.valueOf(prefDTO.isCulture()));
                params.put("art", String.valueOf(prefDTO.isArt()));
                params.put("history", String.valueOf(prefDTO.isHistory()));
                params.put("party", String.valueOf(prefDTO.isParty()));
                params.put("concert", String.valueOf(prefDTO.isConcert()));
                params.put("sport", String.valueOf(prefDTO.isSport()));
                params.put("starthour", String.valueOf(prefDTO.getStarthour()));
                params.put("startminute", String.valueOf(prefDTO.getStartminute()));
                params.put("endhour", String.valueOf(prefDTO.getEndhour()));
                params.put("endminute", String.valueOf(prefDTO.getEndminute()));
                params.put("day", String.valueOf(prefDTO.getDay()));
                params.put("month", String.valueOf(prefDTO.getMonth()));
                params.put("year", String.valueOf(prefDTO.getYear()));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }*/

    public Map<String, String> getParams (SettingsDTO settingsDTO) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("userID", String.valueOf(settingsDTO.getAndroidID()));

        params.put("kirche_kloster", String.valueOf(settingsDTO.isKirche_kloster()));
        params.put("museum", String.valueOf(settingsDTO.isMuseum()));
        params.put("straßenfest", String.valueOf(settingsDTO.isStraßenfest()));
        params.put("musikfest", String.valueOf(settingsDTO.isMusikfest()));
        params.put("nachtleben", String.valueOf(settingsDTO.isNachtleben()));
        params.put("theater", String.valueOf(settingsDTO.isTheater()));
        params.put("kunst", String.valueOf(settingsDTO.isKunst()));
        params.put("univeranataltung", String.valueOf(settingsDTO.isUniveranstaltung()));
        params.put("infovernastaltung", String.valueOf(settingsDTO.isInformationsvernastaltung()));
        params.put("sport", String.valueOf(settingsDTO.isSport()));





        params.put("starthour", String.valueOf(settingsDTO.getStarthour()));
        params.put("startminute", String.valueOf(settingsDTO.getStartminute()));
        params.put("endhour", String.valueOf(settingsDTO.getEndhour()));
        params.put("endminute", String.valueOf(settingsDTO.getEndminute()));
        params.put("day", String.valueOf(settingsDTO.getDay()));
        params.put("month", String.valueOf(settingsDTO.getMonth()));
        params.put("year", String.valueOf(settingsDTO.getYear()));
        return params;
    }

// POST Methode mit der erstmal die User Settings und ID dem Server uebermittelt werden
    public void postPreferencesWithJackson(final SettingsDTO settingsDTO) {
         final int DEFAULT_TIMEOUT = 30000; //
        Map<String, String> params = getParams(settingsDTO);

        RequestQueue queue = SingletonRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
       //String url1 = "http://192.168.1.8:8080/onetag";
        String url = "http://192.168.2.104:8080/routen";
        JacksonRequest<RouteDTO> MyJacksonRequestObject =
                new JacksonRequest<>(DEFAULT_TIMEOUT,Request.Method.POST,
                        url,params,
                        new JacksonRequestListener<RouteDTO>() {
                            @Override
                            public void onResponse(RouteDTO response, int statusCode, VolleyError error) {
                                if (response != null) {
                                    Log.d("TAG", "Response is : " + response.toString());

                                } else {
                                    Log.e("TAG", "An error occurred while parsing the data! Stack trace follows:");
                                    error.printStackTrace();
                                }
                            }
                            @Override
                            public JavaType getReturnType() {

                                return SimpleType.construct(RouteDTO.class);

                            }
                        });




           /* public Map<String, String> getHeaders ()throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/x-www-form-urlencoded");
            return params;
        }*/



        // jacksonRequestObject der Singelton-RequestQueue uebergeben
        SingletonRequestQueue.getInstance(this.getApplication()).addToRequestQueue(MyJacksonRequestObject);


    }

public void getEventsWithJackson(){




}
    public void onClickReadDB(View v) {

        if (v.getId() == R.id.BreadPrefFromDB) {
            int result = db.givePrefrences();
            Toast temp = Toast.makeText(SearchActivity.this, "Das Ergebniss ist :" + result + "!!", Toast.LENGTH_SHORT);
            temp.show();

        }

    }


    public void onClick(View v) {

    }
    //Toast.makeText(SearchActivity.this, result.toString() , Toast.LENGTH_LONG).show();

}














