package de.mybambergapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.mybambergapp.R;
import de.mybambergapp.dto.Event;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.manager.Repository;
import de.mybambergapp.manager.RepositoryImpl;

/**
 * Created by christian on 10.08.16.
 */
public class FinalListActivity extends AppCompatActivity{
    private TableLayout tableLayout;
    List<Event> events;
    List<Event> myEvents;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finallist);
        tableLayout = (TableLayout) findViewById(R.id.table);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        if (i.getStringExtra("id") != null) {

            int ide = Integer.valueOf(id);
  /*      RouteDTO routeDTO = new RouteDTO();
        RepositoryImpl repository = new RepositoryImpl();
        routeDTO = repository.getRouteDTO(this);
        events = routeDTO.getEventList();*/
            setEventToFinalList(ide);


        }
    }
    private void setEventToFinalList(int i) {
        RepositoryImpl repository = new RepositoryImpl();
        RouteDTO routeDTO= repository.getRouteDTO(this);
        List<Event> eventList = routeDTO.getEventList();


            TableRow row = (TableRow) View.inflate(this, R.layout.table_row, null);
            ((TextView) row.findViewById(R.id.text_veranstaltung)).setText("" + eventList.get(i).getEventname());
            ((TextView) row.findViewById(R.id.text_zeit)).setText("" + eventList.get(i).getStartdate().toString());
            row.setId(eventList.get(i).getId().intValue());
            TableRow row1 = (TableRow) View.inflate(this, R.layout.table_row_line, null);
            tableLayout.addView(row);
            tableLayout.addView(row1);
        }






    private void colorEvent(int i){
        Toast toast = new Toast(this);
        toast.makeText(this, "intent" + i+ "angekommen", Toast.LENGTH_LONG).show();
    }
}
