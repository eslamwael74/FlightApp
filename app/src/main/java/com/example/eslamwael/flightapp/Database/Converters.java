package com.example.eslamwael.flightapp.Database;

import android.arch.persistence.room.TypeConverter;

import com.example.eslamwael.flightapp.Benas.Airline;
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
    public static Object stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Object>>() {
        }.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(Object o) {
        return new Gson().toJson(o);
    }

}
