package com.example.eslamwael.flightapp.Benas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
public class Price implements Parcelable {

    float price;
    String seats;
    String currency;

    @SerializedName("flight_number")
    String flightNumber;

    String from;
    String to;

    boolean isVisible = false;


    public float getPrice() {
        return price;
    }

    public String getSeats() {
        return seats;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.price);
        dest.writeString(this.seats);
        dest.writeString(this.currency);
        dest.writeString(this.flightNumber);
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeByte(this.isVisible ? (byte) 1 : (byte) 0);
    }

    public Price() {
    }

    protected Price(Parcel in) {
        this.price = in.readFloat();
        this.seats = in.readString();
        this.currency = in.readString();
        this.flightNumber = in.readString();
        this.from = in.readString();
        this.to = in.readString();
        this.isVisible = in.readByte() != 0;
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}


