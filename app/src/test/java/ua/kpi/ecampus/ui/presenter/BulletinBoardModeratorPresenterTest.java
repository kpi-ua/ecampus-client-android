package ua.kpi.ecampus.ui.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.kpi.ecampus.model.dao.BulletinModeratorDao;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.activity.BulletinBoardModeratorActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BulletinBoardModeratorPresenterTest {

    private BulletinBoardModeratorPresenter mPresenter;
    @Mock
    private BulletinBoardModeratorPresenter.IView mView;
    @Mock
    private Navigator mNavigator;
    @Mock
    private BulletinModeratorDao mDataAccess;
    @Mock
    private Context mContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new BulletinBoardModeratorPresenter(mContext, mNavigator);
        mView = mock(BulletinBoardModeratorActivity.class);
        mPresenter.setView(mView);
        mPresenter.setDao(mDataAccess);
    }

    @Test
    public void initializeViewComponent() {
        mPresenter.initializeViewComponent();
        verify(mView).setViewComponent();
    }

    @Test
    public void onAddBulletin() {
        mPresenter.onButtonAddClick();
        verify(mNavigator).startNewBulletinActivity();
    }

    @Test
    public void onEditBulletin() {
        Bulletin b = new Bulletin("1", "theme", "author", "2016");
        mPresenter.onEditMenuClick(b);
        verify(mNavigator).startEditBulletinActivity(b);
    }

    @Test
    public void getData() {
        mPresenter.getData();
        verify(mDataAccess).getData();
    }

    @Test
    public void getSelectedByProfile() {
        mPresenter.getFilteredByProfile();
        verify(mDataAccess).getFilteredByProfile();
    }

    @Test
    public void getSelectedBySubdivision() {
        mPresenter.getFilteredBySubdivision();
        verify(mDataAccess).getFilteredBySubdiv();
    }

    @Test
    public void getFilteredByDate() {
        mPresenter.getFilteredByDate();
        verify(mDataAccess).getNotExpired();
    }

    @Test
    public void getDeletedBulletins() {
        mPresenter.getDeletedBulletins();
        verify(mDataAccess).getDeleted();
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
