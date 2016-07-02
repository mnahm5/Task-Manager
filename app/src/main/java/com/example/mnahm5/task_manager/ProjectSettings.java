package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProjectSettings extends AppCompatActivity {
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_settings);

        Intent intent = getIntent();
        String projectName = intent.getStringExtra("projectName");
        String description = intent.getStringExtra("description");
        setTitle(projectName);

        final EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Button btEdit = (Button) findViewById(R.id.btEdit);
        final Button btCancel = (Button) findViewById(R.id.btCancel);

        etProjectName.setText(projectName);
        etProjectName.setEnabled(false);
        etDescription.setText(description);
        etDescription.setEnabled(false);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    etProjectName.setEnabled(true);
                    etDescription.setEnabled(true);
                    String btMsg = "Save Changes";
                    btEdit.setText(btMsg);
                    flag = true;
                }
            }
        });
    }
}
