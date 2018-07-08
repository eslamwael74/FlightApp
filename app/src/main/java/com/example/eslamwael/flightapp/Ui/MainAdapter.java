package com.example.eslamwael.flightapp.Ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eslamwael.flightapp.Benas.Airline;
import com.example.eslamwael.flightapp.Benas.Ticket;
import com.example.eslamwael.flightapp.R;
import com.example.eslamwael.flightapp.databinding.MainItemBinding;

import java.util.ArrayList;

/**
 * Created by eslamwael74 on 7/8/2018.
 * Email: eslamwael74@outlook.com.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

   private ArrayList<Ticket> tickets;
   private Context context;
   private LayoutInflater layoutInflater;

    public MainAdapter(ArrayList<Ticket> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater != null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        MainItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.main_item,parent,false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tickets != null ? tickets.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final MainItemBinding binding;

        public MyViewHolder(MainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
