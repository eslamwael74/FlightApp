package com.example.eslamwael.flightapp.Ui;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eslamwael.flightapp.Benas.Ticket;
import com.example.eslamwael.flightapp.R;
import com.example.eslamwael.flightapp.ViewModel.TicketItemViewModel;
import com.inq.eslamwael74.coremodule.Adapter.RecyclerViewAdapter;
import com.example.eslamwael.flightapp.databinding.MainItemBinding;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eslamwael74 on 7/8/2018.
 * Email: eslamwael74@outlook.com.
 */
public class MainAdapter extends RecyclerViewAdapter<Ticket, TicketItemViewModel> {

    TicketItemViewModel viewModel;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item, parent, false);

        viewModel = new TicketItemViewModel();
        MainItemBinding binding = MainItemBinding.bind(itemView);
        binding.setViewModel(viewModel);

        return new MyViewHolder(itemView, binding, viewModel);

    }

    public void setItems(ArrayList<Ticket> tickets) {
        items.clear();
        items.addAll(tickets);
        notifyDataSetChanged();
    }

    public void setItemPrice(Ticket ticket, int pos) {
        items.set(pos, ticket);
        if (viewModel != null)
            viewModel.isPrice(ticket.getPrice(), true);
        notifyItemChanged(pos);
    }

    public ArrayList<Ticket> getTickets() {
        return items;
    }

//    @Override
//    public int getItemCount() {
//        return tickets != null ? tickets.size() : 0;
//    }

    static class MyViewHolder extends ItemViewHolder<Ticket, TicketItemViewModel> {

        public MyViewHolder(View itemView, ViewDataBinding binding, TicketItemViewModel viewModel) {
            super(itemView, binding, viewModel);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onTicketClick() {
            viewModel.onClick();
        }
    }

}
