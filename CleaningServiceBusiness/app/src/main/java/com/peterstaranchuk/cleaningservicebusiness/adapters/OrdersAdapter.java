package com.peterstaranchuk.cleaningservicebusiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.helpers.FieldHelper;
import com.peterstaranchuk.cleaningservicebusiness.helpers.FormatHelper;

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

    private String textOrderNumber;
    private String textOrderPlacedAt;
    private String textOrderPrice;
    private String textCurrencySign;

    public OrdersAdapter(Context context, List<DocumentInfo> orders, int layoutRes) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.orders = (orders == null? new ArrayList<DocumentInfo>() : orders);
        this.layoutRes = layoutRes;

        this.textOrderNumber = context.getString(R.string.order_item_number);
        this.textOrderPlacedAt = context.getString(R.string.order_item_placed_at);
        this.textOrderPrice = context.getString(R.string.order_price);
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


        holder.tvOrderNumber.setText(textOrderNumber + " " + (position + 1));
        holder.tvOrderPlaceTime.setText(textOrderPlacedAt + "\n" + FormatHelper.getFormattedDate(context, placedAt) + " " + FormatHelper.getFormattedTime(context, placedAt));
        holder.tvOrderPrice.setText(String.valueOf(textOrderPrice + " " + orderPrice + " " + textCurrencySign));

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tvOrderNumber) TextView tvOrderNumber;
        @BindView(R.id.tvOrderPlaceTime) TextView tvOrderPlaceTime;
        @BindView(R.id.tvOrderPrice) TextView tvOrderPrice;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
