package de.mybambergapp.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.spothero.volley.JacksonNetwork;

/**
 * Created by christian on 19.05.16.
 */
public class SingletonRequestQueue {
//creating a RequestQueue as a singleton, which makes the RequestQueue last the lifetime of your app.


    private static SingletonRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    //context und die requestqueue als einzige varialblen
    private SingletonRequestQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }


    //Mit synchronized kann man einen bestimmten Codebereich schützen oder auch eine ganze Methode.Hier wird die gesamte Methode exklusiv vergeben
    //wenn es keine Klasse von SRQ wird eine erzeugt ansonsten die existierende zurueckgegeben
    public static synchronized SingletonRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key here (und nicht Activity!), it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            //getApplicationContext= Return the context of the single, global Application object of the current process.
            // This generally should only be used if you need a Context whose lifecycle is separate from the current context
             mRequestQueue = JacksonNetwork.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }







}
