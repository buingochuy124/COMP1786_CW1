package com.example.tripdiary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class ExpensesUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String expensesId, expensesType, expensesAmount, expensesTime, tripId;
    String[] typeExpenses = {"Food", "Travel", "Transport", "Drink"};
    EditText inputDate_ExpenseUpdate, inputAmount_update, dateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_update);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeExpenses);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);


        inputDate_ExpenseUpdate = findViewById(R.id.inputDate_ExpenseUpdate);
        inputAmount_update = findViewById(R.id.inputAmount_update);

        getAndSetIntentData();
        tripId = getIntent().getStringExtra("tripId");

        Button button_save_expense_update = findViewById(R.id.button_save_expense_update);
        button_save_expense_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputDate_ExpenseUpdate = findViewById(R.id.inputDate_ExpenseUpdate);
                EditText inputAmount_update = findViewById(R.id.inputAmount_update);
                String expense_type = spinner.getSelectedItem().toString().trim();

//                Toast.makeText(ExpensesUpdateActivity.this, tripId + inputDate_ExpenseUpdate.getText().toString() + inputAmount_update.getText().toString() + expense_type, Toast.LENGTH_SHORT).show();
                DbHelper db = new DbHelper(ExpensesUpdateActivity.this);


                if (inputAmount_update.getText().toString().isEmpty() ||
                        inputDate_ExpenseUpdate.getText().toString().isEmpty()) {
                    displayRequireAlert();
                } else {
                    db.updateExpense(expensesId, tripId, expense_type, inputAmount_update.getText().toString(), inputDate_ExpenseUpdate.getText().toString());
                }


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

    public void updateDate(LocalDate date) {
        TextView dateText = (TextView) findViewById(R.id.inputDate_ExpenseUpdate);
        dateText.setText(date.toString());
    }


    public void showDatePickerDialogExpensesUpdate(View v) {
        DialogFragment newFragment = new DateExpenseUpdateFragment();
        newFragment.show(getSupportFragmentManager(), "date2");
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("tripId")
                && getIntent().hasExtra("expensesId")
                && getIntent().hasExtra("expensesType")
                && getIntent().hasExtra("expensesAmount")
                && getIntent().hasExtra("expensesTime")) {

            expensesId = getIntent().getStringExtra("expensesId");
            expensesType = getIntent().getStringExtra("expensesType");
            expensesAmount = getIntent().getStringExtra("expensesAmount");
            expensesTime = getIntent().getStringExtra("expensesTime");

            inputDate_ExpenseUpdate.setText(expensesTime);
            inputAmount_update.setText(expensesAmount);


        } else {
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}