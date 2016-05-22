package de.mybambergapp.activities;

import android.app.Activity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import de.mybambergapp.R;

/**
 * Created by christian on 22.05.16.
 */
public class SearchActivityCalendar extends Activity {


    private static final String TAG = SearchActivityCalendar.class.getSimpleName();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Beginn und Ende eines Termins
        Calendar cal = Calendar.getInstance();
        Date from = cal.getTime();
        cal.add(Calendar.HOUR_OF_DAY,1);
        Date to = cal.getTime();

        //Termin anlegen





    }


}
