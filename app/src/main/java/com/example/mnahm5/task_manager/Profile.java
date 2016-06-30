package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.opengl.EGLDisplay;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final EditText etFullName = (EditText) findViewById(R.id.etFullName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etCompanyName = (EditText) findViewById(R.id.etCompanyName);
        final Button btEdit = (Button) findViewById(R.id.btEdit);
        final Button btResetPassword = (Button) findViewById(R.id.btResetPassword);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String fullName = intent.getStringExtra("fullName");
        String email = intent.getStringExtra("email");
        String companyName = intent.getStringExtra("companyName");

        etFullName.setText(fullName);
        etEmail.setText(email);
        etCompanyName.setText(companyName);

        etFullName.setEnabled(false);
        etEmail.setEnabled(false);
        etCompanyName.setEnabled(false);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    etFullName.setEnabled(true);
                    etEmail.setEnabled(true);
                    etCompanyName.setEnabled(true);
                    String btMsg = "Save Changes";
                    btEdit.setText(btMsg);
                    flag = true;
                }
            }
        });
        btResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Profile.this, ResetPassword.class);
                Profile.this.startActivity(intent1);
            }
        });
    }
}
