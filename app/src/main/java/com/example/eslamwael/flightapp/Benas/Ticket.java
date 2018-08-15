package com.example.eslamwael.flightapp.Benas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
public class Ticket implements Parcelable {

    String from;
    String to;

    @SerializedName("flight_number")
    String flightNumber;

    String departure;
    String arrival;
    String duration;
    String instructions;

    @SerializedName("stops")
    int numberOfStops;

    Airline airline;

    Price price;


    protected Ticket(Parcel in) {
        from = in.readString();
        to = in.readString();
        flightNumber = in.readString();
        departure = in.readString();
        arrival = in.readString();
        duration = in.readString();
        instructions = in.readString();
        numberOfStops = in.readInt();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDuration() {
        return duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public Airline getAirline() {
        return airline;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Ticket)) {
            return false;
        }

        return flightNumber.equalsIgnoreCase(((Ticket) obj).getFlightNumber());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.flightNumber != null ? this.flightNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(flightNumber);
        dest.writeString(departure);
        dest.writeString(arrival);
        dest.writeString(duration);
        dest.writeString(instructions);
        dest.writeInt(numberOfStops);
    }
}
