package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.helpers.FormatHelper;
import com.peterstaranchuk.cleaningservicebusiness.helpers.InputHelper;
import com.peterstaranchuk.cleaningservicebusiness.model.OrderDetailModel;
import com.peterstaranchuk.cleaningservicebusiness.presenter.OrderDetailPresenter;
import com.peterstaranchuk.cleaningservicebusiness.view.OrderDetailView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailView {

    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.tvUserAddress) TextView tvUserAddress;
    @BindView(R.id.tvUserPhone) TextView tvUserPhone;
    @BindView(R.id.tvPlacedAt) TextView tvPlacedAt;
    @BindView(R.id.tvOrderPrice) TextView tvOrderPrice;
    @BindView(R.id.tvOrderStatus) TextView tvOrderStatus;
    @BindView(R.id.btnAccept) Button btnAccept;
    @BindView(R.id.btnUndoStatus) Button btnUndoStatus;

    @BindString(R.string.textUserName) String textUserName;
    @BindString(R.string.textUserAddress) String textUserAddress;
    @BindString(R.string.textUserPhone) String textUserPhone;
    @BindString(R.string.order_item_placed_at) String textPlacedAt;
    @BindString(R.string.order_price) String textOrderPrice;
    @BindString(R.string.textOrderStatus) String textOrderStatus;


    public static final String EXTRA_ORDER_INFO = "EXTRA_ORDER_INFO";
    public OrderDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        presenter = new OrderDetailPresenter(this, new OrderDetailModel(this));
        presenter.onCreate(getIntent());
    }

    public static void display(Context context, DocumentInfo orderInfo) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra(EXTRA_ORDER_INFO, orderInfo);
        context.startActivity(intent);
    }

    @Override
    public void setOrderData(DocumentInfo orderData, DocumentInfo userData) {
        tvUserName.setText(textUserName + " " + String.valueOf(orderData.get(getString(R.string.fieldUserName))));
        tvUserPhone.setText(textUserPhone + " " + String.valueOf(userData.get(getString(R.string.fieldPhone))));
        tvUserAddress.setText(textUserAddress + " " + String.valueOf(orderData.get(getString(R.string.fieldAddress))));
        tvOrderPrice.setText(textOrderPrice + " " + String.valueOf(orderData.get(getString(R.string.fieldOrderPrice))) + " " + getString(R.string.currencySign));

        String createdAtString = String.valueOf(orderData.get(getString(R.string.fieldCreatedAt)));
        String dateAndTime = FormatHelper.getFormattedDate(this, createdAtString) + " " + FormatHelper.getFormattedTime(this, createdAtString);
        tvPlacedAt.setText(textPlacedAt + " " + String.valueOf(dateAndTime));
    }

    @Override
    public void setInitialValues() {
        btnAccept.setText(R.string.btnTextAccept);
        btnUndoStatus.setVisibility(View.GONE);
    }

    @Override
    public void setOrdersStatus(int ordersStatus) {
        tvOrderStatus.setText(textOrderStatus + " " + getOrderStatus(ordersStatus));
    }

    @Override
    public void setCompleteState() {
        btnAccept.setText(R.string.btnTextComplete);
    }

    @Override
    public void setUndoButtonVisible() {
        btnUndoStatus.setVisibility(View.VISIBLE);
        InputHelper.enableButton(btnUndoStatus, android.R.color.holo_red_dark);
    }

    @Override
    public void disableUndoButton() {
        InputHelper.disableButton(btnUndoStatus);
    }

    @Override
    public void setStateButtonText(int status) {
        String text = String.format(getString(R.string.setstate), getOrderStatus(status + 1));
        btnAccept.setText(text);
    }

    @Override
    public void showCompletedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warningTitle)
                .setMessage(R.string.areYouSureCompletedMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMoneyConfirmationDialog();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();

    }

    private void showMoneyConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.completedTitle)
                .setMessage(R.string.completedMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }


    public String getOrderStatus(int code) {
        switch (code) {
            case OrderDetailModel.STATUS_PLACED: return getString(R.string.status_placed);
            case OrderDetailModel.STATUS_ACCEPTED: return getString(R.string.status_accepted);
            case OrderDetailModel.STATUS_IN_PROGRESS: return getString(R.string.status_inprogress);
            case OrderDetailModel.STATUS_COMPLETE: return getString(R.string.status_completed);
        }

        return getString(R.string.status_error);
    }

    @OnClick(R.id.btnAccept)
    public void onBtnAcceptClicked(View v) {
        if(presenter.getCurrentStatus() == OrderDetailModel.STATUS_PLACED) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.warningTitle)
                    .setMessage(R.string.warningMessage)
                    .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.onSetNextStatusButtonClicked();
                        }
                    }).setNegativeButton(R.string.cancel, null)
                    .show();
        } else {
            presenter.onSetNextStatusButtonClicked();
        }
    }

    @OnClick(R.id.btnUndoStatus)
    public void onBtnUndoStatusClicked(View v) {
        presenter.onUndoStatusButtonClicked();
    }
}
