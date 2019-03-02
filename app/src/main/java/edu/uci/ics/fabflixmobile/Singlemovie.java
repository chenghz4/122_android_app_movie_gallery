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

    ArrayList<Movie> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String title = bundle.getString("title");
        String year = bundle.getString("year");
        String director = bundle.getString("director");
        String list_s = bundle.getString("list_s");
        String list_g = bundle.getString("list_g");

        Movie movie=new Movie(id,title,year,director,list_s,list_g);
        people.add(movie);
        PeopleListViewAdapter1 adapter = new PeopleListViewAdapter1(people, this);

        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(adapter);


    }

    public void Goback(View view) {
        Intent goToIntent = new Intent(this, BlueActivity.class);
        startActivity(goToIntent);
    }
}
