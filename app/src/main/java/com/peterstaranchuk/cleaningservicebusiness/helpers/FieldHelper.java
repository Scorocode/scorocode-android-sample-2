package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservicebusiness.R;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class FieldHelper {
    //you can use helper class to store all info
    //about field's names
    //and to retrieve information from fields

    private String createdAt;
    private String orderStatus;

    //feedback collection
    private String isEmployee;
    private String orderPrice;

    public FieldHelper(Context context) {
        setOrdersCollectionsFields(context);
    }

    private void setOrdersCollectionsFields(Context context) {

        this.createdAt = context.getString(R.string.createdAtField);
        this.orderStatus = context.getString(R.string.orderStatusField);
        this.isEmployee = context.getString(R.string.isEmployeeField);
        this.orderPrice = context.getString(R.string.orderPriceField);
    }


    @NonNull
    public String getIdFrom(DocumentInfo documentInfo) {
        if (documentInfo == null) {
            return "";
        }

        String id = documentInfo.getId();
        return id == null ? "" : id;
    }


    @NonNull
    public String getPlacedAt(DocumentInfo documentInfo) {
        if (documentInfo == null) {
            return "";
        }

        Object placedAt = documentInfo.get(createdAtField());
        return placedAt == null ? "" : placedAt.toString();
    }

    @NonNull
    public Integer getOrderStatus(DocumentInfo documentInfo) {
        if (documentInfo == null) {
            return 0;
        }

        Object orderStatus = documentInfo.get(orderStatusField());
        return orderStatus == null ? 0 : Math.round(Float.valueOf(orderStatus.toString()));
    }

    @NonNull
    public String createdAtField() {
        return createdAt;
    }

    @NonNull
    public String orderStatusField() {
        return orderStatus;
    }

    public String isEmployeeField() {
        return isEmployee;
    }

    public double getOrderPriceFrom(DocumentInfo order) {
        if (order == null) {
            return 0d;
        }

        Object orderPrice = order.get(orderPriceField());
        return orderPrice == null ? 0d : Double.valueOf(orderPrice.toString());
    }

    private String orderPriceField() {
        return orderPrice;
    }
}
