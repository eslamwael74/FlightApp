package com.example.eslamwael.flightapp;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eslamwael.flightapp.Benas.Ticket;
import com.example.eslamwael.flightapp.Database.AppDatabase;
import com.example.eslamwael.flightapp.Network.RetrofitWebService;
import com.example.eslamwael.flightapp.Ui.MainAdapter;
import com.inq.eslamwael74.coremodule.Adapter.RecyclerViewAdapter;
import com.inq.eslamwael74.coremodule.ViewModel.RecyclerViewViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EslamWael74 on 8/11/2018.
 * Email: eslamwael74@outlook.com
 */
public class TicketViewModel extends RecyclerViewViewModel {

    private final Context context;
    MainAdapter adapter;
    private List<Ticket> ticketsList = new ArrayList<>();
    private Ticket ticket;

    private static final String from = "DEL";
    private static final String to = "HYD";
    private CompositeDisposable disposable = new CompositeDisposable();


    public ObservableBoolean isSwipeToRefreshing = new ObservableBoolean();
    public ObservableBoolean isRefreshing = new ObservableBoolean();


    private int typeOfFail = 1;
    public int hideProgress = 0;

    public TicketViewModel(@Nullable State saveInstanceState, Context context) {
        super(saveInstanceState);
        this.context = context.getApplicationContext();

        if (saveInstanceState instanceof TicketState) {
            ticketsList = ((TicketState) saveInstanceState).tickets;
            isRefreshing = ((TicketState) saveInstanceState).isRefreshing;
        } else {
            Observable<List<Ticket>> ticketsObservable = getTickets(getTicketsFromDatabase(), getTicketsFromNetwork(from, to));


            fetchTickets(ticketsObservable);
            fetchPriceOfTickets(ticketsObservable);

        }
        adapter = new MainAdapter();
        adapter.setItems(ticketsList);
    }

    private void refreshData() {
        isRefreshing.set(false);
        isSwipeToRefreshing.set(true);
        ticketsList = new ArrayList<>();

        Observable<List<Ticket>> ticketsObservable = getTicketsFromNetwork(from, to).replay();
        fetchTickets(ticketsObservable);
        fetchPriceOfTickets(ticketsObservable);

        adapter.setItems(ticketsList);
    }

//    public Runnable refreshRunnable = new Runnable() {
//        @Override
//        public void run() {
//            refreshData();
//        }
//    };

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::refreshData;


    @Override
    protected RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(context);
    }

    @Override
    public RecyclerViewViewModelState getInstanceState() {
        return new TicketState(this);
    }


    /**
     * Making Retrofit call to fetch all tickets
     */

    private Observable<List<Ticket>> getTickets(Observable<List<Ticket>> o1, Observable<List<Ticket>> o2) {
        return Observable
                .concat(o2.doOnNext(tickets -> {
                    AppDatabase.getInstance(context)
                            .opaDao().deleteAll();
                    AppDatabase
                            .getInstance(context.getApplicationContext())
                            .opaDao()
                            .insertAll(tickets);
                }).subscribeOn(AndroidSchedulers.mainThread()
                ), o1.subscribeOn(AndroidSchedulers.mainThread()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Ticket>> getTicketsFromNetwork(String from, String to) {
        return RetrofitWebService
                .getService()
                .searchTickets(from, to)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                );
    }

    private Observable<List<Ticket>> getTicketsFromDatabase() {
        return AppDatabase
                .getInstance(context.getApplicationContext())
                .opaDao()
                .getAll()
                .filter(tickets -> tickets != null && tickets.size() != 0)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    /**
     * Fetching all tickets first
     * Observable emits List<Ticket> at once
     * All the items will be added to RecyclerView
     */
    private void fetchTickets(Observable<List<Ticket>> ticketsObservable) {
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Ticket>>() {
                            @Override
                            public void onNext(List<Ticket> tickets) {
                                ticketsList.clear();
                                ticketsList.addAll(tickets);
                                if (ticketsList.size() != 0) {
                                    ticket = tickets.get(tickets.size() - 1);
                                }
                                adapter.setItems(ticketsList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //Error
                                Log.d("TAG", "onError: " + e.getMessage());
                                if (ticketsList.size() == 0)
                                    typeOfFail = 0;
                                hideProgress = 1;
                                notifyChange();
                            }

                            @Override
                            public void onComplete() {
                                isRefreshing.set(true);
                                isSwipeToRefreshing.set(false);
                            }
                        })
        );
    }

    /**
     * Fetching individual ticket price
     * First FlatMap converts single List<Ticket> to multiple emissions
     * Second FlatMap makes HTTP call on each Ticket emission
     */
    private void fetchPriceOfTickets(Observable<List<Ticket>> ticketsObservable) {
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        //Converting List<Ticket> emission to single Ticket emissions
                        .flatMap(new Function<List<Ticket>, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(List<Ticket> tickets) throws Exception {
                                return Observable.fromIterable(tickets);
                            }
                        })
                        //Fetching price on each Ticket emission
                        .flatMap(new Function<Ticket, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(Ticket ticket) throws Exception {
                                return getPriceObservableNetwork(ticket);
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

                                adapter.setItemPrice(ticket, position);

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
        ticketsObservable.subscribe();
    }

    /**
     * Making Retrofit call to get single ticket price
     * get price HTTP call returns Price object, but
     * map() operator is used to change the return type to Ticket
     */
    private Observable<Ticket> getPriceObservableNetwork(final Ticket ticket) {
        return RetrofitWebService.getService().getPrice(
                ticket.getFlightNumber(),
                ticket.getFrom(),
                ticket.getTo())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(price -> {
                    ticket.setPrice(price);
                    return ticket;
                });
    }

    /*
    return ticket list if not null.
     */
    public List<Ticket> getTicketsList() {
        return ticketsList;
    }

    public int onFail() {
        return typeOfFail;
    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }

    private static class TicketState extends RecyclerViewViewModelState {

        private final List<Ticket> tickets;
        private ObservableBoolean isRefreshing;

        public TicketState(TicketViewModel viewModel) {
            super(viewModel);
            tickets = viewModel.adapter.getItems();
            isRefreshing = viewModel.isRefreshing;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeTypedList(this.tickets);
            dest.writeParcelable(this.isRefreshing, flags);
        }

        protected TicketState(Parcel in) {
            super(in);
            this.tickets = in.createTypedArrayList(Ticket.CREATOR);
            this.isRefreshing = in.readParcelable(ObservableBoolean.class.getClassLoader());
        }

    }
}
