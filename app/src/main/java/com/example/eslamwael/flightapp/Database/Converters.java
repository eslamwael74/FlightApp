package com.example.eslamwael.flightapp.Database;

import android.arch.persistence.room.TypeConverter;

import com.example.eslamwael.flightapp.Benas.Airline;
import com.example.eslamwael.flightapp.Benas.Price;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by EslamWael74 on 9/2/2018.
 * Email: eslamwael74@outlook.com
 */
public class Converters {

    @TypeConverter
    public static Airline stringAirlineToObject(String data) {
        if (data == null) {
            return null;
        }
        return new Gson().fromJson(data, Airline.class);
    }

    @TypeConverter
    public static String objectAirlineToString(Airline o) {
        return new Gson().toJson(o);
    }


    @TypeConverter
    public static Price stringPriceToObject(String data) {
        if (data == null) {
            return null;
        }
        return new Gson().fromJson(data, Price.class);
    }

    @TypeConverter
    public static String objectPriceToString(Price o) {
        return new Gson().toJson(o);
    }

}
