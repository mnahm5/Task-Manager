package com.example.mnahm5.task_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProjectActivity extends AppCompatActivity {
    private String username, projectId, projectName, description, dateCreated;
    private String taskType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        projectId = intent.getStringExtra("projectId");
        projectName = intent.getStringExtra("projectName");
        description = intent.getStringExtra("description");
        dateCreated = intent.getStringExtra("dateCreated");
        setTitle(projectName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_project_settings) {
            Intent intent = new Intent(ProjectActivity.this, ProjectSettings.class);
            intent.putExtra("username", username);
            intent.putExtra("projectId", projectId);
            intent.putExtra("projectName", projectName);
            intent.putExtra("description", description);
            intent.putExtra("dateCreated", dateCreated);
            ProjectActivity.this.startActivity(intent);
        }
        else if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(ProjectActivity.this, Login.class);
            ProjectActivity.this.startActivity(intent);
            finish();
        }
        else {
            if (id == R.id.action_to_do) {
                taskType = "To Do";
            }
            else if (id == R.id.action_doing) {
                taskType = "Doing";
            }
            else if (id == R.id.action_done) {
                taskType = "Done";
            }
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            int noOfTasks = Integer.parseInt(jsonResponse.getString("noOfTasks"));
                            JSONArray taskIds = jsonResponse.getJSONArray("taskIds");
                            JSONArray taskTitles = jsonResponse.getJSONArray("taskTitles");
                            JSONArray descriptions = jsonResponse.getJSONArray("descriptions");
                            JSONArray dueDates = jsonResponse.getJSONArray("dueDates");
                            Intent intent1 = new Intent(ProjectActivity.this, ProjectTasks.class);
                            String[] taskIdArray = new String[noOfTasks];
                            String[] taskTitleArray = new String[noOfTasks];
                            String[] descriptionArray = new String[noOfTasks];
                            String[] dueDateArray = new String[noOfTasks];
                            for (int i = 0; i < noOfTasks; i++) {
                                taskIdArray[i] = taskIds.getString(i);
                                taskTitleArray[i] = taskTitles.getString(i);
                                descriptionArray[i] = descriptions.getString(i);
                                dueDateArray[i] = dueDates.getString(i);
                            }
                            intent1.putExtra("taskIds",taskIdArray);
                            intent1.putExtra("taskTitles", taskTitleArray);
                            intent1.putExtra("descriptions", descriptionArray);
                            intent1.putExtra("dueDates", dueDateArray);
                            intent1.putExtra("projectId", projectId);
                            intent1.putExtra("projectName", projectName);
                            intent1.putExtra("taskType", taskType);
                            ProjectActivity.this.startActivity(intent1);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);
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

            TasksRequest tasksRequest = new TasksRequest(projectId, taskType, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ProjectActivity.this);
            queue.add(tasksRequest);
        }

        return super.onOptionsItemSelected(item);
    }
}
