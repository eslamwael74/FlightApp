package com.example.eslamwael.flightapp.ViewModel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eslamwael.flightapp.Benas.Price;
import com.example.eslamwael.flightapp.Benas.Ticket;
import com.example.eslamwael.flightapp.Network.RetrofitWebService;
import com.example.eslamwael.flightapp.Ui.MainAdapter;
import com.inq.eslamwael74.coremodule.ViewModel.ViewModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
public class MainViewModel extends ViewModel {

    private Context context;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MainAdapter mainAdapter;
    private ArrayList<Ticket> ticketsList = new ArrayList<>();

    private static final String from = "DEL";
    private static final String to = "HYD";


    public MainViewModel(@Nullable State saveInstanceState) {
        super(saveInstanceState);

        ConnectableObservable<ArrayList<Ticket>> ticketsObservable = getTickets(from, to).replay();

        fetchTickets(ticketsObservable);
        fetchPriceOfTickets(ticketsObservable);

    }


    /**
     * Fetching all tickets first
     * Observable emits List<Ticket> at once
     * All the items will be added to RecyclerView
     */
    private void fetchTickets(ConnectableObservable<ArrayList<Ticket>> ticketsObservable) {
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ArrayList<Ticket>>() {
                            @Override
                            public void onNext(ArrayList<Ticket> tickets) {
                                ticketsList.clear();
                                ticketsList.addAll(tickets);
                                mainAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {
                                //Error
                                Log.d("TAG", "onError: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );


    }

    /**
     * Fetching individual ticket price
     * First FlatMap converts single List<Ticket> to multiple emissions
     * Second FlatMap makes HTTP call on each Ticket emission
     */
    private void fetchPriceOfTickets(ConnectableObservable<ArrayList<Ticket>> ticketsObservable) {
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        //Converting List<Ticket> emission to single Ticket emissions
                        .flatMap(new Function<ArrayList<Ticket>, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(ArrayList<Ticket> tickets) throws Exception {
                                return Observable.fromIterable(tickets);
                            }
                        })
                        //Fetching price on each Ticket emission
                        .flatMap(new Function<Ticket, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(Ticket ticket) throws Exception {
                                return getPriceObservable(ticket);
                            }
                        })
                        .subscribeWith(new DisposableObserver<Ticket>() {
                            @Override
                            public void onNext(Ticket ticket) {
                                int position = ticketsList.indexOf(ticket);

                                //tickets not available
                                if (position == -1) {
                                    return;
                                }

                                ticketsList.set(position, ticket);
                                mainAdapter.notifyItemChanged(position);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
        //calling connect to start emission
        ticketsObservable.connect();
    }


    /**
     * Making Retrofit call to fetch all tickets
     */
    private Observable<ArrayList<Ticket>> getTickets(String from, String to) {
        return RetrofitWebService.getService().searchTickets(from, to)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Making Retrofit call to get single ticket price
     * get price HTTP call returns Price object, but
     * map() operator is used to change the return type to Ticket
     */
    private Observable<Ticket> getPriceObservable(final Ticket ticket) {
        return RetrofitWebService.getService().getPrice(
                ticket.getFlightNumber(),
                ticket.getFrom(),
                ticket.getTo())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Price, Ticket>() {
                    @Override
                    public Ticket apply(Price price) {
                        ticket.setPrice(price);
                        return ticket;
                    }
                });
    }

    @BindingAdapter("listBinder")
    public void bind(@NonNull RecyclerView recyclerView, @NonNull ArrayList<Ticket> tickets) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
//            mainAdapter = new MainAdapter(tickets, context);
            recyclerView.setAdapter(mainAdapter);
        }
    }

    public ArrayList<Ticket> getTicketsList() {
        return ticketsList;
    }

}
