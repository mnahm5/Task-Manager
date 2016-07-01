package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Projects extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Projects");

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        int[] projectIds = intent.getIntArrayExtra("projectIds");
        String[] projectNames = intent.getStringArrayExtra("projectNames");
        String[] descriptions = intent.getStringArrayExtra("descriptions");
        String[] datesCreated = intent.getStringArrayExtra("datesCreated");
        ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
        for (int i = 0; i < projectIds.length; i++) {
            projectCards.add(i,new ProjectCard(projectIds[i], projectNames[i], descriptions[i], datesCreated[i]));
        }
        ListAdapter listAdapter = new ProjectCardCustomerAdapter(this, projectCards);
        ListView listView = (ListView) findViewById(R.id.ProjectListView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        FloatingActionButton btAddProject = (FloatingActionButton) findViewById(R.id.btAddProject);
        btAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Projects.this, CreateProject.class);
                intent.putExtra("username", username);
                Projects.this.startActivity(intent);
            }
        });
    }
}
