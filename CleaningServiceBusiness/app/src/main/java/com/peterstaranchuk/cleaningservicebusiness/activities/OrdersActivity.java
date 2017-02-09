package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.adapters.OrdersAdapter;
import com.peterstaranchuk.cleaningservicebusiness.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservicebusiness.presenter.OrdersListScreenPresenter;
import com.peterstaranchuk.cleaningservicebusiness.view.OrdersListActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class OrdersActivity extends AppCompatActivity implements OrdersListActivityView {

    @BindView(R.id.lvOrders) ListView lvOrders;
    private OrdersListScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new OrdersListScreenPresenter(this, new OrdersListScreenModel(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshOrdersList();
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, OrdersActivity.class));
    }

    @Override
    public void refreshOrdersList(final List<DocumentInfo> orders) {
        OrdersAdapter adapter = new OrdersAdapter(this, orders, R.layout.item_order);
        lvOrders.setAdapter(adapter);
        lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onListItemClicked(orders.get(position));
            }
        });
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openOrderDetailsActivity(DocumentInfo orderInfo) {
        OrderDetailsActivity.display(this, orderInfo);
    }
}
