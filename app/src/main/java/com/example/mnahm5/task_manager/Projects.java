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

        String[] projectNames = {"Project A", "Project B"};
        String[] dates = {"10 Febrauary", "20 April"};
        ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
        for (int i = 0; i < projectNames.length; i++) {
            projectCards.add(i, new ProjectCard(projectNames[i],dates[i]));
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
