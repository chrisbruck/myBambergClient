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
import de.mybambergapp.dto.PreferencesDTO;

import de.mybambergapp.dto.TagDTO;
import de.mybambergapp.manager.DatabaseManager;
import de.mybambergapp.manager.SingletonRequestQueue;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends FragmentActivity {


    // der Anwendungskontext hat die mehode getPreferences die die SharedPrefs zurueckgibt
    // SharedPreferences formularwerte = this.getPreferences(MODE_PRIVATE);

    DatabaseManager db = new DatabaseManager(this);


    private CheckBox checkbox_culture, checkbox_art, checkbox_history, checkbox_sport, checkbox_concert, checkbox_party;
    private Button BerstelleRoute;
private Button BsubmittPrefs;




    PreferencesDTO prefferncesDTO = new PreferencesDTO();
    TextView tvdatepicker;

    int lastday;
    int lastmonth;
    int lastyear;

    int starthour;
    int startminute;
    int endhour;
    int endminute;

    boolean culture, history, art, sport, concert, party;


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
                //preferencesDTO.setStarthour(hourOfDay);
                //preferencesDTO.setStarthour(minute);
                starthour = hourOfDay;
                startminute = minute;


            }
        });

        tpEnde.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                tvEnde.setText("Endzeit ist : " + hourOfDay + " : " + minute);
                //save start Hour in PrefDTO
                //preferencesDTO.setEndhour(hourOfDay);
                //preferencesDTO.setEndminute(minute);
                endhour = hourOfDay;
                endminute = minute;


            }
        });

        tvdatepicker = (TextView) findViewById(R.id.tvdatepicker);

        // jetzt noch den datepicker1*************************************      ********************


        // ersma aus dem kalender aktuelles jahr tag monat auslesen
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int dayOfMonth = c.get(c.DAY_OF_MONTH);

        // widgets zuweisen

        final DatePicker dp = (DatePicker) findViewById(R.id.dp);

        tvdatepicker.setText("Geb den Tag an ");

        //was macht init ?--> setzt die neuen-vom listener kommende werte in den datepicker/calendar
        dp.init(year, month, dayOfMonth, dateSetListener);

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
            int newmonth = monthOfYear + 1;
            //  System.out.println ("TEST"+year+"--"+newmonth+"--"+dayOfMonth+"");

            lastday = dayOfMonth;
            lastmonth = newmonth;
            lastyear = year;

            tvdatepicker.setText(" Dein  Datum ist: " + lastyear + "/ " + lastmonth + "/ " + lastday);


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

               /* StringBuffer result = new StringBuffer();

                result.append("Art ist ").append((checkbox_art.isChecked()));
                result.append("\nHistory ist ").append((checkbox_history.isChecked()));
                result.append("\nCulture ist ").append((checkbox_culture.isChecked()));*/

                art = checkbox_art.isChecked();
                history = checkbox_history.isChecked();
                culture = checkbox_culture.isChecked();
                sport = checkbox_sport.isChecked();
                concert = checkbox_concert.isChecked();
                party = checkbox_party.isChecked();


                // result.append();

                // PreferencesDTO prefferncesDTO = new PreferencesDTO();

                prefferncesDTO.setCulture(checkbox_culture.isChecked());
                prefferncesDTO.setHistory(checkbox_history.isChecked());
                prefferncesDTO.setArt(checkbox_art.isChecked());

                db.insertPrefrences(prefferncesDTO);


               /* Toast.makeText(SearchActivity.this, result.toString()
                        , Toast.LENGTH_LONG).show();
*/

            }


        });
    }


    public void onClickSavePreferences(View v) {
        if (v.getId() == R.id.BsavePreferences) {
            /*Calendar c = Calendar.getInstance();
            int year = c.get(c.YEAR);
            int month = c.get(c.MONTH);
            int dayOfMonth = c.get(c.DAY_OF_MONTH);*/
            art = checkbox_art.isChecked();
            history = checkbox_history.isChecked();
            culture = checkbox_culture.isChecked();
            sport = checkbox_sport.isChecked();
            concert = checkbox_concert.isChecked();
            party = checkbox_party.isChecked();

            final String android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);


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


            System.out.println( prefferncesDTO.toString());

        }

    }
    public static void postPreferences(Context context, final PreferencesDTO prefDTO){

      //  mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://192.168.1.8:8080/post/pref", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // mPostCommentResponse.requestCompleted();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // mPostCommentResponse.requestEndedWithError(error);
            }
        }){


            // hier das prefDTO zerlegen und mittels Params uebergeben---muss n string sein ?
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();


                params.put("userID",String.valueOf(prefDTO.getAndroidID()));

                params.put("culture",String.valueOf(prefDTO.isCulture()));
                params.put("art",String.valueOf(prefDTO.isArt()));
                params.put("history",String.valueOf(prefDTO.isHistory()));
                params.put("party",String.valueOf(prefDTO.isParty()));
                params.put("concert",String.valueOf(prefDTO.isConcert()));
                params.put("sport",String.valueOf(prefDTO.isSport()));

                params.put("stathour",String.valueOf(prefDTO.getStarthour()));



                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public interface PostPreferencesResponseListener {
        public void requestStarted();
        public void requestCompleted();
        public void requestEndedWithError(VolleyError error);
    }






    public void onClicksubmitPreferences(View v) {


        if (v.getId() == R.id.BsubmittPrefs) {

            // Instantiate (bzw gibt sie zurueck) the JacksonNetwork  Singleton-RequestQueue.
            RequestQueue queue = SingletonRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();


            String url = "http://192.168.1.8:8080/onetag";
            // String url = "http://www.google.com";
            //String url = "http://192.168.2.106:8080/onelocation";
            //RequestQueue queue = JacksonNetwork.newRequestQueue(this);
           // mTextView = (TextView) findViewById(R.id.TVqueueanswer1);


            //  jacksonRequestObject incl dazugehoeriger callbackfunction erzeugen

            JacksonRequest<TagDTO> MyJacksonRequestObject =
                    new JacksonRequest<>(Request.Method.GET,
                            url,
                            new JacksonRequestListener<TagDTO>() {
                                @Override
                                public void onResponse(TagDTO response, int statusCode, VolleyError error) {
                                    if (response != null) {
                                        Log.d("TAG", "Response is : " + response.getTagName() + response.getID());

                                    } else {
                                        Log.e("TAG", "An error occurred while parsing the data! Stack trace follows:");
                                        error.printStackTrace();
                                    }
                                }

                                @Override
                                public JavaType getReturnType() {
                                    return SimpleType.construct(TagDTO.class);
                                }
                            });

            // jacksonRequestObject der Singelton-RequestQueue uebergeben
            SingletonRequestQueue.getInstance(this.getApplication()).addToRequestQueue(MyJacksonRequestObject);


        }


    }



    public void onClickReadDB(View v) {

        if (v.getId() == R.id.BreadPrefFromDB) {
            int result = db.givePrefrences();
            Toast temp = Toast.makeText(SearchActivity.this, "Das Ergebniss ist :" + result + "!!", Toast.LENGTH_SHORT);
            temp.show();

        }

    }



   /* private void setAttributes()
    {

        checkbox_culture = (CheckBox) findViewById(R.id.checkbox_culture);
        checkbox_history = (CheckBox) findViewById(R.id.checkbox_history);
        checkbox_art = (CheckBox) findViewById(R.id.checkbox_art);
        checkbox_sport = (CheckBox) findViewById(R.id.checkbox_sport);
        checkbox_concert = (CheckBox) findViewById(R.id.checkbox_concert);
        checkbox_party = (CheckBox) findViewById(R.id.checkbox_party);


        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonClear = (Button) findViewById(R.id.button_clear_list);
        tableLayout = (TableLayout) findViewById(R.id.table);
        headingID = (TextView) findViewById(R.id.heading_id);
        headingBreed = (TextView) findViewById(R.id.heading_breed);
        editID = (EditText) findViewById(R.id.edit_id);
        editBreed = (EditText) findViewById(R.id.edit_breed);
        cowCount = (TextView) findViewById(R.id.textview_count_cows);
    }

    private void setUITexts()
    {
        buttonAdd.setText(getString(R.string.button_add));
        buttonDelete.setText(R.string.button_delete);
        buttonClear.setText(R.string.button_clear);
        headingID.setText(R.string.heading_id);
        headingBreed.setText(R.string.heading_breed);
    }

    private void setClickListeners()
    {
        buttonAdd.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v == buttonAdd)
        {
            addEntry();
        }
        else if(v == buttonDelete)
        {
            clearInputFields();
        }
        else if (v == buttonClear)
        {
            clearList();
        }
    }
*/

}














