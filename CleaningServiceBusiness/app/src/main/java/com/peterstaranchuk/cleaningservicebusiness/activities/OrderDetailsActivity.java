package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailView {

    private static final String DELIMITER = " ";

    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.tvUserAddress) TextView tvUserAddress;
    @BindView(R.id.tvUserPhone) TextView tvUserPhone;
    @BindView(R.id.tvPlacedAt) TextView tvPlacedAt;
    @BindView(R.id.tvOrderPrice) TextView tvOrderPrice;
    @BindView(R.id.tvOrderStatus) TextView tvOrderStatus;
    @BindView(R.id.btnAccept) Button btnAccept;
    @BindView(R.id.btnUndoStatus) Button btnUndoStatus;

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
        tvUserName.setText(orderData.getString(getString(R.string.fieldUserName)));
        tvUserPhone.setText(orderData.getString(getString(R.string.fieldContactPhone)));
        tvUserAddress.setText(orderData.getString(getString(R.string.fieldAddress)));
        tvOrderPrice.setText(orderData.getString(getString(R.string.fieldOrderPrice)));
        tvPlacedAt.setText(getDateAndTimeStringFrom(orderData));
    }

    @NonNull
    private String getDateAndTimeStringFrom(DocumentInfo orderData) {
        String createdAtString = orderData.getString(getString(R.string.fieldCreatedAt));
        return FormatHelper.getFormattedDate(this, createdAtString) + " " + FormatHelper.getFormattedTime(this, createdAtString);
    }

    @Override
    public void setInitialValues() {
        btnAccept.setText(R.string.btnTextAccept);
        btnUndoStatus.setVisibility(View.GONE);
    }

    @Override
    public void setOrdersStatus(String ordersStatus) {
        tvOrderStatus.setText(ordersStatus);
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
    public void setStateButtonText(String status) {
        String text = String.format(getString(R.string.setstate), status);
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

    @OnClick(R.id.btnAccept)
    public void onBtnAcceptClicked(View v) {
        if(presenter.getCurrentStatus() == OrderDetailModel.STATUS_PLACED) {
            showWarantDialog();
        } else {
            presenter.onSetNextStatusButtonClicked();
        }
    }

    private void showWarantDialog() {
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
    }

    @OnClick(R.id.btnUndoStatus)
    public void onBtnUndoStatusClicked(View v) {
        presenter.onUndoStatusButtonClicked();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
