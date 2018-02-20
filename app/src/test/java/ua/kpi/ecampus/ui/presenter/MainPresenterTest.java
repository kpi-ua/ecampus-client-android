package ua.kpi.ecampus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.Preference;
import ua.kpi.ecampus.ui.activity.MainActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private MainPresenter mPresenter;
    @Mock
    private MainPresenter.IView mView;
    @Mock
    private Navigator mNavigator;
    @Mock
    private Preference mPreference;
    @Mock
    private Context mContext;
    @Mock
    private Resources resources;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MainPresenter(mContext, mNavigator, mPreference);
        mView = mock(MainActivity.class);
        mPresenter.setView(mView);
    }

    @Test
    public void startActivityOfSubsystem() {
        mPresenter.startActivityBasedOn(0);
        verify(mNavigator).startBulletinBoardActivity();
    }

    @Test
    public void logout() {
        mPresenter.logout();
        verify(mPreference).saveLogoutInfo();
        verify(mNavigator).startLoginActivity();
    }


    @Test
    public void getSubsystems() {
        int subsystemNumber = 6;
        String[] names = new String[]{"1", "2", "3", "4", "5", "6"};
        when(mContext.getResources()).thenReturn(resources);
        when(mContext.getResources().getStringArray(R.array.full_subsystem)).thenReturn(names);
        TypedArray img = mock(TypedArray.class);
        for(int i = 0; i < subsystemNumber; i++)
            when(img.getResourceId(i, -1)).thenReturn(-1);
        when(resources.obtainTypedArray(R.array.full_subsystem_image)).thenReturn(img);

        List<Subsystem> list = mPresenter.getData();
        Assert.assertEquals(subsystemNumber, list.size());
    }

    @Test
    public void userIsLogged() {
        mPresenter.checkUserIsLogged();
        verify(mPreference).getTokenExpirationDate();
        verify(mPreference).getIsLogged();
        verify(mNavigator).startLoginActivity();
    }
}
