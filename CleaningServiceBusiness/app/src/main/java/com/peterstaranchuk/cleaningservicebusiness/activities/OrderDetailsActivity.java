package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.helpers.ActionBarHelper;
import com.peterstaranchuk.cleaningservicebusiness.helpers.AnimationHelper;
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
    @BindView(R.id.tvNumberOfBathrooms) TextView tvNumberOfBathrooms;
    @BindView(R.id.tvNumberOfBedrooms) TextView tvNumberOfBedrooms;
    @BindView(R.id.tvArea) TextView tvArea;
    @BindView(R.id.btnAccept) Button btnAccept;
    @BindView(R.id.btnUndoStatus) Button btnUndoStatus;

    @BindView(R.id.llUserSection) LinearLayout llUserSection;
    @BindView(R.id.llOrderSection) LinearLayout llOrderSection;
    @BindView(R.id.llAdditionalInfoSection) LinearLayout llAdditionalInfoSection;

    @BindView(R.id.ivExpandUserDetails) ImageView ivUserSectionButton;
    @BindView(R.id.ivExpandOrderDetails) ImageView ivOrderSectionButton;
    @BindView(R.id.ivExpandAdditionalInfo) ImageView ivAdditionalInfoSectionButton;
    @BindView(R.id.vDelimiter) View vDelimiter;


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

    @OnClick({R.id.ivExpandUserDetails, R.id.rlExpandUserDetails})
    public void onExpandCollapseUserSectionButtonClicked() {
        if(AnimationHelper.isExpanded(llUserSection)) {
            collapseSection(ivUserSectionButton, llUserSection);
        } else {
            expandSection(ivUserSectionButton, llUserSection);
        }
    }

    @OnClick({R.id.ivExpandOrderDetails, R.id.rlExpandOrderDetails})
    public void onExpandCollapseOrderSectionButtonClicked() {
        if(AnimationHelper.isExpanded(llOrderSection)) {
            collapseSection(ivOrderSectionButton, llOrderSection);
        } else {
            expandSection(ivOrderSectionButton, llOrderSection);
        }
    }

    @OnClick({R.id.ivExpandAdditionalInfo, R.id.rlExpandAdditionalInfoDetails})
    public void onExpandCollapseAdditionalInfoSectionButtonClicked() {
        if(AnimationHelper.isExpanded(llAdditionalInfoSection)) {
            vDelimiter.setVisibility(View.VISIBLE);
            collapseSection(ivAdditionalInfoSectionButton, llAdditionalInfoSection);
        } else {
            vDelimiter.setVisibility(View.GONE);
            expandSection(ivAdditionalInfoSectionButton, llAdditionalInfoSection);
        }
    }

    private void expandSection(ImageView ivExpandCollapseButton, View section) {
        ivExpandCollapseButton.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        AnimationHelper.expand(section);
    }

    private void collapseSection(ImageView ivExpandCollapseButton, View section) {
        ivExpandCollapseButton.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        AnimationHelper.collapse(section);
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
        tvNumberOfBathrooms.setText(String.valueOf(orderData.getInteger(getString(R.string.numberOfBathroomsField))));
        tvNumberOfBedrooms.setText(String.valueOf(orderData.getInteger(getString(R.string.numberOfBedroomsField))));
        tvArea.setText(String.valueOf(orderData.getInteger(getString(R.string.areaField))));
    }

    @NonNull
    private String getDateAndTimeStringFrom(DocumentInfo orderData) {
        String createdAtString = orderData.getString(getString(R.string.fieldCreatedAt));
        return FormatHelper.getFormattedDate(this, createdAtString) + " " + FormatHelper.getFormattedTime(this, createdAtString);
    }

    @Override
    public void setInitialState() {
        collapseSection(ivOrderSectionButton, llOrderSection);
        collapseSection(ivAdditionalInfoSectionButton, llAdditionalInfoSection);

        AnimationHelper.collapse(llOrderSection);
        AnimationHelper.collapse(llAdditionalInfoSection);

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
    public void showWarrantDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warningTitle)
                .setMessage(R.string.areYouSureCompletedMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.setOrderCompletedState();
                        showMoneyConfirmationDialog();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();

    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.orderDetailsTitle));
            ActionBarHelper.setHomeButton(getSupportActionBar());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
