package de.mybambergapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.provider.Settings.Secure;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.mybambergapp.R;
import de.mybambergapp.dto.EventDTO;
import de.mybambergapp.dto.PreferencesDTO;

import de.mybambergapp.dto.TagDTO;
import de.mybambergapp.manager.DatabaseManager;
import de.mybambergapp.manager.SingletonRequestQueue;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


    private CheckBox checkbox_culture, checkbox_art, checkbox_history, checkbox_sport, checkbox_concert, checkbox_party;
    boolean culture, history, art, sport, concert, party;


    TextView tvdatepicker, answerText;
    int lastday, lastmonth, lastyear;
    int starthour, startminute, endhour, endminute;

    PreferencesDTO prefferncesDTO = new PreferencesDTO();
    DatabaseManager db = new DatabaseManager(this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final PreferencesDTO preferencesDTO = new PreferencesDTO();
        setAttributes();
        setDatePicker();
        setStartTimePicker();
        setEndTimePicker();
    }


    public void setAttributes() {
        checkbox_culture = (CheckBox) findViewById(R.id.checkbox_culture);
        checkbox_history = (CheckBox) findViewById(R.id.checkbox_history);
        checkbox_art = (CheckBox) findViewById(R.id.checkbox_art);
        checkbox_sport = (CheckBox) findViewById(R.id.checkbox_sport);
        checkbox_concert = (CheckBox) findViewById(R.id.checkbox_concert);
        checkbox_party = (CheckBox) findViewById(R.id.checkbox_party);
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


            art = checkbox_art.isChecked();
            history = checkbox_history.isChecked();
            culture = checkbox_culture.isChecked();
            sport = checkbox_sport.isChecked();
            concert = checkbox_concert.isChecked();
            party = checkbox_party.isChecked();

            final String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


            prefferncesDTO.setArt(art);
            prefferncesDTO.setHistory(history);
            prefferncesDTO.setCulture(culture);
            prefferncesDTO.setSport(sport);
            prefferncesDTO.setParty(party);
            prefferncesDTO.setConcert(concert);
            prefferncesDTO.setStarthour(starthour);
            prefferncesDTO.setStartminute(startminute);
            prefferncesDTO.setEndminute(endminute);
            prefferncesDTO.setStarthour(endhour);
            prefferncesDTO.setDay(lastday);
            prefferncesDTO.setMonth(lastmonth);
            prefferncesDTO.setYear(lastyear);
            prefferncesDTO.setAndroidID(android_id);


            System.out.println(prefferncesDTO.toString());


            postPreferences(this, prefferncesDTO);
            // postPreferencesWithJackson(prefferncesDTO);

        }

    }

    public void postPreferences(Context context, final PreferencesDTO prefDTO) {

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
    }


    /*public void postPreferencesWithJackson(final PreferencesDTO prefDTO) {

        RequestQueue queue = SingletonRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
       // String url1 = "http://192.168.2.108:8080/onetag";
        String url = "http://192.168.1.8:8080/routen";
        JacksonRequest<EventDTO> MyJacksonRequestObject =
                new JacksonRequest<>(Request.Method.POST,
                        url,
                        new JacksonRequestListener<EventDTO>() {
                            @Override
                            public void onResponse(EventDTO response, int statusCode, VolleyError error) {
                                if (response != null) {
                                    Log.d("TAG", "Response is : " + response.getEventname() + response.getTag().getTagName());

                                } else {
                                    Log.e("TAG", "An error occurred while parsing the data! Stack trace follows:");
                                    error.printStackTrace();
                                }
                            }
                            @Override
                            public JavaType getReturnType() {
                                return SimpleType.construct(EventDTO.class);
                            }
                        });
       {

            protected Map<String, String> getParams () {
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

            public Map<String, String> getHeaders ()throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/x-www-form-urlencoded");
            return params;
        }

        } ;

        // jacksonRequestObject der Singelton-RequestQueue uebergeben
        SingletonRequestQueue.getInstance(this.getApplication()).addToRequestQueue(MyJacksonRequestObject);


    }*/


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














