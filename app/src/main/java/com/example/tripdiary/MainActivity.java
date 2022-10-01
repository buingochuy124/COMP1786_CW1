package com.example.tripdiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton add_button;
    DbHelper db;
    ArrayList<String> tripId, tripName, tripDestination, tripDate,tripRequireAssessement,tripDescription;
    AddActivity activity;
    TripAdapter tripAdapter;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recylerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                // startActivity(intent,);
                startActivityForResult(intent,1);
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
        if (requestCode == 1){
            recreate();
        }
    }
    void TripsInArrays(){
        Cursor cursor = db.readAllTrip();

        if (cursor.getCount() != 0){
            while(cursor.moveToNext()){


                tripId.add(cursor.getString(0));
                tripName.add(cursor.getString(1));
                tripDestination.add(cursor.getString(2));
                tripDate.add(cursor.getString(3));
                tripRequireAssessement.add(cursor.getString(4));
                tripDescription.add(cursor.getString(5));

            }
        }
        else {
            Toast.makeText(this, "There are no trip", Toast.LENGTH_SHORT).show();
        }
    }

}