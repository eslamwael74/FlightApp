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

    boolean isFetchFinished;

    public boolean isFetchFinished() {
        return isFetchFinished;
    }

    public void setFetchFinished(boolean fetchFinished) {
        isFetchFinished = fetchFinished;
    }


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
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeString(this.flightNumber);
        dest.writeString(this.departure);
        dest.writeString(this.arrival);
        dest.writeString(this.duration);
        dest.writeString(this.instructions);
        dest.writeInt(this.numberOfStops);
        dest.writeParcelable(this.airline, flags);
        dest.writeParcelable(this.price, flags);
        dest.writeByte(this.isFetchFinished ? (byte) 1 : (byte) 0);
    }

    public Ticket() {
    }

    protected Ticket(Parcel in) {
        this.from = in.readString();
        this.to = in.readString();
        this.flightNumber = in.readString();
        this.departure = in.readString();
        this.arrival = in.readString();
        this.duration = in.readString();
        this.instructions = in.readString();
        this.numberOfStops = in.readInt();
        this.airline = in.readParcelable(Airline.class.getClassLoader());
        this.price = in.readParcelable(Price.class.getClassLoader());
        this.isFetchFinished = in.readByte() != 0;
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel source) {
            return new Ticket(source);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };
}
