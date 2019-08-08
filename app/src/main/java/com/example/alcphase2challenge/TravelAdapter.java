package com.example.alcphase2challenge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.TravelHolder>{
private ArrayList<TravelModel> travelModelArrayList;
private Context context;

    public TravelAdapter(Context context, ArrayList<TravelModel> travelModelArrayList) {
        this.travelModelArrayList = travelModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TravelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_travel, parent, false);
        return new TravelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelHolder holder, int position) {
        holder.country.setText(travelModelArrayList.get(position).getCountry());
        Log.e("country", travelModelArrayList.get(position).getImageURL() + "---------------------");
        holder.location.setText(travelModelArrayList.get(position).getHolidayLocation());
        holder.amount.setText(travelModelArrayList.get(position).getAmount());
        Picasso.get().load(travelModelArrayList.get(position).getImageURL()).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return travelModelArrayList.size();
    }

    public class TravelHolder extends RecyclerView.ViewHolder {
        TextView country, amount, location;
        ImageView imageView;
        public TravelHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.country);
            location = itemView.findViewById(R.id.location);
            amount = itemView.findViewById(R.id.amount);
            imageView = itemView.findViewById(R.id.itemImageView);



        }
    }
}
