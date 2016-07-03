package com.example.mnahm5.task_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CreateProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        setTitle("Create a new Project");

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userDetailsJson = sharedPreferences.getString("UserDetails", "");
        UserDetails user = gson.fromJson(userDetailsJson, UserDetails.class);
        final String username = user.username;

        final EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Button btCreate = (Button) findViewById(R.id.btCreate);
        final Button btCancel = (Button) findViewById(R.id.btCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateProject.this, Home.class);
                CreateProject.this.startActivity(intent);
            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String projectName = etProjectName.getText().toString();
                final String description = etDescription.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String newProjectId = jsonResponse.getString("newProjectId");
                                String newDateCreated = jsonResponse.getString("newDateCreated");
                                ProjectCard newProjectCard = new ProjectCard(newProjectId, projectName, description, newDateCreated);
                                SharedPreferences sharedPreferences = getSharedPreferences("projectInfo", MODE_PRIVATE);
                                Gson gson = new Gson();
                                String allProjectDetailsJson = sharedPreferences.getString("allProjectDetailsJson", "");
                                Type type = new TypeToken<List<ProjectCard>>(){}.getType();
                                List<ProjectCard> projectCardList = gson.fromJson(allProjectDetailsJson, type);
                                ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
                                for (int i = 0; i < projectCardList.size(); i++) {
                                    projectCards.add(i,projectCardList.get(i));
                                }
                                projectCards.add(newProjectCard);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                allProjectDetailsJson = gson.toJson(projectCards);
                                editor.putString("allProjectDetailsJson", allProjectDetailsJson);
                                editor.apply();
                                Intent intent = new Intent(CreateProject.this, Projects.class);
                                CreateProject.this.startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateProject.this);
                                builder.setMessage("Project Creation Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                CreateProjectRequest createProjectRequest = new CreateProjectRequest(username, projectName, description, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateProject.this);
                queue.add(createProjectRequest);
            }
        });
    }
}
