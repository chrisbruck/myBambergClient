package de.mybambergapp.manager;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.dto.UserDTO;
import de.mybambergapp.exceptions.MyWrongJsonException;

/**
 * Created by christian on 27.07.16.
 */
public class RequestManager {


    //private static final String BASE_URL = "http://192.168.2.102:8080";
    private static final String BASE_URL = "http://192.168.43.12:8080";

    private static final String USER_URL = BASE_URL+"/v1/user";

    private static final String ROUTE_URL = BASE_URL+"/v1/route?androidId=";





    public static void getRoute(final Context context, final String androidId){
        String url = ROUTE_URL+androidId;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try {
                    RouteDTO route = mapper.readValue(response, RouteDTO.class);
                    Log.d("TAG",route.getEventList().toString());
                    RepositoryImpl myRepo = new RepositoryImpl();
                    myRepo.saveRouteDTO(route,context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG","Error:" + error.toString());
            }
        }) ;
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
                        Log.d("TAG","Response: " + response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","Error:" +  error.toString());
                    }
                });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
    }




    @NonNull
    private static JSONObject getJsonObject(UserDTO userDTO) throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userDTO);
        return new JSONObject(requestBody);
    }
}
