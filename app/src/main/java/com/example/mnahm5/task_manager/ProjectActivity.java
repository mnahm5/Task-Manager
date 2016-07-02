package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProjectActivity extends AppCompatActivity {
    private String username, projectId, projectName, description, dateCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        projectId = intent.getStringExtra("projectId");
        projectName = intent.getStringExtra("projectName");
        description = intent.getStringExtra("description");
        dateCreated = intent.getStringExtra("dateCreated");
        setTitle(projectName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_project_settings) {
            Intent intent = new Intent(ProjectActivity.this, ProjectSettings.class);
            intent.putExtra("username", username);
            intent.putExtra("projectId", projectId);
            intent.putExtra("projectName", projectName);
            intent.putExtra("description", description);
            intent.putExtra("dateCreated", dateCreated);
            ProjectActivity.this.startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}
