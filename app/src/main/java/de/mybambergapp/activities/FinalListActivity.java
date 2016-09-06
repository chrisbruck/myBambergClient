package de.mybambergapp.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mybambergapp.R;
import de.mybambergapp.dto.Event;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.entities.Location;
import de.mybambergapp.exceptions.MyWrongJsonException;
import de.mybambergapp.manager.Repository;
import de.mybambergapp.manager.RepositoryImpl;
import de.mybambergapp.manager.RequestManager;

/**
 * Created by christian on 10.08.16.
 */
public class FinalListActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    List<Event> events;
    List<Event> myEvents;
    private int resultlist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finallist);
        tableLayout = (TableLayout) findViewById(R.id.table);
        Intent i = getIntent();
        String id = i.getStringExtra("id");
        if (i.getStringExtra("id") != null) {
            int idEvent = Integer.valueOf(id);
            setEventToFinalList(idEvent);
            setFinalEventList();
        }else{
            setFinalEventList();
        }
    }


    private void fillPref() {
        List<Event> eventList = new ArrayList<>();
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setEventList(eventList);
        routeDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
       // routeDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        RepositoryImpl repository = new RepositoryImpl();
        repository.saveFinalRouteDTO(routeDTO, this);
    }


    private void setFinalEventList() {
        //  TableLayout table = (TableLayout)findViewById(R.id.the_table);
        RepositoryImpl repository = new RepositoryImpl();
        List<Event> eventList = (repository.getFinalRouteDTO(this).getEventList());
        for (int i = 0; i < eventList.size(); i++) {
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);
            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());

          //  ImageView picture= (ImageView)row.findViewById(R.id.ImageView);

           // loadImage(eventList.get(i).getPictureURL(),picture);


            if(i%2==0 ){


            int grey = R.color.grey;
            row.setBackgroundColor(getResources().getColor(grey));
            }


            row.setId(eventList.get(i).getId().intValue());

         //   TableRow row1 = (TableRow) View.inflate(this, R.layout.table_row_line, null);

            tableLayout.addView(row);
          //  tableLayout.addView(row1);
        }
    }



    private void loadImage(String url,ImageView view){
        Picasso.with(this)
                .load(url)
                .into(view);
    }

    private void setFinalEventListUpdate() {


        RepositoryImpl repository = new RepositoryImpl();
        List<Event> eventList = (repository.getFinalRouteDTO(this).getEventList());
        for (int i = 0; i < eventList.size(); i++) {
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row_update, null);
            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());

            if(eventList.get(i).isValid()){
                ((TextView) row.findViewById(R.id.text_valid)).setText("gültig");

            }else{
                ((TextView) row.findViewById(R.id.text_valid)).setText(" NOT !");

            }


            row.setId(eventList.get(i).getId().intValue());
            TableRow row1 = (TableRow) View.inflate(this, R.layout.table_row_line, null);
            tableLayout.addView(row);
            tableLayout.addView(row1);
        }
    }


    private void setEventToFinalList(int j) {
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO routeDTO = repository.getRouteDTO(this);
        RouteDTO myrouteDTO = new RouteDTO();
           myrouteDTO = repository.getFinalRouteDTO(this);
        if(myrouteDTO== null){
            fillPref();
        }
            events = routeDTO.getEventList();
            myEvents = myrouteDTO.getEventList();
            // richtiges event aus der list suchen
            for (int i = 0; i < events.size(); i++) {
                Event e = events.get(i);
                if (e.getId() == j) {
                    // event gefunden---> in die myroute reintun
                    myEvents.add(e);
                    myrouteDTO.setEventList(myEvents);
                    myrouteDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    repository.saveFinalRouteDTO(myrouteDTO, this);
                }
            }
    }

    private Long deleteLastEvent(){
        RepositoryImpl repository = new RepositoryImpl();
      RouteDTO  myrouteDTO = repository.getFinalRouteDTO(this);
      int size =  myrouteDTO.getEventList().size();
          myEvents= myrouteDTO.getEventList();
          Long idEvent=  myEvents.get(size-1).getId();
          myEvents.remove(size-1);
          myrouteDTO.setEventList(myEvents);
          repository.saveFinalRouteDTO(myrouteDTO,this);
          return idEvent;
    }


    public void onClickDeleteEvent(View v){
      Long id =  deleteLastEvent();
       // myEvents= null;
        tableLayout.removeView(findViewById(id.intValue()));
        //setFinalEventList();
    }
    public void onClickToResultList (View v){
        Intent i = new Intent(this, ResultListActivity.class);
        startActivity(i);

    }

    public void onClickPostAndSaveFinalRoute(View v) {
        RequestManager requestManager = new RequestManager();
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO myrouteDTO = repository.getFinalRouteDTO(this);
        try {


            requestManager.postFinalRoute(this, myrouteDTO);
        }catch (MyWrongJsonException e){
            e.getMessage();
        }

    }


    public void onClickUpdateRouteValidity(View v){
        RequestManager requestManager = new RequestManager();
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO myrouteDTO = repository.getFinalRouteDTO(this);

            requestManager.updateRoute(this, myrouteDTO.getAndroidId());

        for(int i =0;  i < myrouteDTO.getEventList().size(); i++){

            Long id = myrouteDTO.getEventList().get(i).getId();
            tableLayout.removeView(findViewById(id.intValue()));

        }

           setFinalEventListUpdate();

        }

    }

