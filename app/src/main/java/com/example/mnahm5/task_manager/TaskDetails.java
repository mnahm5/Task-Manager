package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");
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
    }
}
