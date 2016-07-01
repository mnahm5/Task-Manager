package com.example.mnahm5.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private String username, fullName, email, companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home Page");

        final TextView tvCompanyName = (TextView) findViewById(R.id.tvCompanyName);
        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        fullName = intent.getStringExtra("fullName");
        email = intent.getStringExtra("email");
        companyName = intent.getStringExtra("companyName");
        tvCompanyName.setText(companyName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(Home.this, Profile.class);
            intent.putExtra("username", username);
            intent.putExtra("fullName", fullName);
            intent.putExtra("email", email);
            intent.putExtra("companyName", companyName);
            Home.this.startActivity(intent);
            return true;
        }
        else if (id == R.id.action_projects) {
            Intent intent = new Intent(Home.this, Projects.class);
            Home.this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
