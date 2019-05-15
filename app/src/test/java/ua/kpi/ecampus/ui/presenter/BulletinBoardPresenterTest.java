package ua.kpi.ecampus.ui.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.kpi.ecampus.model.dao.BulletinDao;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.activity.BulletinBoardActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BulletinBoardPresenterTest {

    private BulletinBoardPresenter mPresenter;
    @Mock
    private BulletinBoardPresenter.IView mView;
    @Mock
    private Navigator mNavigator;
    @Mock
    private BulletinDao mDataAccess;
    @Mock
    private Context mContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new BulletinBoardPresenter(mContext, mNavigator);
        mView = mock(BulletinBoardActivity.class);
        mPresenter.setView(mView);
        mPresenter.setDao(mDataAccess);
    }

    @Test
    public void initializeViewComponent() {
        mPresenter.initializeViewComponent();
        verify(mView).setViewComponent();
    }

    @Test
    public void openBulletinModeratorActivity() {
        mPresenter.openBulletinModeratorActivity();
        verify(mNavigator).startBulletinBoardModeratorActivity();
    }

    @Test
    public void onItemClick() {
        Bulletin b = new Bulletin("1", "theme", "author", "2016");
        mPresenter.onItemClick(b);
        verify(mNavigator).startBulletinContentActivity(b);
    }

    @Test
    public void getData() {
        mPresenter.getData();
        verify(mDataAccess).getData();
    }

    @Test
    public void getSelectedByProfile() {
        mPresenter.getSelectedByProfile();
        verify(mDataAccess).getFilteredByProfile();
    }

    @Test
    public void getSelectedBySubdivision() {
        mPresenter.getSelectedBySubdivision();
        verify(mDataAccess).getFilteredBySubdiv();
    }

    @Test
    public void filterData() {
        List<Bulletin> list = mPresenter.filterData(createBulletinList(), "a");
        assertEquals(1, list.size());
    }

    private Collection<Bulletin> createBulletinList() {
        return new ArrayList<Bulletin>() {
            {
                add(new Bulletin("1", "aSubject", "author", "2016"));
                add(new Bulletin("1", "bSubject", "author", "2016"));
            }
        };
    }
}
