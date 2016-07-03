package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Projects extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Projects");

        SharedPreferences sharedPreferences = getSharedPreferences("projectInfo", MODE_PRIVATE);
        Gson gson = new Gson();
        String allProjectDetailsJson = sharedPreferences.getString("allProjectDetailsJson", "");
        Type type = new TypeToken<List<ProjectCard>>(){}.getType();
        List<ProjectCard> projectCardList = gson.fromJson(allProjectDetailsJson, type);
        ArrayList<ProjectCard> projectCards = new ArrayList<ProjectCard>();
        for (int i = 0; i < projectCardList.size(); i++) {
            projectCards.add(i,projectCardList.get(i));
        }

        ListAdapter listAdapter = new ProjectCardCustomerAdapter(this, projectCards);
        final ListView listView = (ListView) findViewById(R.id.ProjectListView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProjectCard projectCard = (ProjectCard) listView.getItemAtPosition(i);
                Intent intent1 = new Intent(Projects.this, ProjectActivity.class);
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
//                intent.putExtra("username", username);
                Projects.this.startActivity(intent);
            }
        });
    }
}
