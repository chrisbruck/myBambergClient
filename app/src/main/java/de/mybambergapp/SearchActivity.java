package de.mybambergapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by christian on 01.05.16.
 */
public class SearchActivity extends Activity{

    String answer;
    final TextView mTextView = (TextView) findViewById(R.id.TVqueueanswer);

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       // String username = getIntent().getStringExtra("Username"); wo kommt der input her ?

        TextView tv = (TextView)findViewById(R.id.TVresults);
        tv.setText(answer);

    }


}
