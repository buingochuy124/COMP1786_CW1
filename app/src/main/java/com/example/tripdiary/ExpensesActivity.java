package com.example.tripdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_expense_button;
    DbHelper db;
    ArrayList<String> expensesId, expensesType, expensesAmount, expensesTime;
    ExpensesAdapter expensesAdapter;
    String expensesTripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        recyclerView = findViewById(R.id.expenses_recyclerView);
        add_expense_button = findViewById(R.id.add_expense_button);

        //expensesTripID = getIntent().getStringExtra("expensesTripID");
        getIntentTripId();
        add_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpensesActivity.this, AddExpensesActivity.class);
                // startActivity(intent,);


                intent.putExtra("expensesTripID", String.valueOf(expensesTripID));
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });


        db = new DbHelper(ExpensesActivity.this);
        expensesId = new ArrayList<>();
        expensesType = new ArrayList<>();
        expensesAmount = new ArrayList<>();
        expensesTime = new ArrayList<>();

        ExpensesInArrays(Integer.valueOf(expensesTripID));

        //Toast.makeText(this, expensesTripID + expensesType + expensesTime + expensesAmount, Toast.LENGTH_SHORT).show();

        expensesAdapter = new ExpensesAdapter(
                ExpensesActivity.this,
                this,
                expensesId,
                expensesType,
                expensesAmount,
                expensesTime,
                Integer.valueOf(expensesTripID));

        recyclerView.setAdapter(expensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpensesActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    public void ExpensesInArrays(int tripId) {
        Cursor cursor = db.readAllExpensesWithTripID(tripId);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                expensesId.add(cursor.getString(0));
                expensesType.add(cursor.getString(1));
                expensesAmount.add(cursor.getString(2));
                expensesTime.add(cursor.getString(3));
            }
        } else {
            Toast.makeText(this, "There are no expenses", Toast.LENGTH_SHORT).show();
        }
    }

    public void getIntentTripId() {

        expensesTripID = getIntent().getStringExtra("expensesTripID");
        //expensesTripID = "2";


        // Toast.makeText(this, expensesTripID, Toast.LENGTH_SHORT).show();

    }

}