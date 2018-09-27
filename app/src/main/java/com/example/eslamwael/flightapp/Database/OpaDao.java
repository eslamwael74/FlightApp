package com.example.eslamwael.flightapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.eslamwael.flightapp.Benas.Ticket;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by EslamWael74 on 9/3/2018.
 * Email: eslamwael74@outlook.com
 */
@Dao
public interface OpaDao {

    @Query("SELECT * FROM ticketsTable")
    Flowable<List<Ticket>> getAll();

    @Query("SELECT * FROM ticketsTable WHERE id IN (:ids)")
    Flowable<List<Ticket>>  loadAllByIds(int[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ticket> tickets);

    @Delete
    void delete(Ticket ticket);
}
