package de.mybambergapp.activities;

import com.android.volley.VolleyError;

/**
 * Created by christian on 07.06.16.
 */
public interface PostPreferencesresponseListener {


        public void requestStarted();
        public void requestCompleted();
        public void requestEndedWithError(VolleyError error);
    }

