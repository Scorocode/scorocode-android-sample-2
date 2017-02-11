package com.peterstaranchuk.cleaningservicebusiness.view;

import android.content.Intent;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface OrderDetailView {

    void setOrderData(DocumentInfo orderData, DocumentInfo userData);

    void setInitialState();

    void setOrdersStatus(int orderStatusCode);

    Intent getIntent();

    void setUndoButtonVisible();

    void disableUndoButton();

    void setStateButtonText(String status);

    void showWarrantDialog();

    void setActionBar();

    void openPhoneScreen();

    void openMapScreen();

    void expandOrCollapseUserSection();

    void expandOrCollapseOrderSection();

    void expandOrCollapseAdditionalInfoSection();

    void showMoneyConfirmationDialog();

    void setStatusComplete();

    void showError(int error);

    void showAcceptWarrantDialog();
}
