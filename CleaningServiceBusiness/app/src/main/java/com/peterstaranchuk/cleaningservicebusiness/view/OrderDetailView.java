package com.peterstaranchuk.cleaningservicebusiness.view;

import android.content.Intent;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface OrderDetailView {

    void setOrderData(DocumentInfo orderData, DocumentInfo userData);

    void setInitialValues();

    void setOrdersStatus(int ordersStatus);

    void setCompleteState();

    Intent getIntent();

    void setUndoButtonVisible();

    void disableUndoButton();

    void setStateButtonText(int status);

    void showCompletedDialog();
}