package com.inq.eslamwael74.coremodule.ViewModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.inq.eslamwael74.coremodule.Adapter.RecyclerViewAdapter;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public abstract class RecyclerViewViewModel extends ViewModel {

    private RecyclerView.LayoutManager layoutManager;
    private Parcelable savedLayoutManagerState;

    protected abstract RecyclerViewAdapter getAdapter();
    protected abstract RecyclerView.LayoutManager createLayoutManager();


    protected RecyclerViewViewModel(@Nullable State saveInstanceState) {
        super(saveInstanceState);
        if (saveInstanceState instanceof RecyclerViewViewModelState){
            savedLayoutManagerState =
                    ((RecyclerViewViewModelState) saveInstanceState).layoutManagerState;
        }
    }

    @Override
    public RecyclerViewViewModelState getInstanceState(){
        return new RecyclerViewViewModelState(this);
    }

    public final void initRecyclerView(RecyclerView recyclerView){
        layoutManager = createLayoutManager();
        if (savedLayoutManagerState != null){
            layoutManager.onRestoreInstanceState(savedLayoutManagerState);
            savedLayoutManagerState = null;
        }
        recyclerView.setAdapter(getAdapter());
        recyclerView.setLayoutManager(layoutManager);
    }

    protected static class RecyclerViewViewModelState extends State {

        private final Parcelable layoutManagerState;

        public RecyclerViewViewModelState(RecyclerViewViewModel viewModel) {
            super(viewModel);
            layoutManagerState = viewModel.layoutManager.onSaveInstanceState();
        }

        public RecyclerViewViewModelState(Parcel in) {
            super(in);
            layoutManagerState = in.readParcelable(
                    RecyclerView.LayoutManager.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(layoutManagerState, flags);
        }

    }
}
