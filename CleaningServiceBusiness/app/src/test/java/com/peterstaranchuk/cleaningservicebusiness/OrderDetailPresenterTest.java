package com.peterstaranchuk.cleaningservicebusiness;


import android.content.Intent;

import com.peterstaranchuk.cleaningservicebusiness.model.OrderDetailModel;
import com.peterstaranchuk.cleaningservicebusiness.presenter.OrderDetailPresenter;
import com.peterstaranchuk.cleaningservicebusiness.view.OrderDetailView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackDocumentSaved;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Peter Staranchuk.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderDetailPresenterTest {
    @Mock
    OrderDetailView view;
    @Mock
    OrderDetailModel model;
    private OrderDetailPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OrderDetailPresenter(view, model);
    }

    @Test
    public void shouldFillFieldsWhenActivityStarted() throws Exception {
        //when
        presenter.onCreate(new Intent());

        //than
        verify(model).getUserData();
        verify(model).getOrderData(any(Intent.class));
        verify(view).setOrderData(any(DocumentInfo.class), any(DocumentInfo.class));
    }

    @Test
    public void shouldSetInitialStatesWhenActivityCreated() throws Exception {
        //when
        presenter.onCreate(new Intent());

        //than
        verify(view).setInitialValues();
    }

    @Test
    public void shouldSetAcceptedStatusAndSetUndoButtonVisibleWhenAcceptButtonClicked() throws Exception {
        //when
        when(view.getIntent()).thenReturn(new Intent());
        when(model.getOrderData(view.getIntent())).thenReturn(new DocumentInfo());
        presenter.onSetNextStatusButtonClicked();

        //when
        verify(model).getCurrentOrderStatus();
        verify(model).setNextStatus(anyString(), any(CallbackDocumentSaved.class));
//        verify(view).setOrdersStatus(anyInt());
    }

    @Test
    public void shouldSetPreviousStatusWhenUndoButtonClicked() throws Exception {
        when(view.getIntent()).thenReturn(new Intent());
        when(model.getOrderData(view.getIntent())).thenReturn(new DocumentInfo());
        presenter.onUndoStatusButtonClicked();

        //when
        verify(model).getCurrentOrderStatus();
        verify(model).setPreviousStatus(anyString(), any(CallbackDocumentSaved.class));
    }
}