package com.example.tripdiary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    ArrayList<String> expensesId, expensesType, expensesAmount, expensesTime;
    int tripId;

    public ExpensesAdapter(Context context,
                           Activity activity,
                           ArrayList expensesId,
                           ArrayList expensesType,
                           ArrayList expensesAmount,
                           ArrayList expensesTime,
                           int tripiD) {
        this.context = context;
        this.activity = activity;
        this.expensesId = expensesId;
        this.expensesType = expensesType;
        this.expensesAmount = expensesAmount;
        this.expensesTime = expensesTime;
        this.tripId = tripiD;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.expenses_id_text.setText("No " + (position + 1));
        holder.expenses_type_text.setText("Type: " + expensesType.get(position));
        holder.expenses_amount_text.setText("Amount: " + expensesAmount.get(position));
        holder.expenses_time_text.setText("Date: " + expensesTime.get(position));


        holder.expenses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExpensesUpdateActivity.class);

                intent.putExtra("tripId", String.valueOf(tripId));
                intent.putExtra("expensesId", String.valueOf(expensesId.get(position)));
                intent.putExtra("expensesType", String.valueOf(expensesType.get(position)));
                intent.putExtra("expensesAmount", String.valueOf(expensesAmount.get(position)));
                intent.putExtra("expensesTime", String.valueOf(expensesTime.get(position)));


                activity.startActivityForResult(intent, 1);


            }
        });


    }

    @Override
    public int getItemCount() {
        return expensesId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expenses_id_text, expenses_type_text, expenses_amount_text, expenses_time_text;
        LinearLayout expenses_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expenses_id_text = itemView.findViewById(R.id.expenses_id_text);
            expenses_type_text = itemView.findViewById(R.id.expenses_type_text);
            expenses_amount_text = itemView.findViewById(R.id.expenses_amount_text);
            expenses_time_text = itemView.findViewById(R.id.expenses_time_text);
            expenses_layout = itemView.findViewById(R.id.expense_layout);

        }
    }
}
