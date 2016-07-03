package com.example.mnahm5.task_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("UserDetails")) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            MainActivity.this.startActivity(intent);
        }
        else {
            setTitle("Task Manager");

            final Button btRegisterPage = (Button) findViewById(R.id.btRegisterPage);
            final Button btLoginPage = (Button) findViewById(R.id.btLoginPage);
            btRegisterPage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent registerIntent = new Intent(MainActivity.this, Register.class);
                    MainActivity.this.startActivity(registerIntent);
                }
            });

            btLoginPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent loginIntent = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(loginIntent);
                }
            });
        }
    }
}
