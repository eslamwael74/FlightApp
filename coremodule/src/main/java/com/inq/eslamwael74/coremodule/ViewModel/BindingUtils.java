package com.inq.eslamwael74.coremodule.ViewModel;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public class BindingUtils {

    @BindingAdapter("recyclerViewViewModel")
    public static void setRecyclerViewViewModel(RecyclerView recyclerView,RecyclerViewViewModel viewModel){
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
        if(num.isEmpty()) return 0.0F;
        try {
            return Float.parseFloat(num);
        } catch (NumberFormatException e) {
            return 0.0F;
        }
    }

    @BindingAdapter({"bind:visible"})
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.GONE : View.VISIBLE);
    }


}
