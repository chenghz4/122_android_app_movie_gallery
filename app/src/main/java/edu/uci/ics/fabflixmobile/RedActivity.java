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
        if (bundle != null) {
            if (bundle.getString("last_activity") != null) {
                Toast.makeText(this, "Last activity was " + bundle.get("last_activity") + ".", Toast.LENGTH_LONG).show();
            }
            String msg = bundle.getString("message");
            if (msg != null && !"".equals(msg)) {
                ((TextView) findViewById(R.id.last_page_msg_container)).setText(msg);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_red, menu);
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

    public void connectToTomcat(View view){

        // Post request form data
        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", "anteater");
        params.put("password", "123456");

        // no user is logged in, so we must connect to the server

        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        // 10.0.2.2 is the host machine when running the android emulator
        final StringRequest afterLoginRequest = new StringRequest(Request.Method.GET, "https://10.0.2.2:8443/project4-login-example/api/username",
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {

                    Log.d("response2", response);
                    ((TextView)findViewById(R.id.http_response)).setText(response);
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("security.error", error.toString());
                }
            }
        );


        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://10.0.2.2:8443/project4-login-example/api/android-login",
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {

                    Log.d("response", response);
                    ((TextView)findViewById(R.id.http_response)).setText(response);
                    // Add the request to the RequestQueue.
                    queue.add(afterLoginRequest);
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("security.error", error.toString());
                }
            }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }  // HTTP POST Form Data
        };

        SafetyNet.getClient(this).verifyWithRecaptcha("your-site-key")
            .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                @Override
                public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                    if (!response.getTokenResult().isEmpty()) {
                        // Add the request to the RequestQueue.
                        params.put("g-recaptcha-response", response.getTokenResult());
                        queue.add(loginRequest);
                    }
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ApiException) {
                        ApiException apiException = (ApiException) e;
                        Log.d("Login", "Error message: " +
                                CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                    } else {
                        Log.d("Login", "Unknown type of error: " + e.getMessage());
                    }
                }
            });

        return ;
    }

    public void goToBlue(View view){
        String msg = ((EditText)findViewById(R.id.red_2_blue_message)).getText().toString();

        Intent goToIntent = new Intent(this, BlueActivity.class);

        goToIntent.putExtra("last_activity", "red");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }
    public void goToGreen(View view){
        String msg = ((EditText)findViewById(R.id.red_2_green_message)).getText().toString();

        Intent goToIntent = new Intent(this, GreenActivity.class);

        goToIntent.putExtra("last_activity", "red");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }
}
