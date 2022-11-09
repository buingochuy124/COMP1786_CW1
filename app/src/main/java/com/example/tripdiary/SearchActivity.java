package com.example.tripdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button searchButton;
    DbHelper db = new DbHelper(SearchActivity.this);
    SearchTripAdapter searchTripAdapter;
    RecyclerView recyclerView;

    EditText tripNametext;
    ArrayList<String> tripId, tripName, tripDestination, tripDate, tripRequireAssessement, tripDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.SearchRecyclerView);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_screen:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        startActivityForResult(intent, 1);
                        return true;
                    case R.id.search_screen:
                        Intent intent2 = new Intent(SearchActivity.this, SearchActivity.class);
                        startActivityForResult(intent2, 1);
                        return true;
                    case R.id.upload_screen:
                        Intent intent3 = new Intent(SearchActivity.this, UploadActivity.class);
                        startActivityForResult(intent3, 1);
                        return true;

                }
                return false;
            }
        });


        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = new DbHelper(SearchActivity.this);
                tripId = new ArrayList<>();
                tripName = new ArrayList<>();
                tripDestination = new ArrayList<>();
                tripDate = new ArrayList<>();
                tripRequireAssessement = new ArrayList<>();
                tripDescription = new ArrayList<>();

                tripNametext = findViewById(R.id.search_trip_name);
                TripsInArrays(tripNametext.getText().toString());

            }
        });


//
//        String text = tripNametext.getText().toString();
//
//        TripsInArrays(text);
//
//        //TripsInArrays(text);
//        //Toast.makeText(this, "" + tripId + tripName + tripDescription + tripDate + tripRequireAssessement + tripDestination, Toast.LENGTH_SHORT).show();
//
//
//        searchTripAdapter = new SearchTripAdapter(
//                SearchActivity.this,
//                this,
//                tripId,
//                tripName,
//                tripDestination,
//                tripDate,
//                tripRequireAssessement,
//                tripDescription);
//
//        recyclerView.setAdapter(searchTripAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

    }

    void TripsInArrays(String trip_name) {


        Cursor cursor = db.SearchTrip(trip_name);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                tripId.add(cursor.getString(0));
                tripName.add(cursor.getString(1));
                tripDestination.add(cursor.getString(2));
                tripDate.add(cursor.getString(3));
                tripRequireAssessement.add(cursor.getString(4));
                tripDescription.add(cursor.getString(5));


            }


            searchTripAdapter = new SearchTripAdapter(
                    SearchActivity.this,
                    this,
                    tripId,
                    tripName,
                    tripDestination,
                    tripDate,
                    tripRequireAssessement,
                    tripDescription);

            recyclerView.setAdapter(searchTripAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        } else {
            Toast.makeText(this, "There are no trip", Toast.LENGTH_SHORT).show();
            recreate();
        }

    }

}