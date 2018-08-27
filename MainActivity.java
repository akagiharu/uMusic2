package com.example.nuno.umusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName, pwd;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        pwd = findViewById(R.id.pwd);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });

    }

    public void Check() {
        if(userName.getText().toString().equals("admin") && pwd.getText().toString().equals("admin")) {
            Toast.makeText(this, "Success Login: " + userName.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            } catch (Exception ex) {
                Log.e("Error in go through", ex.getMessage());
            }
        }
        else {
            Toast.makeText(this, "This username " + userName.getText().toString() + " doesn't exit!", Toast.LENGTH_SHORT).show();
        }
    }
}
