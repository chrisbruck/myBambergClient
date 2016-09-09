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
import de.mybambergapp.entities.Tag;
import de.mybambergapp.manager.Repository;
import de.mybambergapp.manager.RepositoryImpl;

/**
 * Created by christian on 19.05.16.
 */
public class ResultListActivity extends AppCompatActivity {

    private TableLayout tableLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);
        tableLayout = (TableLayout) findViewById(R.id.table);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        RouteDTO routeDTO = new RouteDTO();
        RepositoryImpl repository = new RepositoryImpl();
        routeDTO = repository.getRouteDTO(this);
        //events = routeDTO.getEventList();
        setEventList(routeDTO);


    }


    private void setEventList(RouteDTO routeDTO) {
        //  TableLayout table = (TableLayout)findViewById(R.id.the_table);
        List<Event> eventList = selectionSortByStartTime(routeDTO.getEventList());
        for (int i = 0; i < eventList.size(); i++) {
            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);

            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());

            ImageView picture = (ImageView) row.findViewById(R.id.ImageView);

            loadImage(eventList.get(i).getPictureURL(), picture);

            row.setId(eventList.get(i).getId().intValue());
            // TableRow row1 = (TableRow) View.inflate(this, R.layout.table_row_line, null);
            tableLayout.addView(row);
            // tableLayout.addView(row1);
        }
    }

    private void loadImage(String url, ImageView view) {
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



/*    private String concatString(List<Tag> tagList){
        String toReturn= null;
        for (Tag tag:tagList
             ) {
        toReturn=    toReturn+","+tag;
        }


        return toReturn;

    }*/

    public void startMapView(View v) {
        int id = v.getId();
        String lastaddress = getLastAddress();
        //  String taglist = " gratis, familienfreundlich, supergeil";
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO routeDTO = repository.getRouteDTO(this);
        List<Event> events = routeDTO.getEventList();
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getId() == id) {
                Location l = e.getLocation();

                String eventname = e.getEventname();
                String description = e.getDescription();
                //String description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
                String startdate = e.getStartdate().toString();
                List<Tag> tagList = e.getTaglist();

                //  String tags = concatString(tagList);
                String tags = " gratis, geil, draussen, einmalig ";
                String address = l.getLocationaddress();


                // String address =  events.get(id).getLocation().getLocationaddress();
                // Log.d("raw-intent-fun", "id ist: "+id+ " !"+ "Adresse ist :"+ events.get(id).getLocation().getLocationaddress());

                Intent j = new Intent(this, MapsActivity.class);


                j.putExtra("id", String.valueOf(id));
                j.putExtra("address", address);
                j.putExtra("eventname", eventname);
                j.putExtra("description", description);
                j.putExtra("startdate", startdate);

                j.putExtra("tags", tags);

                j.putExtra("lastaddress", lastaddress);
                j.putExtra("taglist", tags);

                startActivity(j);

            }
        }

    }


    private String getLastAddress() {
        String answer = "Bamberg Ludwigstraße 2";
        /*RepositoryImpl myrepo = new RepositoryImpl();
        RouteDTO myRoute = new RouteDTO();

        try {
            myRoute = myrepo.getFinalRouteDTO(this);

        } catch (
                IllegalStateException
                        e) {
            e.getMessage();

        }

        int last = myRoute.getEventList().size();
        if (last != 0) {
            answer = myRoute.getEventList().get(last - 1).getLocation().getLocationaddress();
        } else {

        }*/
        return answer;

    }

}



