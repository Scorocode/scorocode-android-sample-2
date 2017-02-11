package com.peterstaranchuk.cleaningservicebusiness.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.helpers.FieldHelper;
import com.peterstaranchuk.cleaningservicebusiness.helpers.FormatHelper;
import com.peterstaranchuk.cleaningservicebusiness.model.OrderDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.Document;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

/**
 * Created by Peter Staranchuk.
 */

public class OrdersAdapter extends BaseAdapter {

    private Context context;
    private List<DocumentInfo> orders;
    private LayoutInflater inflater;
    private int layoutRes;

    private String textCurrencySign;

    public OrdersAdapter(Context context, List<DocumentInfo> orders, int layoutRes) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.orders = (orders == null? new ArrayList<DocumentInfo>() : orders);
        this.layoutRes = layoutRes;
        this.textCurrencySign = context.getString(R.string.currencySign);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < orders.size()) {
            return orders.get(position);
        } else {
            return new Document("");
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(layoutRes, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        DocumentInfo order = (DocumentInfo) getItem(position);

        FieldHelper fieldHelper = new FieldHelper(context);
        String placedAt = fieldHelper.getPlacedAt(order);
        String orderPrice = FormatHelper.formatMoney(fieldHelper.getOrderPriceFrom(order));
        int orderStatus = order.getInteger(context.getString(R.string.orderStatusField));

        holder.tvOrderPrice.setText(orderPrice + " " + textCurrencySign);
        holder.tvOrderPlaceTime.setText(getDateAndTime(placedAt));

        setIcon(holder, orderStatus);

        return view;
    }

    private void setIcon(ViewHolder holder, int orderStatus) {
        int drawableId = 0;
        switch (orderStatus) {
            case OrderDetailModel.STATUS_PLACED:
                drawableId = R.drawable.ic_fiber_new_black_24dp;
                break;

            case OrderDetailModel.STATUS_ACCEPTED:
                drawableId = R.drawable.ic_content_paste_black_24dp;
                break;

            case OrderDetailModel.STATUS_IN_PROGRESS:
                drawableId = R.drawable.ic_event_note_black_24dp;
                break;
        }

        if(drawableId != 0) {
            holder.ivStatus.setVisibility(View.VISIBLE);
            holder.ivStatus.setBackground(context.getResources().getDrawable(drawableId));
        } else {
            holder.ivStatus.setVisibility(View.GONE);
        }
    }

    @NonNull
    private String getDateAndTime(String placedAt) {
        return FormatHelper.getFormattedDate(context, placedAt) + " " + FormatHelper.getFormattedTime(context, placedAt);
    }

    static class ViewHolder {
        @BindView(R.id.tvOrderPlaceTime) TextView tvOrderPlaceTime;
        @BindView(R.id.tvOrderPrice) TextView tvOrderPrice;
        @BindView(R.id.ivStatus) ImageView ivStatus;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
