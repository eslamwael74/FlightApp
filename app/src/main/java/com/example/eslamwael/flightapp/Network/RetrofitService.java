package com.example.eslamwael.flightapp.Network;

import com.example.eslamwael.flightapp.Benas.Price;
import com.example.eslamwael.flightapp.Benas.Ticket;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
public interface RetrofitService {

    @GET("airline-tickets.php")
    Single<ArrayList<Ticket>> searchTickets(@Query("from") String from,
                                            @Query("to") String to);

    @GET("airline-tickets-price.php")
    Single<Price> getPrice(@Query("flight_number") String flightNumber,
                           @Query("from") String from,
                           @Query("to") String to);


}
