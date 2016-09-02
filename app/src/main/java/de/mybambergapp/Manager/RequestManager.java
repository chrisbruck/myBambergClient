package de.mybambergapp.manager;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import de.mybambergapp.R;
import de.mybambergapp.activities.MapsActivity;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.dto.UserDTO;
import de.mybambergapp.exceptions.MyWrongJsonException;

/**
 * Created by christian on 27.07.16.
 */
public class RequestManager {

   // private  static final String BASE_URL = "http://10.1.6.223:8080";
    private static final String BASE_URL = "http://192.168.2.102:8080";
    //private static final String BASE_URL = "http://192.168.43.12:8080";
    private static final String USER_URL = BASE_URL + "/v1/user";
    private static final String ROUTE_URL = BASE_URL + "/v1/route?androidId=";
    private static final String FINALROUTE_URL= BASE_URL+"/v1/finalroute";
    private static final String UPDATE_URL=BASE_URL+"/v1/updateroute?androidId=";

    public static String distance;
    public static String duration;

    public static void getPath(Context context, final LatLng origin, final LatLng dest, final GoogleMap googleMap, final TextView textview) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String walking= "&mode=walking";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+walking;
        //String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("TAG", "response :" + response.toString());
                List<LatLng>   latLngs=   parseResponse(response.toString(),textview);
                latLngs.add(0,origin);
                ArrayList<LatLng> latLngArrayList = new ArrayList<>(latLngs);
                drawPrimaryLinePath(latLngArrayList,googleMap);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("APP", "Error: " + error.getMessage());
         /*       Toast.makeText(this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();*/
                // hide the progress dialog
                // hideDialog();
            }
        });
        // Adding request to request queue
        SingletonRequestQueue.getInstance(context).addToRequestQueue(jsonObjReq);

    }
    private static void drawPrimaryLinePath(ArrayList<LatLng> listLocsToDraw, GoogleMap map) {
        if (map == null) {
            return;
        }
        if (listLocsToDraw.size() < 2) {
            return;
        }
        PolylineOptions options = new PolylineOptions();
        options.color(Color.parseColor("#CC0000FF"));
        options.width(5);
        options.visible(true);
        for (LatLng locRecorded : listLocsToDraw) {
            options.add(locRecorded);
        }
        map.addPolyline(options);
    }

    @Nullable
    public static List<LatLng> parseResponse(String responseString,TextView textView) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<LatLng> resultList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseString);
            JsonNode routesNode = rootNode.get("routes");
            JsonNode legsNode = routesNode.get(0);
            JsonNode legsDeepNode = legsNode.get("legs");
            JsonNode somethingNode = legsDeepNode.get(0);

         distance= String.valueOf(somethingNode.get("distance").get("text"));
         duration=    String.valueOf(somethingNode.get("duration").get("text"));

            textView.setText("Entfernung :"+distance+"     Dauer :"+duration);


            JsonNode stepsArray = somethingNode.get("steps");
            Iterator iteratorListArray = stepsArray.iterator();

            while (iteratorListArray.hasNext()) {
                JsonNode nextNode = (JsonNode) iteratorListArray.next();
                JsonNode endLocation = nextNode.path("end_location");
                Double lat = endLocation.path("lat").asDouble();
                Double lng = endLocation.path("lng").asDouble();
                LatLng latLng = new LatLng(lat, lng);
                resultList.add(latLng);
            }
            Log.d("TAG", "ResultList :" + resultList.size());
            return resultList;
        } catch (IOException e) {
            e.getMessage();
        }
        return resultList;
    }

    public static void getRoute(final Context context, final String androidId) {
        String url = ROUTE_URL + androidId;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try {
                    RouteDTO route = mapper.readValue(response, RouteDTO.class);
                    Log.d("TAG", route.getEventList().toString());
                    RepositoryImpl myRepo = new RepositoryImpl();
                    myRepo.saveRouteDTO(route, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error:" + error.toString());
            }
        });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);
    }


    public static void postUser(Context context, UserDTO userDTO) throws MyWrongJsonException {
        JSONObject jsonObject = null;
        try {
            jsonObject = getJsonObject(userDTO);
        } catch (JsonProcessingException e) {
            throw new MyWrongJsonException(e.getMessage());
        } catch (JSONException e) {
            throw new MyWrongJsonException(e.getMessage());
        }
        JsonRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, USER_URL, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "Response: " + response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Error:" + error.toString());
                    }
                });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public static void postFinalRoute(Context context, RouteDTO routeDTO) throws MyWrongJsonException{
        JSONObject jsonObject = null;
        try {
            jsonObject = getJsonObject(routeDTO);
        } catch (JsonProcessingException e) {
            throw new MyWrongJsonException(e.getMessage());
        } catch (JSONException e) {
            throw new MyWrongJsonException(e.getMessage());
        }
        JsonRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, FINALROUTE_URL, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "Response: " + response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Error:" + error.toString());
                    }
                });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);


    }
    //Annotieren mit timescheduler, diese methode soll nur checken ob die events noch machbar sind
    public static  void updateRoute(final Context context,final String androidId){
        String url = UPDATE_URL + androidId;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try {
                    RouteDTO route = mapper.readValue(response, RouteDTO.class);
                    Log.d("TAG", route.getEventList().toString());
                    RepositoryImpl myRepo = new RepositoryImpl();
                    myRepo.saveFinalRouteDTO(route, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error:" + error.toString());
            }
        });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }


    @NonNull
    private static JSONObject getJsonObject(UserDTO userDTO) throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userDTO);
        return new JSONObject(requestBody);// Creates a new JSONObject with name/value mappings from the JSON string.

    }
    @NonNull
    private static JSONObject getJsonObject(RouteDTO routeDTO) throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(routeDTO);
        return new JSONObject(requestBody);
    }
}
