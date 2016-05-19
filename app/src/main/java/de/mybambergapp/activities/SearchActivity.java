package de.mybambergapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import de.mybambergapp.R;
import de.mybambergapp.dto.PreferencesDTO;
import de.mybambergapp.manager.DatabaseManager;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends Activity {


   // final CheckBox checkBoxArt = (CheckBox) findViewById(R.id.checkbox_art);

DatabaseManager db = new DatabaseManager(this);

    private CheckBox checkbox_culture, checkbox_art,checkbox_history;
    private Button BerstelleRoute;
    private  Button BreadPrefFromDB;
    PreferencesDTO preferencesDTO = new PreferencesDTO();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       // addListenerOnArtBox();

        addListenerOnButton();


        }



   /* public void addListenerOnArtBox() {

        checkbox_art = (CheckBox) findViewById(R.id.checkbox_art);

        checkbox_art.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(SearchActivity.this,
                            "Bro, try Art :)", Toast.LENGTH_LONG).show();
                    preferencesDTO.setArt(true);
                }

            }
        });

    }*/



    public void addListenerOnButton() {

        checkbox_culture = (CheckBox) findViewById(R.id.checkbox_culture);
        checkbox_history = (CheckBox) findViewById(R.id.checkbox_history);
        checkbox_art= (CheckBox) findViewById(R.id.checkbox_art);
        BerstelleRoute = (Button) findViewById(R.id.BerstelleRoute);


        BerstelleRoute.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("Art ist ").append((checkbox_art.isChecked()));
                result.append("\nHistory ist ").append((checkbox_history.isChecked()));
                result.append("\nCulture ist ").append((checkbox_culture.isChecked()));

                PreferencesDTO prefferncesDTO = new PreferencesDTO();
                prefferncesDTO.setCulture(checkbox_culture.isChecked());
                prefferncesDTO.setHistory(checkbox_history.isChecked());
                prefferncesDTO.setArt(checkbox_art.isChecked());

                db.insertPrefrences(prefferncesDTO);


                Toast.makeText(SearchActivity.this,result.toString()
                    , Toast.LENGTH_LONG).show();


            }


        });
    }


    public void onClickReadDB(View v){

        if(v.getId()== R.id.BreadPrefFromDB){
           int result= db.givePrefrences();
            Toast temp = Toast.makeText(SearchActivity.this, "Das Ergebniss ist :"+ result+"!!", Toast.LENGTH_SHORT);
            temp.show();

        }

    }

}















