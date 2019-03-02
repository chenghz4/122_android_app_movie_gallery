package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Singlemovie extends Activity {

    final RequestQueue queue = NetworkManager.sharedManager(this).queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        ArrayList<Movie> movies = new ArrayList<>();

        final JsonArrayRequest SearchRequest1 = new JsonArrayRequest(
                "https://10.0.2.2:8443/api/single-movie?id="+id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            System.out.print(response.length());
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("movie_id");
                                String title = jsonObject.getString("movie_title");
                                String year = jsonObject.getString("movie_year");
                                String director = jsonObject.getString("movie_director");
                                String list_s = jsonObject.getString("list_s");
                                String list_g = jsonObject.getString("list_g");
                                Movie movie = new Movie(id, title, year, director, list_s, list_g);
                                movies.add(movie);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
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
        queue.add(SearchRequest1);
        PeopleListViewAdapter1 adapter = new PeopleListViewAdapter1(movies, this);

        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(adapter);


    }

    public void Goback(View view) {
        Intent goToIntent = new Intent(this, BlueActivity.class);
        startActivity(goToIntent);
    }
}
