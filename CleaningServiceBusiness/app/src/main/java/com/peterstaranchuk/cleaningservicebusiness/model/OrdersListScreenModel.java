package com.peterstaranchuk.cleaningservicebusiness.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservicebusiness.R;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.Query;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersListScreenModel {
    public static final int STATUS_ORDER_PLACED = 0;

    private Context context;

    public OrdersListScreenModel(Context context) {
        this.context = context;
    }

    @NonNull
    public void getOrdersList(CallbackFindDocument callback) {
        Query query = new Query(context.getString(R.string.ordersCollection));
        query.equalTo(context.getString(R.string.orderStatusField), STATUS_ORDER_PLACED);
        query.findDocuments(callback);
    }
}
