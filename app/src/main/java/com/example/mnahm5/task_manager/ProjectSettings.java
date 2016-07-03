package com.example.mnahm5.task_manager;

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

public class ProjectSettings extends AppCompatActivity {
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_settings);

        Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");
        String projectName = intent.getStringExtra("projectName");
        String description = intent.getStringExtra("description");
        final String dateCreated = intent.getStringExtra("dateCreated");
        setTitle(projectName);

        final EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Button btEdit = (Button) findViewById(R.id.btEdit);
        final Button btDelete = (Button) findViewById(R.id.btDelete);
        final Button btCancel = (Button) findViewById(R.id.btCancel);

        etProjectName.setText(projectName);
        etProjectName.setEnabled(false);
        etDescription.setText(description);
        etDescription.setEnabled(false);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    etProjectName.setEnabled(true);
                    etDescription.setEnabled(true);
                    String btMsg = "Save Changes";
                    btEdit.setText(btMsg);
                    flag = true;
                }
                else {
                    final String projectName = etProjectName.getText().toString();
                    final String description = etDescription.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    ProjectCard newProjectCard = new ProjectCard(projectId, projectName, description, dateCreated);
                                    SharedPreferences sharedPreferences = getSharedPreferences("projectInfo", MODE_PRIVATE);
                                    Gson gson = new Gson();
                                    String allProjectDetailsJson = sharedPreferences.getString("allProjectDetailsJson", "");
                                    Type type = new TypeToken<List<ProjectCard>>(){}.getType();
                                    List<ProjectCard> projectCardList = gson.fromJson(allProjectDetailsJson, type);
                                    ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
                                    for (int i = 0; i < projectCardList.size(); i++) {
                                        if (projectCardList.get(i).projectId.equals(projectId)) {
                                            projectCards.add(i, newProjectCard);
                                        }
                                        else {
                                            projectCards.add(i,projectCardList.get(i));
                                        }
                                    }
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    allProjectDetailsJson = gson.toJson(projectCards);
                                    editor.putString("allProjectDetailsJson", allProjectDetailsJson);
                                    editor.apply();
                                    Intent intent = new Intent(ProjectSettings.this, Projects.class);
                                    ProjectSettings.this.startActivity(intent);
                                    finish();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectSettings.this);
                                    builder.setMessage("Project Update Failed")
                                            .setNegativeButton("Retry",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    EditProjectRequest editProjectRequest = new EditProjectRequest(projectId, projectName, description, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProjectSettings.this);
                    queue.add(editProjectRequest);
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                SharedPreferences sharedPreferences = getSharedPreferences("projectInfo", MODE_PRIVATE);
                                Gson gson = new Gson();
                                String allProjectDetailsJson = sharedPreferences.getString("allProjectDetailsJson", "");
                                Type type = new TypeToken<List<ProjectCard>>(){}.getType();
                                List<ProjectCard> projectCardList = gson.fromJson(allProjectDetailsJson, type);
                                ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
                                int j = 0;
                                for (int i = 0; i < projectCardList.size(); i++) {
                                    if (!projectCardList.get(i).projectId.equals(projectId)) {
                                        projectCards.add(j,projectCardList.get(i));
                                        j++;
                                    }
                                }
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                allProjectDetailsJson = gson.toJson(projectCards);
                                editor.putString("allProjectDetailsJson", allProjectDetailsJson);
                                editor.apply();
                                Intent intent = new Intent(ProjectSettings.this, Projects.class);
                                ProjectSettings.this.startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectSettings.this);
                                builder.setMessage("Project Deletion Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DeleteProjectRequest deleteProjectRequest = new DeleteProjectRequest(projectId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProjectSettings.this);
                queue.add(deleteProjectRequest);
            }
        });
    }
}
