package com.inq.eslamwael74.coremodule.Adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inq.eslamwael74.coremodule.ViewModel.ItemViewModel;

import java.util.ArrayList;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public abstract class RecyclerViewAdapter<ITEM_T,VIEW_MODEL_T extends ItemViewModel<ITEM_T>>
        extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder<ITEM_T,VIEW_MODEL_T>>{

    protected final ArrayList<ITEM_T> items;

    protected RecyclerViewAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public final void onBindViewHolder(ItemViewHolder<ITEM_T, VIEW_MODEL_T> holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder<T,VM extends ItemViewModel<T>>
            extends RecyclerView.ViewHolder {

        protected final ViewDataBinding binding;
        protected final VM viewModel;

        public ItemViewHolder(View itemView, ViewDataBinding binding, VM viewModel) {
            super(itemView);
            this.binding = binding;
            this.viewModel = viewModel;
        }

        void setItem(T item){
            viewModel.setItem(item);
            binding.executePendingBindings();
        }
    }

}
