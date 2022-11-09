package com.example.tripdiary;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class TripUpdateActivity extends AppCompatActivity {

    Button updateButton, deleteButton;
    EditText nameInput, destinationInput, dateInput, descriptionInput;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String id, name, destination, date, requireAssessement, description;
    AlertDialog.Builder builder;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_update);


        nameInput = findViewById(R.id.inputName_Update);
        destinationInput = findViewById(R.id.inputDestination_Update);
        dateInput = findViewById(R.id.inputDate_Update);
        descriptionInput = findViewById(R.id.inputDescription_Update);
        radioGroup = findViewById(R.id.radioGroup_Update);


        getAndSetIntentData();

        updateButton = findViewById(R.id.btn_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString().trim();

                description = descriptionInput.getText().toString().trim();
                date = dateInput.getText().toString().trim();
                destination = destinationInput.getText().toString().trim();

                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                requireAssessement = radioButton.getText().toString();

                if (requireAssessement.equals("yes")) {
                    requireAssessement = "true";
                } else {
                    requireAssessement = "false";
                }

                if (nameInput.getText().toString().trim().isEmpty() ||
                        destinationInput.getText().toString().trim().isEmpty() ||
                        dateInput.getText().toString().trim().isEmpty() ||
                        descriptionInput.getText().toString().trim().isEmpty()) {
                    displayRequireAlert();
                } else {
                    //Toast.makeText(TripUpdateActivity.this, ""+id+name +destination+date+Boolean.parseBoolean(requireAssessement.trim())+description, Toast.LENGTH_SHORT).show();
                    DbHelper db = new DbHelper(TripUpdateActivity.this);

                    db.updateTrip(id, name, destination, date, Boolean.parseBoolean(requireAssessement), description);
                }

            }
        });
        Button seeAllExpensesButton = findViewById(R.id.btn_seeallexpenses);

        seeAllExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripUpdateActivity.this, ExpensesActivity.class);
                intent.putExtra("expensesTripID", String.valueOf(id));
                //startActivity(intent);
//                intent.putExtra("tripId", )
                startActivityForResult(intent, 1);
            }
        });

    }

    private void displayRequireAlert() {
        new android.app.AlertDialog.Builder(this).setTitle("Some thing wrong").setMessage("you need to fill all required fields").setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }


    public void action_trip_update_delete(MenuItem item) {

        DbHelper db = new DbHelper(TripUpdateActivity.this);
        db.deleteOneTrip(id);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // recreate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trip_update_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void updateDate(LocalDate date) {
        TextView dateText = (TextView) findViewById(R.id.inputDate_Update);
        dateText.setText(date.toString());
    }

    public void showDatePickerDialogUpdate(View v) {
        DialogFragment newFragment = new DateUpdateFragment();
        newFragment.show(getSupportFragmentManager(), "date2");
    }


    void getAndSetIntentData() {
        if (getIntent().hasExtra("tripId")
                && getIntent().hasExtra("tripName")
                && getIntent().hasExtra("tripDestination")
                && getIntent().hasExtra("tripDate")
                && getIntent().hasExtra("tripRequireAssessement")
                && getIntent().hasExtra("tripDescription")) {

            id = getIntent().getStringExtra("tripId");
            name = getIntent().getStringExtra("tripName");
            destination = getIntent().getStringExtra("tripDestination");
            date = getIntent().getStringExtra("tripDate");
            requireAssessement = getIntent().getStringExtra("tripRequireAssessement");
            description = getIntent().getStringExtra("tripDescription");

            nameInput.setText(name);
            destinationInput.setText(destination);
            dateInput.setText(date);
            descriptionInput.setText(description);
            if (requireAssessement.toString() == "true") {
                RadioButton yes = findViewById(R.id.radioButtonYes);
                RadioButton no = findViewById(R.id.radioButtonNo);

                yes.setChecked(true);
                no.setChecked(false);

            } else {
                RadioButton yes = findViewById(R.id.radioButtonYes);
                RadioButton no = findViewById(R.id.radioButtonNo);
                yes.setChecked(false);
                no.setChecked(true);
            }


        } else {
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        }
    }

    public void RadioButtonClicked2(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonYes:
                if (checked)
                    break;
            case R.id.radioButtonNo:
                if (checked)
                    break;
        }
    }
}