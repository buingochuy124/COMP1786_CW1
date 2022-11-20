package com.example.tripdiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UploadActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Button upload_btn = findViewById(R.id.upload_btn);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UploadActivity.this).setTitle("Some thing went wrong")
                        .setMessage("Not working"
                        ).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_screen:
                        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                        startActivityForResult(intent, 1);
                        return true;
                    case R.id.search_screen:
                        Intent intent2 = new Intent(UploadActivity.this, SearchActivity.class);
                        startActivityForResult(intent2, 1);
                        return true;
                    case R.id.upload_screen:
                        Intent intent3 = new Intent(UploadActivity.this, UploadActivity.class);
                        startActivityForResult(intent3, 1);
                        return true;

                }
                return false;
            }
        });


    }
}