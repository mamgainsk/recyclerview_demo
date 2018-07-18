package com.org.demowipro.ui;

import com.org.demowipro.R;
import com.org.demowipro.request_pojo.RowContentInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfoListPresenterTest {

    @Mock
    private InfoListContract.View view;
    private InfoListPresenter presenter;
    private RowContentInfo rowContentInfo;

    @Before
    public void setUp() throws Exception {
        presenter = new InfoListPresenter();
        presenter.setView(view);
        rowContentInfo = new RowContentInfo();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_loadData_whenContentIsNull() {
        presenter.setRowContentInfo(null);

        presenter.loadData();

        Mockito.verify(view, Mockito.times(1)).showViewMsg(R.string.no_data);
        Mockito.verify(view, Mockito.times(1)).showViews(true);
    }

    @Test
    public void test_loadData_whenContentIsNotNull() {
        presenter.setRowContentInfo(rowContentInfo);

        presenter.loadData();

        Mockito.verify(view, Mockito.times(1)).setToolbarTitle(rowContentInfo.getTitle());
        Mockito.verify(view, Mockito.times(1)).reInitListSupportVariable();
        Mockito.verify(view, Mockito.times(1)).showViews(false);
    }
}