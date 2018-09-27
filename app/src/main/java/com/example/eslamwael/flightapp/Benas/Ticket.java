package com.example.eslamwael.flightapp.Benas;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.eslamwael.flightapp.Database.Converters;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
@Entity(tableName = "ticketsTable")
public class Ticket implements Parcelable {


    @PrimaryKey
    int id;

    @ColumnInfo(name = "from")
    String from;

    @ColumnInfo(name = "tooo")
    String to;

    @ColumnInfo(name = "flight_number")
    @SerializedName("flight_number")
    String flightNumber;

    @ColumnInfo(name = "departure_")
    String departure;

    @ColumnInfo(name = "arrival_")
    String arrival;

    @ColumnInfo(name = "duration_")
    String duration;

    @ColumnInfo(name = "instructions_")
    String instructions;

    @ColumnInfo(name = "stops_")
    @SerializedName("stops")
    int numberOfStops;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "airline_")
    public Airline airline;

//    @Embedded
    @TypeConverters(Converters.class)
    @ColumnInfo(name = "price_")
    public Price price;

    @ColumnInfo(name = "fetch_finished_")
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
        dest.writeInt(this.id);
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

    @Ignore
    protected Ticket(Parcel in) {
        this.id = in.readInt();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
