package com.peterstaranchuk.cleaningservicebusiness.presenter;

import android.content.Intent;

import com.peterstaranchuk.cleaningservicebusiness.model.OrderDetailModel;
import com.peterstaranchuk.cleaningservicebusiness.view.OrderDetailView;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrderDetailPresenter {
    private OrderDetailView view;
    private OrderDetailModel model;

    public OrderDetailPresenter(OrderDetailView view, OrderDetailModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate(Intent intent) {
        view.setInitialValues();

        DocumentInfo orderData = model.getOrderData(intent);
        DocumentInfo userData = model.getUserData();
        view.setOrderData(orderData, userData);
        view.setOrdersStatus(model.getOrderStatusStringFrom(model.getCurrentOrderStatus()));
    }

    public void onSetNextStatusButtonClicked() {

        CallbackDocumentSaved callbackDocumentSaved = new CallbackDocumentSaved() {
            @Override
            public void onDocumentSaved() {
                int newStatus = model.getCurrentOrderStatus();

                view.setOrdersStatus(model.getOrderStatusStringFrom(newStatus));
                view.setStateButtonText(model.getOrderStatusStringFrom(newStatus+1));

                if(newStatus > OrderDetailModel.STATUS_ACCEPTED) {
                    view.setUndoButtonVisible();
                } else {
                    view.disableUndoButton();
                }
            }

            @Override
            public void onDocumentSaveFailed(String errorCode, String errorMessage) {
                view.setOrdersStatus(model.getOrderStatusStringFrom(OrderDetailModel.STATUS_ERROR));
            }
        };

        final int status = model.getCurrentOrderStatus();
        String orderId = model.getOrderData(view.getIntent()).getId();

        if(status < OrderDetailModel.STATUS_IN_PROGRESS) {
            model.setNextStatus(orderId, callbackDocumentSaved);
        } else {
            view.showCompletedDialog();
        }


    }

    public void onUndoStatusButtonClicked() {
        int status = model.getCurrentOrderStatus();
        String orderId = model.getOrderData(view.getIntent()).getId();

        CallbackDocumentSaved callbackDocumentSaved = new CallbackDocumentSaved() {
            @Override
            public void onDocumentSaved() {
                int newStatus = model.getCurrentOrderStatus();
                String statusString = model.getOrderStatusStringFrom(newStatus);

                view.setOrdersStatus(statusString);
                if(newStatus == OrderDetailModel.STATUS_ACCEPTED) {
                    view.disableUndoButton();
                }

                view.setStateButtonText(statusString);
            }

            @Override
            public void onDocumentSaveFailed(String errorCode, String errorMessage) {

            }
        };

        if(status > 1) {
            model.setPreviousStatus(orderId, callbackDocumentSaved);
        }

    }

    public int getCurrentStatus() {
        return model.getCurrentOrderStatus();
    }
}
