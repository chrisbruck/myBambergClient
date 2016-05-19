package de.mybambergapp.manager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonNetwork;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;


import de.mybambergapp.R;
import de.mybambergapp.dto.PreferencesDTO;
import de.mybambergapp.dto.TagDTO;

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

            // Get a RequestQueue
            RequestQueue queue = SingletonRequestQueue.getInstance(this.getApplicationContext()).
                    getRequestQueue();

            //RequestQueue queue = Volley.newRequestQueue(this);
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
           // queue.add(stringRequest);
            // Add a request (in this example, called stringRequest) to your RequestQueue.
            SingletonRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    public void updateLocations(View v) {
        if (v.getId() == R.id.BupdateLocations) {

            // Instantiate the RequestQueue.
            String url = "http://192.168.2.108:8080/onetag";
            // String url = "http://www.google.com";
            //String url = "http://192.168.2.106:8080/onelocation";
            RequestQueue queue = JacksonNetwork.newRequestQueue(this);
            mTextView = (TextView) findViewById(R.id.TVqueueanswer1);
            queue.add(new JacksonRequest<TagDTO>(Request.Method.GET,
                            url,
                            new JacksonRequestListener<TagDTO>() {
                                @Override
                                public void onResponse(TagDTO response, int statusCode, VolleyError error) {
                                    if (response != null) {
                                        Log.d("TAG", "Response " + response.getTagName() + response.getID());
                                    } else {
                                        Log.e("TAG", "An error occurred while parsing the data! Stack trace follows:");
                                        error.printStackTrace();
                                    }
                                }

                                @Override
                                public JavaType getReturnType() {
                                    return SimpleType.construct(TagDTO.class);
                                }
                            })
            );

        }


    }


}
