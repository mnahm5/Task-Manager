package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        setTitle("Create a new Project");

        final EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Button btCreate = (Button) findViewById(R.id.btCreate);
        final Button btCancel = (Button) findViewById(R.id.btCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateProject.this, Home.class);
                CreateProject.this.startActivity(intent);
            }
        });
    }
}
