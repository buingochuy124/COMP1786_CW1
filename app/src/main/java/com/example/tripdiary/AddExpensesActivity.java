package com.example.tripdiary;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class AddExpensesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button AddExpense_Button;
    String[] typeExpenses = {"Food", "Travel", "Transport"};
    String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);


        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeExpenses);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);


        EditText expense_amount = findViewById(R.id.add_expense_amount_input);
        EditText expense_time = findViewById(R.id.add_expense_time_input);


        AddExpense_Button = findViewById(R.id.btn_addExpenseToDb);
        AddExpense_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = expense_amount.getText().toString();
                String time = expense_time.getText().toString();
                String expense_type = spinner.getSelectedItem().toString().trim();


                //Toast.makeText(AddExpensesActivity.this, ""+ amount + time+ expense_type, Toast.LENGTH_SHORT).show();

                tripId = getIntent().getStringExtra("expensesTripID");

                // Toast.makeText(AddExpensesActivity.this, ""+ tripId, Toast.LENGTH_SHORT).show();

                // Toast.makeText(AddExpensesActivity.this, ""+ time + amount + expense_type + tripId, Toast.LENGTH_SHORT).show();

                AddExpenseToTrip(expense_type, amount, time, Integer.valueOf(tripId));

            }
        });

    }


    public void showDatePickerDialog3(View v) {
        DialogFragment newFragment = new DatePickerExpenseFragment();
        newFragment.show(getSupportFragmentManager(), "date3");
    }

    public void AddExpenseToTrip(String type, String amount, String time, int tripId) {
        DbHelper db = new DbHelper(AddExpensesActivity.this);
        db.AddExpenseTripToDb(type, amount, time, tripId);
    }

    public void updateDate(LocalDate date) {
        TextView dateText = (TextView) findViewById(R.id.add_expense_time_input);
        dateText.setText(date.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(),typeExpenses[position] , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}