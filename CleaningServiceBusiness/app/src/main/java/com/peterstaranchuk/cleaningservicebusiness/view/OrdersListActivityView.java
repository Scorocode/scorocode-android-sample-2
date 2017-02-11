package com.peterstaranchuk.cleaningservicebusiness.view;

import java.util.List;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public interface OrdersListActivityView {
    void refreshOrdersList(List<DocumentInfo> orders);

    void showMessage(int message);

    void openOrderDetailsActivity(DocumentInfo orderInfo);

    void setActionBar();
}
