package com.example.eslamwael.flightapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.eslamwael.flightapp.Benas.Airline;
import com.example.eslamwael.flightapp.Benas.Price;
import com.example.eslamwael.flightapp.Benas.Ticket;

/**
 * Created by EslamWael74 on 9/2/2018.
 * Email: eslamwael74@outlook.com
 */
@Database(entities = {Ticket.class, Airline.class, Price.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TicketDao ticketDao();

}
