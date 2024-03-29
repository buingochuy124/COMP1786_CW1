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

public class SearchTripAdapter extends RecyclerView.Adapter<SearchTripAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    ArrayList<String> tripId, tripName, tripDestination, tripDate, tripRequireAssessement, tripDescription;
    int position;


    public SearchTripAdapter(Context context,
                             SearchActivity searchActivity,
                             ArrayList<String> tripId,
                             ArrayList<String> tripName,
                             ArrayList<String> tripDestination,
                             ArrayList<String> tripDate,
                             ArrayList<String> tripRequireAssessement,
                             ArrayList<String> tripDescription) {
        this.context = context;
        this.activity = activity;
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.tripDate = tripDate;
        this.tripRequireAssessement = tripRequireAssessement;
        this.tripDescription = tripDescription;
    }

    @NonNull
    @Override
    public SearchTripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_search_row, parent, false);
        return new SearchTripAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTripAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
//        holder.trip_id_text.setText(String.valueOf(tripId.get(position)));
//        holder.trip_name_text.setText(String.valueOf(tripName.get(position)));
//        holder.trip_destination_text.setText(String.valueOf(tripDestination.get(position)));
//        holder.trip_date_text.setText(String.valueOf(tripDate.get(position)));


        holder.trip_id_text.setText("No " + (position + 1));
        holder.trip_name_text.setText("Trip name: " + tripName.get(position));
        holder.trip_destination_text.setText("Destination: " + tripDestination.get(position));
        holder.trip_date_text.setText("Date: " + tripDate.get(position));


        String check = "";
        if (String.valueOf(tripRequireAssessement.get(position)).equals("1")) {
            check = "yes";
        } else {
            check = "no";
        }
        check = "Risk Assessement: " + check;
        holder.trip_requireAssessement_text.setText(check);


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TripUpdateActivity.class);

                intent.putExtra("tripId", String.valueOf(tripId.get(position)));
                intent.putExtra("tripName", String.valueOf(tripName.get(position)));
                intent.putExtra("tripDestination", String.valueOf(tripDestination.get(position)));
                intent.putExtra("tripDate", String.valueOf(tripDate.get(position)));
                intent.putExtra("tripRequireAssessement", String.valueOf(tripRequireAssessement.get(position)));
                intent.putExtra("tripDescription", String.valueOf(tripDescription.get(position)));


                activity.startActivityForResult(intent, 1);


            }
        });
    }

    @Override
    public int getItemCount() {
        return tripId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_text, trip_name_text, trip_destination_text, trip_date_text, trip_requireAssessement_text;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_text = itemView.findViewById(R.id.trip_id_text);
            trip_name_text = itemView.findViewById(R.id.trip_name_text);
            trip_destination_text = itemView.findViewById(R.id.trip_destination_text);
            trip_date_text = itemView.findViewById(R.id.trip_date_text);
            trip_requireAssessement_text = itemView.findViewById(R.id.trip_requireAssessement_text);
            mainLayout = itemView.findViewById(R.id.tripsearchlayout);

        }
    }
}
