package com.inq.eslamwael74.coremodule.ViewModel;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public class ViewModel extends BaseObservable {

    protected ViewModel(@Nullable State saveInstanceState){

    }

    @CallSuper
    public void onStart(){

    }

    public State getInstanceState(){
        return new State(this);
    }

    @CallSuper
    public void onStop(){

    }


    /**
     * Static class to save the states of this App.
     */
    public static class State implements Parcelable{

        public State(ViewModel viewModel){

        }

        protected State(Parcel in) {
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

}
