package com.peterstaranchuk.cleaningservicebusiness.presenter;

import android.content.Intent;

import com.peterstaranchuk.cleaningservicebusiness.R;
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
        DocumentInfo orderData = model.getOrderData(intent);
        DocumentInfo userData = model.getUserData();

        model.setInitialState(orderData);

        view.setInitialState();
        view.setActionBar();

        view.setOrderData(orderData, userData);
        view.setOrdersStatus(model.getCurrentOrderStatus());
    }

    public void onSetNextStatusButtonClicked() {
        CallbackDocumentSaved callbackDocumentSaved = new CallbackDocumentSaved() {
            @Override
            public void onDocumentSaved() {
                if(model.getCurrentOrderStatus() == OrderDetailModel.STATUS_COMPLETE) {
                    view.showWarrantDialog();
                } else {
                    view.setOrdersStatus(model.getCurrentOrderStatus());
                }
            }

            @Override
            public void onDocumentSaveFailed(String errorCode, String errorMessage) {
                view.setOrdersStatus(OrderDetailModel.STATUS_ERROR);
            }
        };

        String orderId = model.getOrderData(view.getIntent()).getId();
        model.setNextStatus(orderId, callbackDocumentSaved);
    }

    public void onUndoStatusButtonClicked() {
        String orderId = model.getOrderData(view.getIntent()).getId();

        CallbackDocumentSaved callbackDocumentSaved = new CallbackDocumentSaved() {
            @Override
            public void onDocumentSaved() {
                view.setOrdersStatus(model.getCurrentOrderStatus());
            }

            @Override
            public void onDocumentSaveFailed(String errorCode, String errorMessage) {
                view.showError(R.string.errorDuringStatusChange);
            }
        };

        model.setPreviousStatus(orderId, callbackDocumentSaved);
    }

    public int getCurrentStatus() {
        return model.getCurrentOrderStatus();
    }

    public void setOrderCompletedState() {
        view.showMoneyConfirmationDialog();
        view.setStatusComplete();
    }

    public void onPhoneButtonClicked() {
        view.openPhoneScreen();
    }

    public void onMapButtonClicked() {
        view.openMapScreen();
    }

    public void onUserSectionButtonClicked() {
        view.expandOrCollapseUserSection();
    }

    public void onOrderSectionButtonClicked() {
        view.expandOrCollapseOrderSection();
    }

    public void onAdditionalInfoSectionButtonClicked() {
        view.expandOrCollapseAdditionalInfoSection();
    }
}
