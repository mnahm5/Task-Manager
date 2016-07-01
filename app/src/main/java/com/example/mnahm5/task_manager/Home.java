package com.example.mnahm5.task_manager;

import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    private String username, fullName, email, companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home Page");

        final TextView tvCompanyName = (TextView) findViewById(R.id.tvCompanyName);
        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        fullName = intent.getStringExtra("fullName");
        email = intent.getStringExtra("email");
        companyName = intent.getStringExtra("companyName");
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
            intent.putExtra("username", username);
            intent.putExtra("fullName", fullName);
            intent.putExtra("email", email);
            intent.putExtra("companyName", companyName);
            Home.this.startActivity(intent);
            return true;
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
                            Intent intent1 = new Intent(Home.this, Projects.class);
                            int[] projectIdArray = new int[noOfProjects];
                            String[] projectNameArray = new String[noOfProjects];
                            String[] descriptionArray = new String[noOfProjects];
                            String[] dateCreatedArray = new String[noOfProjects];
                            for (int i = 0; i < noOfProjects; i++) {
                                projectIdArray[i] = projectIds.getInt(i);
                                projectNameArray[i] = projectNames.getString(i);
                                descriptionArray[i] = descriptions.getString(i);
                                dateCreatedArray[i] = datesCreated.getString(i);
                            }
                            intent1.putExtra("projectIds",projectIdArray);
                            intent1.putExtra("projectNames", projectNameArray);
                            intent1.putExtra("descriptions", descriptionArray);
                            intent1.putExtra("datesCreated", dateCreatedArray);
                            intent1.putExtra("username",username);
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
