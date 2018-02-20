package ua.kpi.ecampus.ui.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.kpi.ecampus.ui.activity.BulletinContentActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BulletinContentPresenterTest {

    private BulletinContentPresenter mPresenter;
    @Mock
    private BulletinContentPresenter.IView mView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new BulletinContentPresenter();
        mView = mock(BulletinContentActivity.class);
        mPresenter.setView(mView);
    }

    @Test
    public void initializeViewComponent() {
        mPresenter.initializeViewComponent();
        verify(mView).setViewComponent();
    }
}
