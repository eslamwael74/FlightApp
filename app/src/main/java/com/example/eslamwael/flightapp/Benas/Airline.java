package com.example.eslamwael.flightapp.Benas;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
@Entity
public class Airline implements Parcelable {

    @PrimaryKey
    int arid;
    String name;
    String logo;

    public Airline(){

    }

    protected Airline(Parcel in) {
        arid = in.readInt();
        name = in.readString();
        logo = in.readString();
    }

    public static final Creator<Airline> CREATOR = new Creator<Airline>() {
        @Override
        public Airline createFromParcel(Parcel in) {
            return new Airline(in);
        }

        @Override
        public Airline[] newArray(int size) {
            return new Airline[size];
        }
    };

    public int getArid() {
        return arid;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(arid);
        dest.writeString(name);
        dest.writeString(logo);
    }
}
