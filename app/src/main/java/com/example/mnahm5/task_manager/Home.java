package com.example.mnahm5.task_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private String username, fullName, email, companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home Page");

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userDetailsJson = sharedPreferences.getString("UserDetails", "");
        UserDetails user = gson.fromJson(userDetailsJson, UserDetails.class);
        username = user.username;
        fullName = user.fullName;
        email = user.email;
        companyName = user.companyName;

        final TextView tvCompanyName = (TextView) findViewById(R.id.tvCompanyName);
        tvCompanyName.setText(companyName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(Home.this, Profile.class);
            Home.this.startActivity(intent);
            return true;
        }
        else if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(Home.this, Login.class);
            Home.this.startActivity(intent);
            finish();
        }
        else if (id == R.id.action_projects) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            int noOfProjects = Integer.parseInt(jsonResponse.getString("noOfProjects"));
                            JSONArray projectIds = jsonResponse.getJSONArray("projectIds");
                            JSONArray projectNames = jsonResponse.getJSONArray("projectNames");
                            JSONArray descriptions = jsonResponse.getJSONArray("descriptions");
                            JSONArray datesCreated = jsonResponse.getJSONArray("datesCreated");
                            ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
                            for (int i = 0; i < noOfProjects; i++) {
                                projectCards.add(i, new ProjectCard(projectIds.getString(i),
                                        projectNames.getString(i),
                                        descriptions.getString(i),
                                        datesCreated.getString(i)));
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("projectInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String allProjectDetailsJson = gson.toJson(projectCards);
                            editor.putString("allProjectDetailsJson", allProjectDetailsJson);
                            editor.apply();
                            Intent intent1 = new Intent(Home.this, Projects.class);
                            Home.this.startActivity(intent1);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                            builder.setMessage("Login Failed")
                                    .setNegativeButton("Retry",null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            ProjectsRequest projectsRequest = new ProjectsRequest(username, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Home.this);
            queue.add(projectsRequest);
        }
        return super.onOptionsItemSelected(item);
    }
}
