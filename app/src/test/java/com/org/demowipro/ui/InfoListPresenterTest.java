package com.org.demowipro.ui;

import com.org.demowipro.R;

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
    InfoListContract.View view;
    private InfoListPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new InfoListPresenter();
        presenter.setView(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_setView() {
    }

    @Test
    public void test_init() {
    }

    @Test
    public void test_fetchData() {
    }

    @Test
    public void test_onDbRetrieved() {
    }

    @Test
    public void test_loadData_whenContentIsNull() {
        presenter.setRowContentInfo(null);

        presenter.loadData();

        Mockito.verify(view, Mockito.times(1)).showViewMsg(R.string.no_data);
        Mockito.verify(view, Mockito.times(1)).showViews(true);
    }

    @Test
    public void test_getDataFromApi() {
    }

    @Test
    public void test_getRowDescriptions() {
    }

    @Test
    public void test_getRowContentInfo() {
    }

    @Test
    public void test_setRowContentInfo() {
    }

    @Test
    public void test_onStart() {
    }

    @Test
    public void test_onStop() {
    }

    @Test
    public void test_getRecentItemPosition() {
    }

    @Test
    public void test_setRecentItemPosition() {
    }
}