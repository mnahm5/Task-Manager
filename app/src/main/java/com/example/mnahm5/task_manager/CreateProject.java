package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        setTitle("Create a new Project");

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

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
                                Intent intent1 = new Intent(CreateProject.this,Projects.class);
                                intent1.putExtra("username", username);
                                CreateProject.this.startActivity(intent1);
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
