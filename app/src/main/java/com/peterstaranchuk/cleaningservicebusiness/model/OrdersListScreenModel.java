package com.peterstaranchuk.cleaningservicebusiness.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.helpers.DataStoreHelper;

import java.util.ArrayList;
import java.util.List;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;
import ru.profit_group.scorocode_sdk.scorocode_objects.Query;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersListScreenModel {

    private Context context;

    public OrdersListScreenModel(Context context) {
        this.context = context;
    }

    @NonNull
    public void getOrdersList(CallbackFindDocument callback) {
        Query query = new Query(context.getString(R.string.ordersCollection));
        query.findDocuments(callback);
    }

    public List<DocumentInfo> filterList(List<DocumentInfo> documentInfos) {
        String keyStatus = context.getString(R.string.fieldOrderStatus);
        String keyAcceptedBy = context.getString(R.string.fieldAcceptedBy);
        String userName = new DataStoreHelper(context).getUserName();

        List<DocumentInfo> result = new ArrayList<>();

        for(DocumentInfo documentInfo : documentInfos) {
            int orderStatus = documentInfo.getInteger(keyStatus);
            boolean firstCondition = orderStatus == OrderDetailModel.STATUS_PLACED;
            boolean secondCondition = documentInfo.getString(keyAcceptedBy).equals(userName)
                    && orderStatus != OrderDetailModel.STATUS_COMPLETE;

            if(firstCondition || secondCondition) {
                result.add(documentInfo);
            }
        }

        return result;
    }
}
