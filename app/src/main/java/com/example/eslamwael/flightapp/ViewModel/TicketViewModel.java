package com.example.eslamwael.flightapp.ViewModel;

import android.arch.persistence.room.Room;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.eslamwael.flightapp.Utils.Const.DB_NAME;

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
            ConnectableObservable<List<Ticket>> ticketsObservable = getTickets(from, to).replay();
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();


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

        ConnectableObservable<List<Ticket>> ticketsObservable = getTickets(from, to).replay();
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


    AppDatabase appDatabase;

    /**
     * Making Retrofit call to fetch all tickets
     */
    private Observable<List<Ticket>> getTickets(String from, String to) {

        Observable<List<Ticket>>  dbObservable = (Observable<List<Ticket>>) appDatabase.ticketDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Ticket>>() {
                               @Override
                               public void accept(List<Ticket> tickets) throws Exception {
                                   Log.e("TAGDB", "getTicketsSizeFrom DATABASE: " + tickets.size());
                               }
                           });

                        Observable <List<Ticket>> ticketObservable =
                                RetrofitWebService
                                        .getService()
                                        .searchTickets(from, to)
                                        .map(tickets -> {

                                            Observable.create(emitter -> {
                                                appDatabase.ticketDao().insertAll(tickets);
                                                emitter.onComplete();
                                            }).subscribeOn(Schedulers.computation())
                                                    .subscribe();
                                            return tickets;

                                        })
                                        .toObservable()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());

        return Observable
                .concat(dbObservable, ticketObservable)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Fetching all tickets first
     * Observable emits List<Ticket> at once
     * All the items will be added to RecyclerView
     */
    private void fetchTickets(ConnectableObservable<List<Ticket>> ticketsObservable) {
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Ticket>>() {
                            @Override
                            public void onNext(List<Ticket> tickets) {
                                ticketsList = tickets;
                                ticket = tickets.get(tickets.size() - 1);
                                adapter.setItems(ticketsList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //Error
                                Log.d("TAG", "onError: " + e.getMessage());
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
    private void fetchPriceOfTickets(ConnectableObservable<List<Ticket>> ticketsObservable) {
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        //Converting List<Ticket> emission to single Ticket emissions
                        .flatMap((Function<List<Ticket>, ObservableSource<Ticket>>) Observable::fromIterable)
                        //Fetching price on each Ticket emission
                        .flatMap((Function<Ticket, ObservableSource<Ticket>>) this::getPriceObservable)
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
        ticketsObservable.connect();
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

        private final ArrayList<Ticket> tickets;
        private ObservableBoolean isRefreshing;

        public TicketState(TicketViewModel viewModel) {
            super(viewModel);
            tickets = viewModel.adapter.getTickets();
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
