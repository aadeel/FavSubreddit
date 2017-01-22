package com.adeel.favouritesubreddit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String REQUEST_TAG = "request_tag";

    RequestQueue mRequestQueue;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://www.reddit.com/r/Android/.json";
        mListView = (ListView) findViewById(R.id.activity_main_listview);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PostModel postModel;
                    Gson gson = new GsonBuilder().create();
                    List<PostModel> list = new ArrayList<PostModel>();
                    JSONObject json = new JSONObject(response).getJSONObject("data");
                    JSONArray array = json.getJSONArray("children");
                    for (int i = 0; i<array.length(); i++){
                        postModel = gson.fromJson(array.getJSONObject(i).getJSONObject("data").toString(),
                                PostModel.class);
                        list.add(postModel);
                    }
                    PostAdapter postAdapter = new PostAdapter(MainActivity.this, list);
                    mListView.setAdapter(postAdapter);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setTag(REQUEST_TAG);
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(REQUEST_TAG);
        super.onStop();
    }
}
