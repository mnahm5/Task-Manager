package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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

        final Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");
        final String projectName = intent.getStringExtra("projectName");
        final String taskType = intent.getStringExtra("taskType");
        String title = projectName + " (" + taskType + ")";
        setTitle(title);

        String[] taskIds = intent.getStringArrayExtra("taskIds");
        String[] taskTitles = intent.getStringArrayExtra("taskTitles");
        String[] descriptions = intent.getStringArrayExtra("descriptions");
        String[] dueDates = intent.getStringArrayExtra("dueDates");
        final ArrayList<TaskCard> taskCards = new ArrayList<TaskCard>();
        for (int i = 0; i < taskIds.length; i++) {
            taskCards.add(i,new TaskCard(taskIds[i], taskTitles[i], descriptions[i], dueDates[i]));
        }
        ListAdapter listAdapter = new taskCardCustomAdapter(this, taskCards);
        final ListView listView = (ListView) findViewById(R.id.taskListView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskCard taskCard = (TaskCard) listView.getItemAtPosition(i);
                Intent intent1 = new Intent(ProjectTasks.this, TaskDetails.class);
                intent1.putExtra("taskId", taskCard.taskId);
                intent1.putExtra("taskTitle", taskCard.taskTitle);
                intent1.putExtra("description", taskCard.description);
                intent1.putExtra("dueDate", taskCard.dueDate);
                intent1.putExtra("taskType", taskType);
                ProjectTasks.this.startActivity(intent1);
            }
        });

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
