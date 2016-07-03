package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskDetails extends AppCompatActivity {

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        final String taskId = intent.getStringExtra("taskId");
        String taskTitle = intent.getStringExtra("taskTitle");
        String description = intent.getStringExtra("description");
        String dueDate = intent.getStringExtra("dueDate");
        String taskType = intent.getStringExtra("taskType");
        setTitle(taskTitle);

        final EditText etTaskTitle = (EditText) findViewById(R.id.etTaskTitle);
        final EditText etTaskDescription =(EditText) findViewById(R.id.etTaskDescription);
        final Spinner spTaskType = (Spinner) findViewById(R.id.spTaskType);
        final DatePicker dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
        final Button btEdit = (Button) findViewById(R.id.btEdit);
        final Button btCancel = (Button) findViewById(R.id.btCancel);

        etTaskTitle.setText(taskTitle);
        etTaskDescription.setText(description);
        switch (taskType) {
            case "To Do" :
                spTaskType.setSelection(1);
                break;
            case "Doing":
                spTaskType.setSelection(2);
                break;
            case "Done":
                spTaskType.setSelection(3);
                break;
        }
        int year = Integer.parseInt(dueDate.substring(dueDate.length()-4));
        int month = Integer.parseInt(dueDate.substring(dueDate.length()-7, dueDate.length()-5));
        int day = Integer.parseInt(dueDate.substring(dueDate.length()-10, dueDate.length()-8));
        Toast.makeText(TaskDetails.this, day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();
        dpDueDate.init(year,month-1,day,null);

        etTaskTitle.setEnabled(false);
        etTaskDescription.setEnabled(false);
        spTaskType.setEnabled(false);
        dpDueDate.setEnabled(false);

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
                    etTaskTitle.setEnabled(true);
                    etTaskDescription.setEnabled(true);
                    spTaskType.setEnabled(true);
                    dpDueDate.setEnabled(true);
                    String btMsg = "Save Changes";
                    btEdit.setText(btMsg);
                    flag = true;
                }
                else {
                    String taskType = spTaskType.getSelectedItem().toString();
                    if (taskType.equals("Choose Task List")) {
                        Toast.makeText(TaskDetails.this, "Need to select which List to put current task in\nTry Again",Toast.LENGTH_LONG).show();
                    }
                    else {
                        final String taskTitle = etTaskTitle.getText().toString();
                        final String description = etTaskDescription.getText().toString();
                        final String dueDate = dpDueDate.getDayOfMonth() + "-" + (dpDueDate.getMonth()+1) + "-" + dpDueDate.getYear();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        Intent intent = new Intent(TaskDetails.this, Projects.class);
                                        TaskDetails.this.startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetails.this);
                                        builder.setMessage("Task Update Failed")
                                                .setNegativeButton("Retry",null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        TaskEditRequest taskEditRequest = new TaskEditRequest(taskId, taskTitle, taskType, dueDate, description, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(TaskDetails.this);
                        queue.add(taskEditRequest);
                    }
                }
            }
        });
    }
}
