package com.peterstaranchuk.cleaningservicebusiness.model;

import android.content.Context;
import android.content.Intent;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.activities.OrderDetailsActivity;
import com.peterstaranchuk.cleaningservicebusiness.helpers.DataStoreHelper;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackGetDocumentById;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrderDetailModel {
    public static final int STATUS_ERROR = -1;
    public static final int STATUS_PLACED = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_IN_PROGRESS = 2;
    public static final int STATUS_COMPLETE = 3;

    private Context context;
    private int currentStatus;

    public OrderDetailModel(Context context) {
        this.context = context;
        this.currentStatus = 0;
    }


    public DocumentInfo getUserData() {
        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);
        return dataStoreHelper.getUserInfo();
    }

    public DocumentInfo getOrderData(Intent intent) {
        if(intent == null) {
            return new DocumentInfo();
        }

        return (DocumentInfo) intent.getSerializableExtra(OrderDetailsActivity.EXTRA_ORDER_INFO);
    }

    public void setNextStatus(final String orderId, final CallbackDocumentSaved callback) {
        setStatus(orderId, ++currentStatus, callback);
    }

    public void setPreviousStatus(final String orderId, final CallbackDocumentSaved callback) {
        setStatus(orderId, --currentStatus, callback);
    }

    private void setStatus(String orderId, final int status, final CallbackDocumentSaved callback) {
        final Document document = new Document(context.getString(R.string.ordersCollection));
        document.getDocumentById(orderId, new CallbackGetDocumentById() {
            @Override
            public void onDocumentFound(DocumentInfo documentInfo) {
                document.updateDocument().set(context.getString(R.string.fieldOrderStatus), status);
                document.saveDocument(callback);
            }

            @Override
            public void onDocumentNotFound(String errorCode, String errorMessage) {
                callback.onDocumentSaveFailed(errorCode, errorMessage);
            }
        });
    }

    public String getOrderStatusStringFrom(int code) {
        switch (code) {
            case OrderDetailModel.STATUS_PLACED: return context.getString(R.string.status_placed);
            case OrderDetailModel.STATUS_ACCEPTED: return context.getString(R.string.status_accepted);
            case OrderDetailModel.STATUS_IN_PROGRESS: return context.getString(R.string.status_inprogress);
            case OrderDetailModel.STATUS_COMPLETE: return context.getString(R.string.status_completed);
        }

        return context.getString(R.string.status_error);
    }

    public int getCurrentOrderStatus() {
        return currentStatus;
    }

    public void setInitialState(DocumentInfo orderData) {
         this.currentStatus = orderData.getInteger(context.getString(R.string.fieldOrderStatus));
    }
}
