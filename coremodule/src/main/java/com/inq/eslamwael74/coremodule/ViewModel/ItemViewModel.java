package com.inq.eslamwael74.coremodule.ViewModel;

import android.support.annotation.Nullable;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public abstract class ItemViewModel<ITEM_T> extends ViewModel {

    protected ItemViewModel() {
        super(null);
    }

    public abstract void setItem(ITEM_T item);

}
