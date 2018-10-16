package com.example.eslamwael.flightapp;

import android.databinding.Bindable;

import com.example.eslamwael.flightapp.BR;
import com.example.eslamwael.flightapp.Benas.Airline;
import com.example.eslamwael.flightapp.Benas.Price;
import com.example.eslamwael.flightapp.Benas.Ticket;
import com.inq.eslamwael74.coremodule.ViewModel.ItemViewModel;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public class TicketItemViewModel extends ItemViewModel<Ticket> {

    private Ticket ticket;

    @Override
    public void setItem(Ticket item) {
        ticket = item;
        notifyChange();
    }

    public void isPrice(Price price, boolean isPrice) {
        price.setVisible(isPrice);
        notifyPropertyChanged(BR.ticketPrice);
    }

    public void onClick() {
        notifyPropertyChanged(BR.flightNumber);
    }

    @Bindable
    public String getFrom() {
        return ticket.getFrom();
    }

    @Bindable
    public String getTo() {
        return ticket.getTo();
    }

    @Bindable
    public String getFlightNumber() {
        return ticket.getFlightNumber();
    }

    @Bindable
    public String getDeparture() {
        return ticket.getDeparture();
    }

    @Bindable
    public String getArrival() {
        return ticket.getArrival();
    }

    @Bindable
    public String getDuration() {
        return ticket.getDuration();
    }

    @Bindable
    public String getInstructions() {
        return ticket.getInstructions();
    }

    @Bindable
    public int getNumberOfStops() {
        return ticket.getNumberOfStops();
    }

    @Bindable
    public Airline getAirline() {
        return ticket.getAirline();
    }

    @Bindable
    public Price getPrice() {
        return ticket.getPrice();
    }

    @Bindable
    public boolean isTicketPrice() {
        return ticket.getPrice() != null && ticket.getPrice().isVisible();
    }

}
