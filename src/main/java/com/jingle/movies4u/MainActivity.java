package com.jingle.movies4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "http://www.mocky.io/v2/5d4d3c3f330000d43f3376b0";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private SwipeRefreshLayout refreshLayout;
    //private int refresh_count = 0;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshItems();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadJson();
            }
        });

        listItems = new ArrayList<>();

        loadRecyclerViewData();
    }

    private void LoadJson() {

        refreshLayout.setRefreshing(true);
    }

    private void refreshItems(){

        refreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson();
                    }
                }
        );
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonObject = new JSONArray(response);


                            for (int i = 0; i<jsonObject.length(); i++){
                                JSONObject productObject = jsonObject.getJSONObject(i);

                                String title = productObject.getString("title");
                                String image = productObject.getString("image");
                                double rating = productObject.getDouble("rating");
                                int releaseyear = productObject.getInt("releaseYear");
                                String desc = productObject.getString("genre");

                                ListItem item = new ListItem(title,image,rating,releaseyear,desc);
                                listItems.add(item);
                            }

                            refreshLayout.setRefreshing(false);
                            adapter = new MyAdapter(MainActivity.this, listItems);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        refreshLayout.setRefreshing(false);

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
