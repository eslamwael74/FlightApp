package com.example.eslamwael.flightapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.eslamwael.flightapp.Benas.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by EslamWael74 on 9/2/2018.
 * Email: eslamwael74@outlook.com
 */
@Dao
public interface TicketDao {

    @Query("SELECT * FROM ticket")
    Flowable<List<Ticket>> getAll();

    @Query("SELECT * FROM ticket WHERE id IN (:ids)")
    Flowable<List<Ticket>>  loadAllByIds(int[] ids);

    @Insert
    void insertAll(List<Ticket> tickets);

    @Delete
    void delete(Ticket ticket);

}
