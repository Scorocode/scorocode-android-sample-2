package com.peterstaranchuk.cleaningservicebusiness;


import com.peterstaranchuk.cleaningservicebusiness.model.OrdersListScreenModel;
import com.peterstaranchuk.cleaningservicebusiness.presenter.OrdersListScreenPresenter;
import com.peterstaranchuk.cleaningservicebusiness.view.OrdersListActivityView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackFindDocument;
import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


/**
 * Created by Peter Staranchuk.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrdersScreenPresenterTest {
    @Mock
    OrdersListActivityView view;
    @Mock OrdersListScreenModel model;
    private OrdersListScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OrdersListScreenPresenter(view, model);
    }

    @Test
    public void shouldLoadOrdersWhenActivityStartedOrRefreshButtonClicked() throws Exception {
        //when
        presenter.refreshOrdersList();

        //than
        verify(model).getOrdersList(any(CallbackFindDocument.class));
//        verify(view).refreshOrdersList(new ArrayList<DocumentInfo>());
    }

    @Test
    public void shouldOpenOrderDetailScreenWhenOrdersListItemClicked() throws Exception {
        //when
        presenter.onListItemClicked(any(DocumentInfo.class));

        //than
        verify(view).openOrderDetailsActivity(any(DocumentInfo.class));

    }
}