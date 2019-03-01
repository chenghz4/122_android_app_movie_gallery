package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BlueActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

    }

    public void connectToTomcat(View view) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        Intent goToIntent = new Intent(this, GreenActivity.class);
        String title=((EditText) findViewById(R.id.blue_2_red_message)).getText().toString();
        String page="1";


        final StringRequest SearchRequest = new StringRequest(Request.Method.GET,
                "https://10.0.2.2:8443/api/stars" +
                        "?id="+title+"&year=&director=&star=&page="+page+"&number=20" +
                        "&sort=a.rating%20desc&genres=&letters=",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("result.success", response);
                        ((TextView) findViewById(R.id.http_response3)).setText(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("result.error", error.toString());
                    }
                }
        );

        // !important: queue.add is where the login request is actually sent
        queue.add(SearchRequest);

    }



}
