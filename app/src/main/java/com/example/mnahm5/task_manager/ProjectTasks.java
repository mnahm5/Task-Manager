package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ProjectTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");
        final String projectName = intent.getStringExtra("projectName");
        final String taskType = intent.getStringExtra("taskType");
        String title = projectName + " (" + taskType + ")";
        setTitle(title);

        FloatingActionButton btAddTask = (FloatingActionButton) findViewById(R.id.btAddTask);
        btAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProjectTasks.this, CreateTask.class);
                intent1.putExtra("projectId", projectId);
                intent1.putExtra("projectName", projectName);
                ProjectTasks.this.startActivity(intent1);
            }
        });
    }

}
