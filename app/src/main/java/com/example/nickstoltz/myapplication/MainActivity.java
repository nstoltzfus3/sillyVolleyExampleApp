package com.example.nickstoltz.myapplication;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import java.util.*;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;

import java.net.URI;

//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hello world App");  // provide compatibility to all the versions

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://mogeste-c1.cloud.rnoc.gatech.edu/";
        final TextView text = (TextView) findViewById(R.id.textView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Responded");
                    // Display the first 500 characters of the response string.
                    text.setText("Response is: "+ response.substring(0,5));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    text.setText("That didn't work!");
                }
            }
            );
            @Override
            public void onClick(View view) {
                text.setText("Getting");
                queue.add(stringRequest);
                System.out.println("Got that stuff");
            }
        });

        Button recordData = (Button) findViewById(R.id.recordData);
        recordData.setOnClickListener(new View.OnClickListener(){
            StringRequest sr = new StringRequest(Request.Method.POST, "http://mogeste-c1.cloud.rnoc.gatech.edu/post_demo",
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("response");
                    text.setText("Response is: "+ response.substring(0,response.length() - 1));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    text.setText("That didn't work!");
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("user", "nickTestUser");
                    params.put("comment", "Whats up");
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            @Override
            public void onClick(View view) {
                text.setText("Posting...");
                queue.add(sr);
                System.out.println("Posted");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
