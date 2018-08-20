package com.inq.eslamwael74.coremodule.ViewModel;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inq.eslamwael74.coremodule.R;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public class BindingUtils {

    @BindingAdapter("recyclerViewViewModel")
    public static void setRecyclerViewViewModel(RecyclerView recyclerView, RecyclerViewViewModel viewModel) {
        viewModel.initRecyclerView(recyclerView);
    }


    @BindingAdapter("android:text")
    public static void setFloat(TextView view, float value) {
        if (Float.isNaN(value)) view.setText("");
        else view.setText(String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getFloat(TextView view) {
        String num = view.getText().toString();
        if (num.isEmpty()) return 0.0F;
        try {
            return Float.parseFloat(num);
        } catch (NumberFormatException e) {
            return 0.0F;
        }
    }


    @BindingAdapter({"logo"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    @BindingAdapter({"bind:visible"})
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter({"bind:fail_Visible"})
    public static void setFailVisibility(View view, int typeOfFail) {
        view.setVisibility(typeOfFail == 0 ? View.VISIBLE : View.GONE);
    }

//    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
//    public static void setOnRefreshListener(final SwipeRefreshLayout view,
//                                            final SwipeRefreshLayout.OnRefreshListener listener,
//                                            final InverseBindingListener refreshingAttrChanged) {
//
//        SwipeRefreshLayout.OnRefreshListener newValue = new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (listener != null) {
//                    if (refreshingAttrChanged != null) {
//                        refreshingAttrChanged.onChange();
//                    }
//                    listener.onRefresh();
//                }
//            }
//        };
//
//        SwipeRefreshLayout.OnRefreshListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.refresh);
//        if (oldValue != null) {
//            view.setOnRefreshListener(null);
//        }
//        view.setOnRefreshListener(newValue);
//    }


    @BindingAdapter("bind:onSwipeToRefresh")
   public static void setOnSwipeRefreshListener(SwipeRefreshLayout swipeRefreshLayout, final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshListener.onRefresh();
            }
        });
    }

    @BindingAdapter("bind:refresh_visibility")
    public static void setRefreshVisibility(SwipeRefreshLayout view, boolean isRefreshing) {
        if (isRefreshing != view.isRefreshing())
            view.setRefreshing(isRefreshing);
    }



}
