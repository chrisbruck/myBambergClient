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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import de.mybambergapp.R;
import de.mybambergapp.dto.Event;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.entities.Location;
import de.mybambergapp.manager.RepositoryImpl;

/**
 * Created by christian on 19.05.16.
 */
public class ResultListActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    List<Event> events;
    List<Event> myEvents;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);
        tableLayout = (TableLayout) findViewById(R.id.table);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        RouteDTO routeDTO = new RouteDTO();
        RepositoryImpl repository = new RepositoryImpl();
        routeDTO = repository.getRouteDTO(this);
        events = routeDTO.getEventList();
        setEventList(routeDTO);


    }



    private void setEventList(RouteDTO routeDTO) {
        //  TableLayout table = (TableLayout)findViewById(R.id.the_table);
        List<Event> eventList = selectionSortByStartTime(routeDTO.getEventList());
        for (int i = 0; i < eventList.size(); i++) {
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);

            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());
             ImageView picture= (ImageView)row.findViewById(R.id.ImageView);

           loadImage(eventList.get(i).getPictureURL(),picture);

            row.setId(eventList.get(i).getId().intValue());
            TableRow row1 = (TableRow) View.inflate(this, R.layout.table_row_line, null);
            tableLayout.addView(row);
            tableLayout.addView(row1);
        }
    }

    private void loadImage(String url,ImageView view){
        Picasso.with(this)
                .load(url)
                .into(view);
    }

    private List<Event> selectionSortByStartTime(List<Event> events) {
        Event[] eventarray = new Event[events.size()];
        events.toArray(eventarray);
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if ((eventarray[i].getStartdate()).after(eventarray[j].getStartdate())) {
                    Event temp = eventarray[i];
                    eventarray[i] = eventarray[j];
                    eventarray[j] = temp;
                }
            }
        }
        events = Arrays.asList(eventarray);
        return events;
    }


    public void startMapView(View v) {
        int id = v.getId();
        String lastaddress = "Bamberg Luitpoldstraße 21";
        String taglist = " gratis, familienfreundlich, supergeil";
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getId() == id) {
                Location l = e.getLocation();

                String eventname = e.getEventname();
                String description = e.getDescription();
                String startdate = e.getStartdate().toString();

                String address = l.getLocationaddress();
                // String address =  events.get(id).getLocation().getLocationaddress();
                // Log.d("raw-intent-fun", "id ist: "+id+ " !"+ "Adresse ist :"+ events.get(id).getLocation().getLocationaddress());

                Intent j = new Intent(this, MapsActivity.class);


                j.putExtra("id",String.valueOf(id));
                j.putExtra("address", address);
                j.putExtra("eventname", eventname);
                j.putExtra("description", description);
                j.putExtra("startdate", startdate);
                j.putExtra("lastaddress", lastaddress);
                // j.putExtra("taglist", taglist);

                startActivity(j);

            }
        }


        // System.out.println(  toDisplay.getEventname().toString());
    }

}

