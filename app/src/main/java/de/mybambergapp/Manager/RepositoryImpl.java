package de.mybambergapp.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.mybambergapp.dto.Event;
import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.dto.UserDTO;

/**
 * Created by christian on 23.07.16.
 */
public class RepositoryImpl implements Repository {


    public static final String FINAL_ROUTE = "finalRoute";

    @Override
    public void saveRouteDTO(RouteDTO routeDTO, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            String events = getString(routeDTO);
            editor.putString("actualRoute", events);
            editor.apply();
            editor.commit();
        } catch (JsonProcessingException e) {
            e.getOriginalMessage();
        }
    }

    @Override
    public RouteDTO getRouteDTO(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);



        String events = sharedPreferences.getString("actualRoute", null);

        try {
            RouteDTO routeDTO1 = getRouteDTO(events);


            return routeDTO1;
        } catch (java.io.IOException e) {
            e.getMessage();
        }
        return null;
    }

    public void saveFinalRouteDTO(RouteDTO routeDTO,Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            String events = getString(routeDTO);
            editor.putString(FINAL_ROUTE, events);
            editor.apply();
            editor.commit();
        } catch (JsonProcessingException e) {
            e.getOriginalMessage();
        }

    }

    // hier entweder null oder ein gefülltes route dto. darf nix reinschreiben. nur liefern
    public RouteDTO getFinalRouteDTO(Context context) throws IOException {
        RouteDTO routeDTO = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        String events = sharedPreferences.getString(FINAL_ROUTE, null);

        if(events==null)
            return null;

        return getRouteDTO(events);

        }






    @Override
    public void addEventToRoute(RouteDTO routeDTO) {

    }

    @Override
    public void removeEventFromRoute(RouteDTO routeDTO) {

    }

    private static String getString(RouteDTO routeDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String route = objectMapper.writeValueAsString(routeDTO);
        return route;
    }

    private static RouteDTO getRouteDTO(String route) throws java.io.IOException {
        RouteDTO routeDTO = null;
        ObjectMapper mapper = new ObjectMapper();
        routeDTO = mapper.readValue(route, RouteDTO.class);


        return routeDTO;
    }
}
