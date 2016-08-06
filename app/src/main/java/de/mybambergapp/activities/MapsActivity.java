package de.mybambergapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mybambergapp.R;
import de.mybambergapp.manager.RequestManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    String location = null;
    String eventname= null;
    String eventdescription= null;
    String startDate= null;
    String lastaddress= null;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i = getIntent();

        location = i.getStringExtra("address");
        eventname= i.getStringExtra("eventname");
        eventdescription= i.getStringExtra("description");
        startDate= i.getStringExtra("startdate");
        lastaddress= i.getStringExtra("lastaddress");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.   ++++  1   +++++
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //A GoogleMap must be acquired using getMapAsync(OnMapReadyCallback). This class automatically initializes the maps system and the view.  ++++  2   ++++++
        mapFragment.getMapAsync(this);
        displayDetails();






    }





private void displayDetails(){

    TextView textViewName = (TextView) findViewById(R.id.eventname);
    textViewName.setText(eventname);

   // TextView textViewDescription = (TextView) findViewById(R.id.eventdescription);
   // textViewDescription.setText(eventdescription);

    TextView textViewAddress = (TextView) findViewById(R.id.eventaddress);

    textViewAddress.setText(location);

    TextView textViewDate = (TextView) findViewById(R.id.eventstartdate);
    textViewDate.setText(startDate);




}
    private void drawPrimaryLinePath(ArrayList<LatLng> listLocsToDraw, GoogleMap map )
    {
        if ( map == null )
        {
            return;
        }
        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }
        PolylineOptions options = new PolylineOptions();
        options.color( Color.parseColor( "#CC0000FF" ) );
        options.width( 5 );
        options.visible( true );
        for ( LatLng locRecorded : listLocsToDraw )
        {
            options.add( locRecorded );
        }
        map.addPolyline( options );
    }


    private LatLng transformStuff(String location) {
        LatLng latLng = null;
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            // Geocoding is the process of transforming a street address or other description of a location into a (latitude, longitude) coordinate & umgekehrt
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // den letzten Eintrag rausholen und die Koordinaten rausholen
            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
        }
        return latLng;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.   ++++++  3  +++++++
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float floatVar = 16;
        LatLng currentLoc = transformStuff(location);
        LatLng lastLoc = transformStuff(lastaddress);
       List <LatLng> latLngs= RequestManager.getPath(this,currentLoc,lastLoc);

      // ArrayList <LatLng> latLngArrayList = new ArrayList<>();

        ArrayList<LatLng> latLngArrayList = new ArrayList<>(latLngs);
        //latLngArrayList.add(0,currentLoc);
        //latLngArrayList.add(1,lastLoc);

        drawPrimaryLinePath(latLngArrayList,mMap);



      Marker lastPlace= mMap.addMarker(new MarkerOptions().position(lastLoc).title("Letzter Ort").snippet("Letzter Ort"));
        lastPlace.showInfoWindow();
        Marker nextPlace= mMap.addMarker(new MarkerOptions().position(currentLoc).title("Nächster Ort").snippet("Nächster Ort"));
        nextPlace.showInfoWindow();

        // setzt den Zoom so das beide Marker sichtbar sind

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(currentLoc)
                .include(lastLoc).build();

        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, floatVar));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));







    }
}
