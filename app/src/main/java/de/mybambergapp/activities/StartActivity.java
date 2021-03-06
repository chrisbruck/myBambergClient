package de.mybambergapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import de.mybambergapp.R;
import de.mybambergapp.manager.RequestManager;

public class StartActivity extends AppCompatActivity {

    ImageView startpicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        startpicture= (ImageView)findViewById(R.id.startImageView);
        setSupportActionBar(toolbar);
       // setPicture();
        loadImgae();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

   private void loadImgae(){
    Picasso.with(this)
            .load(ResultListActivity.urlLocal+"bamberg_4.jpg")
            .into(startpicture);
}


    public void changingActivity(View view){
       // Toast.makeText(this,getResources().getDisplayMetrics().toString(),Toast.LENGTH_LONG).show();

       Intent i = new Intent(StartActivity.this, SearchActivity.class);
        startActivity(i);



    }

    public void onClickToFinalList(View view){
        // Toast.makeText(this,getResources().getDisplayMetrics().toString(),Toast.LENGTH_LONG).show();

        Intent i = new Intent(StartActivity.this, FinalListActivity.class);
        startActivity(i);



    }






/*
    private void setPicture(){
        startpicture.setImageResource(R.drawable.bamberg_night);

    }
*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
