package de.mybambergapp;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;




import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by christian on 01.05.16.
 */
public class RequestManager extends Activity {

    DatabaseManager dbmanager = new DatabaseManager(this);
    private TextView mTextView;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_request);



    }


    public void doStuff(View v) {
        if (v.getId() == R.id.BstarteEndlich) {
            // Instantiate the RequestQueue.
            String url = "http://www.bild.de";
            // String url = "http://www.google.com";
            //String url = "http://192.168.2.106:8080/onelocation";
            RequestQueue queue = Volley.newRequestQueue(this);
            mTextView = (TextView) findViewById(R.id.TVqueueanswer1);


            // Request a string response from the provided URL.= Anfrage besteht aus: GET,  url,callbackmetheode
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    //ResponseListener= Callback interface for delivering parsed responses.
                    new Response.Listener<String>() {
                        @Override
                        //Called when a response is received= callbackmethode fuer wenns geklappt hat
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            mTextView.setText("Response is: " + response.substring(0, 100));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mTextView.setText("That didn't work!");
                }
            });
                        // Add the request to the RequestQueue. Calling add(Request) will enqueue the given Request for dispatch,
                        // resolving from either cache or network on a worker thread, and then delivering a parsed response on the main thread.
            queue.add(stringRequest);
        }
    }


    public void updateLocations(View v) {
        if (v.getId() == R.id.BupdateLocations) {

           // String url = "http://192.168.2.106:8080/alllocations";
            String url = "http://192.168.2.106:8080/onetag";
          //String url=  "http://jsonplaceholder.typicode.com/posts/1";
            RequestQueue queue = Volley.newRequestQueue(this);
            mTextView = (TextView) findViewById(R.id.TVqueueanswer1);

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            // mTextView.setText("Response is: " + response.toString());
                            //Log.d("Response", response.toString());
                            try {
                                    JSONObject lasttag = response.getJSONObject("TagDTO");
                                ObjectMapper mapper = new ObjectMapper();


                          //  TagDTO  tagDTO = mapper.readValue(lasttag,TagDTO.class);
                               //String test = lastlocation.get("locationname").toString();
                              //  dbmanager.insertTags();


                            }catch(JSONException e){
                                e.printStackTrace();

                            } /*catch (JsonMappingException e) {
                                e.printStackTrace();
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mTextView.setText("Tut nicht worken leider!");
                            // Log.d("Error.Response", response);
                        }
                    }
            );


            /*int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            getRequest.setRetryPolicy(policy);*/


            queue.add(getRequest);
        }
    }


}
