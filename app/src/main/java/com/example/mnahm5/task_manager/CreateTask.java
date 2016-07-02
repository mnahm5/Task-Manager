package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");
        final String projectName = intent.getStringExtra("projectName");
        setTitle(projectName);

        final EditText etTaskTitle = (EditText) findViewById(R.id.etTaskTitle);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Spinner spTaskType = (Spinner) findViewById(R.id.spTaskType);
        final Button btAddTask = (Button) findViewById(R.id.btAddTask);
        final Button btCancel = (Button) findViewById(R.id.btCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskType = spTaskType.getSelectedItem().toString();
                if (taskType.equals("Choose Task List")) {
                    Toast.makeText(CreateTask.this, "Need to select which List to put current task in\nTry Again",Toast.LENGTH_LONG).show();
                }
                else {
                    String taskTitle = etTaskTitle.getText().toString();
                    String description = etDescription.getText().toString();
                    final DatePicker dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
                    String dueDate = dpDueDate.getDayOfMonth() + "-" + (dpDueDate.getMonth()+1) + "-" + dpDueDate.getYear();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(CreateTask.this, Home.class);
                                    CreateTask.this.startActivity(intent);
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateTask.this);
                                    builder.setMessage("Task Created Failed")
                                            .setNegativeButton("Retry",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    CreateTaskRequest createTaskRequest = new CreateTaskRequest(projectId, taskType, taskTitle, description, dueDate, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CreateTask.this);
                    queue.add(createTaskRequest);
                }
            }
        });
    }
}
