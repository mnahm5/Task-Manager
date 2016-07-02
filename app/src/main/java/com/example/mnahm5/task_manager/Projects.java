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
import android.widget.Toast;

import java.util.ArrayList;

public class Projects extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Projects");

        final Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        String[] projectIds = intent.getStringArrayExtra("projectIds");
        String[] projectNames = intent.getStringArrayExtra("projectNames");
        String[] descriptions = intent.getStringArrayExtra("descriptions");
        String[] datesCreated = intent.getStringArrayExtra("datesCreated");
        ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
        for (int i = 0; i < projectIds.length; i++) {
            projectCards.add(i,new ProjectCard(projectIds[i], projectNames[i], descriptions[i], datesCreated[i]));
        }
        ListAdapter listAdapter = new ProjectCardCustomerAdapter(this, projectCards);
        final ListView listView = (ListView) findViewById(R.id.ProjectListView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProjectCard projectCard = (ProjectCard) listView.getItemAtPosition(i);
                Intent intent1 = new Intent(Projects.this, ProjectActivity.class);
                intent1.putExtra("username", username);
                intent1.putExtra("projectId", projectCard.projectId);
                intent1.putExtra("projectName", projectCard.projectName);
                intent1.putExtra("description", projectCard.description);
                intent1.putExtra("dateCreated", projectCard.dateCreated);
                Projects.this.startActivity(intent1);
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
