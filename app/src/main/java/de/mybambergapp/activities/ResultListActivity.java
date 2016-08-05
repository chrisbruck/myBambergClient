package de.mybambergapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import de.mybambergapp.R;
import de.mybambergapp.dto.Event;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.manager.RepositoryImpl;

/**
 * Created by christian on 19.05.16.
 */
public class ResultListActivity extends AppCompatActivity {

private TableLayout tableLayout;
    List<Event>events;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);
        tableLayout= (TableLayout) findViewById(R.id.table);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        RouteDTO routeDTO = new RouteDTO();
        RepositoryImpl repository = new RepositoryImpl();
        routeDTO = repository.getRouteDTO(this);
        events= routeDTO.getEventList();
        setEventList(routeDTO);




    }

    private void setEventList(RouteDTO routeDTO){
      //  TableLayout table = (TableLayout)findViewById(R.id.the_table);
        List<Event> eventList=  selectionSortByStartTime(routeDTO.getEventList());
        for(int i=0; i<eventList.size(); i++){
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);
            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());
            row.setId(eventList.get(i).getId().intValue());

          //  ((TextView) row.findViewById(R.id.text_tags)).setText("" + eventList.get(i).getTaglist().toString());
       // TextView tv = new TextView(this);
      //  tv.setGravity(Gravity.LEFT);
       // tv.setText(eventList.get(i).getEventname()+"| |fängt an:"+eventList.get(i).getStartdate().toString());
       // tv.setText(eventList.get(i).getStartdate());
      //  row.addView(tv);
        tableLayout.addView(row);
        }
    }

    private List<Event> selectionSortByStartTime(List<Event> events){
        Event[]eventarray= new Event[events.size()];
        events.toArray(eventarray);
            for (int i = 0; i < events.size() - 1; i++) {
                for (int j = i + 1; j < events.size(); j++) {
                    if ((eventarray[i].getStartdate()).after( eventarray[j].getStartdate())){
                        Event temp = eventarray[i];
                        eventarray[i] = eventarray[j];
                        eventarray[j] = temp;
                    }
                }
            }
             events=  Arrays.asList(eventarray);
            return events;
    }


    public void startMapView(View v){
        int id=v.getId();
       // Event toDisplay=  events.get(id);
        Log.d("raw-intent-fun", "id ist: "+id+ " !"+ "Adresse ist :"+ events.get(id).getLocation().getLocationaddress());

        Intent i= new Intent(this, MapsActivity.class);
        String address=events.get(id).getLocation().getLocationaddress();

        i.putExtra("address",address);

        startActivity(i);

     // System.out.println(  toDisplay.getEventname().toString());
    }

    }

