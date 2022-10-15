package com.example.tripdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class AddActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText nameInput = findViewById(R.id.inputName);
        EditText destinationInput = findViewById(R.id.inputDestination);
        EditText dateInput = findViewById(R.id.inputDate);
        EditText descriptionInput = findViewById(R.id.inputDescription);
        Button addButton = findViewById(R.id.btn_add);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(AddActivity.this);

                if(nameInput.getText().toString().trim().isEmpty() ||
                        destinationInput.getText().toString().trim().isEmpty() ||
                        dateInput.getText().toString().trim().isEmpty() ||
                        descriptionInput.getText().toString().trim().isEmpty()){
                    displayRequireAlert();
                }
                else {
                    radioGroup = findViewById(R.id.radioGroup) ;
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    Boolean radioValues = true;
                    if(radioButton.getText().toString().equals("yes")){
                        radioValues = true;
                    }
                    else{
                        radioValues = false;
                    }

                    // Toast.makeText(AddActivity.this, ""+ radioValues, Toast.LENGTH_SHORT).show();

                    db.addTrip(
                            nameInput.getText().toString().trim(),
                            destinationInput.getText().toString().trim(),
                            dateInput.getText().toString().trim(),
                            radioValues,
                            descriptionInput.getText().toString().trim()
                    );

                }
            }
        });
    }



    private  void displayRequireAlert(){
        new AlertDialog.Builder(this).setTitle("Some thing wrong").setMessage("you need to fill all required fields").setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    public void RadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButtonYes:
                if (checked)
                    break;
            case R.id.radioButtonNo:
                if (checked)
                    break;
        }
    }

    public void updateDate(LocalDate date){
        TextView dateText = (TextView) findViewById(R.id.inputDate);
        dateText.setText(date.toString());
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date1");
    }

}