package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class RedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);
        Bundle bundle = getIntent().getExtras();
    }


    public void connectToTomcat(View view) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        Intent goToIntent = new Intent(this, BlueActivity.class);


        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://10.0.2.2:8443/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("login.success", response);
                        ((TextView) findViewById(R.id.http_response)).setText(response);
                        if(response.contains("success")){


                            goToIntent.putExtra("last_activity", "red");
                            goToIntent.putExtra("message", "I love you");
                            startActivity(goToIntent);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("login.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // Post request form data
                final Map<String, String> params = new HashMap<String, String>();
                String user=((EditText) findViewById(R.id.red_2_blue_message)).getText().toString();
                String password=((EditText) findViewById(R.id.red_2_green_message)).getText().toString();
                params.put("username", user);
                params.put("password", password);

                return params;
            }
        };

        // !important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }
}
