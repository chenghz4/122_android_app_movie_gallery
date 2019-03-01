package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        ArrayList<Movie> people = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        String title=bundle.getString("message");
        String page="1";


        final JsonArrayRequest SearchRequest = new JsonArrayRequest("https://10.0.2.2:8443/api/stars" +
                "?id="+title+"&year=&director=&star=&page="+page+"&number=20" +
                "&sort=a.rating%20desc&genres=&letters=",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id=jsonObject.getString("movie_id");
                                String title=jsonObject.getString("movie_title");
                                String year=jsonObject.getString("movie_year");
                                String director=jsonObject.getString("movie_director");
                                String list_s=jsonObject.getString("list_s");
                                String list_g=jsonObject.getString("list_g");
                                Movie movie=new Movie(id,title,year,director,list_s,list_g);
                                people.add(movie);
                            }
                        }catch (Exception e){
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
        queue.add(SearchRequest);



        PeopleListViewAdapter adapter = new PeopleListViewAdapter(people, this);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie person = people.get(position);
                String message = String.format("Clicked on position: %d, name: %s, %d", position, person.getName(), person.getBirthYear());
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
