package com.example.eslamwael.flightapp.Benas;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
public class Airline implements Parcelable {

    int id;
    String name;
    String logo;

    protected Airline(Parcel in) {
        id = in.readInt();
        name = in.readString();
        logo = in.readString();
    }

    public static final Creator<Airline> CREATOR = new Creator<Airline>() {
        @Override
        public Airline createFromParcel(Parcel in) {
            return new Airline(in);
        }

        @Override
        public Airline[] newArray(int size) {
            return new Airline[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    @BindingAdapter({"logo"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(logo);
    }
}
