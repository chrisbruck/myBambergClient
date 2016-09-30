package de.mybambergapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mybambergapp.R;
import de.mybambergapp.dto.Event;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.exceptions.MyWrongJsonException;
import de.mybambergapp.manager.RepositoryImpl;
import de.mybambergapp.manager.RequestManager;

/**
 * Created by christian on 10.08.16.
 */
public class FinalListActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    List<Event> events;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finallist);
        tableLayout = (TableLayout) findViewById(R.id.table);
        Intent i = getIntent();
        String id = i.getStringExtra("id");
        if (i.getStringExtra("id") != null) {
            int idEvent = Integer.valueOf(id);
            try {
                setEventToFinalList(idEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            addEventToView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void fillPref() {
        List<Event> eventList = new ArrayList<>();
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setEventList(eventList);
        routeDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        RepositoryImpl repository = new RepositoryImpl();
        repository.saveFinalRouteDTO(routeDTO, this);
    }


    private void addEventToView() throws IOException {
        RepositoryImpl repository = new RepositoryImpl();
        List<Event> eventList = (repository.getFinalRouteDTO(this).getEventList());
        for (int i = 0; i < eventList.size(); i++) {
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);
            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());

           ImageView picture= (ImageView)row.findViewById(R.id.ImageView);

            loadImage(eventList.get(i).getPictureURL(),picture);

            row.setId(eventList.get(i).getId().intValue());

            tableLayout.addView(row);

        }
    }

    private void loadImage(String url,ImageView view){
        Picasso.with(this)
                .load(ResultListActivity.urlLocal+url)
                .into(view);
    }

    private void setFinalEventListUpdate() throws IOException {

        RepositoryImpl repository = new RepositoryImpl();
        List<Event> eventList = (repository.getFinalRouteDTO(this).getEventList());
        for (int i = 0; i < eventList.size(); i++) {
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);
            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());

            ImageView picture= (ImageView)row.findViewById(R.id.ImageView);
            loadImage(eventList.get(i).getPictureURL(),picture);
            int myColor;
            if(eventList.get(i).isValid()){
             myColor = R.color.green;
                ((TextView) row.findViewById(R.id.text_valid)).setText("GÜLTIG ! ");
                ((TextView) row.findViewById(R.id.text_valid)).setBackgroundColor(getResources().getColor(myColor));


           }else{
                myColor= R.color.red;
                ((TextView) row.findViewById(R.id.text_valid)).setText(" NICHT GÜLTIG !");
                ((TextView) row.findViewById(R.id.text_valid)).setBackgroundColor(getResources().getColor(myColor));

            }

           // row.setBackgroundColor(getResources().getColor(myColor));
            row.setId(eventList.get(i).getId().intValue());
           //TableRow row1 = (TableRow) View.inflate(this, R.layout.table_row_line, null);
            tableLayout.addView(row);
           // tableLayout.addView(row1);
        }
    }


    private void setEventToFinalList(int eventIdToFinalList) throws IOException {
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO routeDTO = repository.getRouteDTO(this);

        RouteDTO   myrouteDTO = repository.getFinalRouteDTO(this);

        if(myrouteDTO== null){
            fillPref();
        }
            events = routeDTO.getEventList();
            List<Event> eventList = routeDTO.getEventList();
            // richtiges event aus der list suchen

             for(Event event:eventList)   {
                 if (event.getId() == eventIdToFinalList) {
                    // event gefunden---> in die myroute reintun
                    //eventList.add(event);
                     List<Event> myRouteEventList = myrouteDTO.getEventList();
                     if(myRouteEventList == null){
                         myRouteEventList = new ArrayList<Event>();
                         myrouteDTO.setEventList(myRouteEventList);
                     }
                     myRouteEventList.add(event);


                    myrouteDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    repository.saveFinalRouteDTO(myrouteDTO, this);
                }
            }
    }

    private Long deleteLastEvent() throws IOException {
        RepositoryImpl repository = new RepositoryImpl();
      RouteDTO  myrouteDTO = repository.getFinalRouteDTO(this);
      int size =  myrouteDTO.getEventList().size();
          List<Event> eventList= myrouteDTO.getEventList();
          Long idEvent=  eventList.get(size-1).getId();
          eventList.remove(size-1);
          myrouteDTO.setEventList(eventList);
          repository.saveFinalRouteDTO(myrouteDTO,this);
          return idEvent;
    }


    public void onClickDeleteEvent(View v) throws IOException {
      Long id =  deleteLastEvent();
       // myEvents= null;
        tableLayout.removeView(findViewById(id.intValue()));
        //addEventToView();
    }
    public void onClickToResultList (View v){
        Intent i = new Intent(this, ResultListActivity.class);
        startActivity(i);

    }

    public void onClickPostAndSaveFinalRoute(View v) throws IOException {
        RequestManager requestManager = new RequestManager();
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO myrouteDTO = repository.getFinalRouteDTO(this);
        myrouteDTO.setAndroidId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        try {


            requestManager.postFinalRoute(this, myrouteDTO);
        }catch (MyWrongJsonException e){
            e.getMessage();
        }

    }


    public void onClickUpdateRouteValidity(View v) throws IOException {
        RequestManager requestManager = new RequestManager();
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO myrouteDTO = repository.getFinalRouteDTO(this);

            requestManager.updateRoute(this, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        for(int i =0;  i < myrouteDTO.getEventList().size(); i++){

            Long id = myrouteDTO.getEventList().get(i).getId();
            tableLayout.removeView(findViewById(id.intValue()));

        }

           setFinalEventListUpdate();

        }

    }

