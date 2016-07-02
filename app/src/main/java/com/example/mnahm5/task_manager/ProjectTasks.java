package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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

        String[] taskIds = intent.getStringArrayExtra("taskIds");
        String[] taskTitles = intent.getStringArrayExtra("taskTitles");
        String[] descriptions = intent.getStringArrayExtra("descriptions");
        String[] dueDates = intent.getStringArrayExtra("dueDates");
        ArrayList<taskCard> taskCards = new ArrayList<taskCard>();
        for (int i = 0; i < taskIds.length; i++) {
            taskCards.add(i,new taskCard(taskIds[i], taskTitles[i], descriptions[i], dueDates[i]));
        }
        ListAdapter listAdapter = new taskCardCustomAdapter(this, taskCards);
        final ListView listView = (ListView) findViewById(R.id.taskListView);
        listView.setAdapter(listAdapter);


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
