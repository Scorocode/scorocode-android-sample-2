package com.peterstaranchuk.cleaningservicebusiness.presenter;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservicebusiness.view.OrdersListActivityView;

import java.util.List;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersListScreenPresenter {
    private OrdersListActivityView view;
    private OrdersListScreenModel model;

    public OrdersListScreenPresenter(OrdersListActivityView view, OrdersListScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void refreshOrdersList() {
        CallbackFindDocument callbackFindDocument = new CallbackFindDocument() {
            @Override
            public void onDocumentFound(List<DocumentInfo> documentInfos) {
                documentInfos = model.filterList(documentInfos);
                view.refreshOrdersList(documentInfos);
            }

            @Override
            public void onDocumentNotFound(String errorCode, String errorMessage) {
                view.showMessage(R.string.cant_getOrders);
            }
        };

        model.getOrdersList(callbackFindDocument);
    }

    public void onListItemClicked(DocumentInfo orderInfo) {
        view.openOrderDetailsActivity(orderInfo);
    }

    public void onCreate() {
        view.setActionBar();
    }
}
