package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

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
    @BindString(R.string.fieldUserName) String keyUsername;
    @BindString(R.string.fieldContactPhone) String keyPhone;
    @BindString(R.string.fieldAddress) String keyAddress;
    @BindString(R.string.fieldOrderPrice) String keyPrice;
    @BindString(R.string.numberOfBathroomsField) String fieldBathrooms;
    @BindString(R.string.numberOfBedroomsField) String fieldBedrooms;
    @BindString(R.string.fieldOrderStatus) String keyStatus;
//    String keyUsername = getString(R.string.fieldUserName);
//    String keyPhone = getString(R.string.fieldContactPhone);
//    String keyAddress = getString(R.string.fieldAddress);
//    String keyPrice = getString(R.string.fieldOrderPrice);
//    String fieldBathrooms = getString(R.string.numberOfBathroomsField);
//    String fieldBedrooms = getString(R.string.numberOfBedroomsField);
//    String keyStatus = getString(R.string.fieldOrderStatus);

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
        presenter.onUserSectionButtonClicked();
    }

    @OnClick({R.id.ivExpandOrderDetails, R.id.rlExpandOrderDetails})
    public void onExpandCollapseOrderSectionButtonClicked() {
        presenter.onOrderSectionButtonClicked();
    }

    @OnClick({R.id.ivExpandAdditionalInfo, R.id.rlExpandAdditionalInfoDetails})
    public void onExpandCollapseAdditionalInfoSectionButtonClicked() {
        presenter.onAdditionalInfoSectionButtonClicked();
    }

    @OnClick(R.id.ivCall)
    public void onPhoneButtonClicked() {
         presenter.onPhoneButtonClicked();
    }

    @OnClick(R.id.ivMap)
    public void onMapButtonClicked() {
        presenter.onMapButtonClicked();
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
        String userName = orderData.getString(keyUsername);
        String userPhone = orderData.getString(keyPhone);
        String userAddress = orderData.getString(keyAddress);
        String orderPrice = orderData.getString(keyPrice) + " " + getString(R.string.currencySign);
        String numberOfBathrooms = String.valueOf(orderData.getInteger(fieldBathrooms));
        String numberOfBedrooms = String.valueOf(orderData.getInteger(fieldBedrooms));
        String area = String.valueOf(orderData.getInteger(keyArea())) + " " + getString(R.string.sqft);
        String orderStatus = String.valueOf(orderData.getString(keyStatus));

        tvUserName.setText(userName);
        tvUserPhone.setText(userPhone);
        tvUserAddress.setText(userAddress);
        tvOrderPrice.setText(orderPrice);
        tvPlacedAt.setText(getDateAndTimeStringFrom(orderData));
        tvNumberOfBathrooms.setText(numberOfBathrooms);
        tvNumberOfBedrooms.setText(numberOfBedrooms);
        tvArea.setText(area);
        tvOrderStatus.setText(orderStatus);
    }

    @NonNull
    private String keyArea() {
        return getString(R.string.areaField);
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
    }

    @Override
    public void setOrdersStatus(int orderStatusCode) {

        String currentStatus = "";
        String nextStatus = "";
        boolean isUndoVisible = false;
        boolean isUndoEnabled = false;

        switch (orderStatusCode) {
            case OrderDetailModel.STATUS_PLACED:
                nextStatus = getString(R.string.btnTextAccept);
                currentStatus = getString(R.string.status_placed);
                isUndoVisible = false;
                isUndoEnabled = false;
                break;

            case OrderDetailModel.STATUS_ACCEPTED:
                nextStatus = getString(R.string.status_inprogress);
                currentStatus = getString(R.string.status_accepted);
                isUndoVisible = true;
                isUndoEnabled = false;
                break;

            case OrderDetailModel.STATUS_IN_PROGRESS:
                nextStatus = getString(R.string.status_completed);
                currentStatus = getString(R.string.status_inprogress);
                isUndoVisible = true;
                isUndoEnabled = true;
                break;

            case OrderDetailModel.STATUS_COMPLETE:
                currentStatus = getString(R.string.status_completed);
                nextStatus = getString(R.string.status_completed);
                isUndoVisible = true;
                isUndoEnabled = true;
                InputHelper.disableButton(btnAccept);
                InputHelper.disableButton(btnUndoStatus);
                break;
        }

        tvOrderStatus.setText(currentStatus);
        btnUndoStatus.setVisibility(isUndoVisible? View.VISIBLE : View.GONE);

        btnAccept.setText(String.format(getString(R.string.setstate), nextStatus));

        if(isUndoEnabled) {
            InputHelper.enableButton(btnUndoStatus, android.R.color.holo_red_dark);
        } else {
            InputHelper.disableButton(btnUndoStatus);
        }

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

    @Override
    public void openPhoneScreen() {
        String phone = InputHelper.getStringFrom(tvUserPhone);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void openMapScreen() {
        String map = getString(R.string.google_map_open_link) + InputHelper.getStringFrom(tvUserAddress);
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(i);
    }

    @Override
    public void expandOrCollapseUserSection() {
        if(AnimationHelper.isExpanded(llUserSection)) {
            collapseSection(ivUserSectionButton, llUserSection);
        } else {
            expandSection(ivUserSectionButton, llUserSection);
        }
    }

    @Override
    public void expandOrCollapseOrderSection() {
        if(AnimationHelper.isExpanded(llOrderSection)) {
            collapseSection(ivOrderSectionButton, llOrderSection);
        } else {
            expandSection(ivOrderSectionButton, llOrderSection);
        }
    }

    @Override
    public void expandOrCollapseAdditionalInfoSection() {
        if(AnimationHelper.isExpanded(llAdditionalInfoSection)) {
            vDelimiter.setVisibility(View.VISIBLE);
            collapseSection(ivAdditionalInfoSectionButton, llAdditionalInfoSection);
        } else {
            vDelimiter.setVisibility(View.GONE);
            expandSection(ivAdditionalInfoSectionButton, llAdditionalInfoSection);
        }
    }

    @Override
    public void showMoneyConfirmationDialog() {
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

    @Override
    public void setStatusComplete() {
        tvOrderStatus.setText(getString(R.string.status_completed));
    }

    @Override
    public void showError(int error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnAccept)
    public void onBtnAcceptClicked(View v) {
        if(presenter.getCurrentStatus() == OrderDetailModel.STATUS_PLACED) {
            showAcceptWarrantDialog();
        }  else {
            presenter.onSetNextStatusButtonClicked();
        }
    }

    @Override
    public void showAcceptWarrantDialog() {
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
