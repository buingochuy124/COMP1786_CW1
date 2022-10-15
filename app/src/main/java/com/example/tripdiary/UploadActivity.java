package com.example.tripdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UploadActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_screen:
                        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                        startActivityForResult(intent,1);
                        return  true;
                    case R.id.search_screen:
                        Intent intent2 = new Intent(UploadActivity.this, SearchActivity.class);
                        startActivityForResult(intent2,1);
                        return  true;
                    case R.id.upload_screen:
                        Intent intent3 = new Intent(UploadActivity.this, UploadActivity.class);
                        startActivityForResult(intent3,1);
                        return  true;

                }
                return  false;
            }
        });
    }
}