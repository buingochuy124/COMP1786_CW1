package com.example.tripdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    DbHelper db;
    ArrayList<String> tripId, tripName, tripDestination, tripDate, tripRequireAssessement, tripDescription;
    TripAdapter tripAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                // startActivity(intent,);
                startActivityForResult(intent, 1);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_screen:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivityForResult(intent, 1);
                        return true;
                    case R.id.search_screen:
                        Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                        startActivityForResult(intent2, 1);
                        return true;
                    case R.id.upload_screen:
                        Intent intent3 = new Intent(MainActivity.this, UploadActivity.class);
                        startActivityForResult(intent3, 1);
                        return true;

                }
                return false;
            }
        });


        db = new DbHelper(MainActivity.this);
        tripId = new ArrayList<>();
        tripName = new ArrayList<>();
        tripDestination = new ArrayList<>();
        tripDate = new ArrayList<>();
        tripRequireAssessement = new ArrayList<>();
        tripDescription = new ArrayList<>();


        TripsInArrays();


        tripAdapter = new TripAdapter(
                MainActivity.this,
                this,
                tripId,
                tripName,
                tripDestination,
                tripDate,
                tripRequireAssessement,
                tripDescription);
        
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trip_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void delete(MenuItem item) {

        db = new DbHelper(MainActivity.this);
        db.deleteAllTrip();
        recreate();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void TripsInArrays() {
        Cursor cursor = db.readAllTrip();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {


                tripId.add(cursor.getString(0));
                tripName.add(cursor.getString(1));
                tripDestination.add(cursor.getString(2));
                tripDate.add(cursor.getString(3));
                tripRequireAssessement.add(cursor.getString(4));
                tripDescription.add(cursor.getString(5));

            }
        } else {
            Toast.makeText(this, "There are no trip", Toast.LENGTH_SHORT).show();
        }
    }

}