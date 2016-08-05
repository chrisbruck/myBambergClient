package de.mybambergapp.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import de.mybambergapp.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String location= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i = getIntent();
         location = i.getStringExtra("address");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float floatVar = 3.14E5F;
        LatLng currentLoc = transformStuff(location);
        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(currentLoc).title("Marker of Location"));
       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,floatVar));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));
    }
}
