package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        final EditText etFullName = (EditText) findViewById(R.id.etFullName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etCompanyName = (EditText) findViewById(R.id.etCompanyName);
        final Button btEdit = (Button) findViewById(R.id.btEdit);
        final Button btResetPassword = (Button) findViewById(R.id.btReset);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
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
                else {
                    final String newFullName = etFullName.getText().toString();
                    final String newEmail = etEmail.getText().toString();
                    final String newCompanyName = etCompanyName.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(Profile.this, Home.class);
                                    intent.putExtra("username",username);
                                    intent.putExtra("fullName", newFullName);
                                    intent.putExtra("email", newEmail);
                                    intent.putExtra("companyName", newCompanyName);
                                    Profile.this.startActivity(intent);
                                    finish();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                                    builder.setMessage("Update Failed")
                                            .setNegativeButton("Retry",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(username, newFullName, newEmail, newCompanyName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Profile.this);
                    queue.add(updateProfileRequest);
                }
            }
        });
        btResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Profile.this, ResetPassword.class);
                intent1.putExtra("username",username);
                Profile.this.startActivity(intent1);
            }
        });
    }
}
