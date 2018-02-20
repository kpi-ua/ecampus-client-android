package ua.kpi.ecampus.ui.presenter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.rx.BulletinRxLoader;
import ua.kpi.ecampus.ui.activity.SaveBulletinActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

public class SaveBulletinPresenterTest {

    private SaveBulletinPresenter mPresenter;
    @Mock
    private SaveBulletinPresenter.IView mView;
    @Mock
    private BulletinRxLoader mLoader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SaveBulletinPresenter();
        mView = mock(SaveBulletinActivity.class);
        mPresenter.setView(mView);
        mPresenter.setLoader(mLoader);
    }

    @Ignore
    @Test
    public void initializeViewComponent() {
        List<Item> subdivisions = getStubSubdivision();
        String id = subdivisions.get(0).getId().toString();

        PowerMockito.suppress(PowerMockito.constructor(User.class));
        User mockUser = PowerMockito.mock(User.class);
        PowerMockito.mockStatic(User.class);
        doReturn(mockUser).when(Mockito.spy(User.getInstance()));
        doReturn(subdivisions).when(mockUser).getSubdivision();

        mPresenter.initializeViewComponent();
        verify(mView).setViewComponent();
        verify(mLoader).loadDescSubdivisions(id);
        verify(mLoader).loadGroupsOf(id);
        verify(mLoader).loadProfiles();
    }

    @Test
    public void onStartRequest() {
        CudAction action = mock(CudAction.class);
        mPresenter.onStartRequest(action);
        verify(mView).showProgressDialog();
    }

    @Test
    public void onFinishRequest() {
        int code = 0;
        String msg = "msg";
        mPresenter.onFinishRequest(code, msg);
        verify(mView).dismissProgressDialog();
        verify(mView).showResponse(code, msg);
    }

    @Test
    public void loadGroups() {
        String subdivId = "1";
        mPresenter.loadGroupsOfSubdivision(subdivId);
        verify(mLoader).loadGroupsOf(subdivId);
    }

    @Test
    public void setDescendantSubdivisions() {
        List<Item> subdivisions = getStubSubdivision();
        mPresenter.setDescSubdivisions(subdivisions);
        verify(mView).setSubdivisionAdapter(subdivisions);
    }

    @Test
    public void setProfiles() {
        List<Item> stub = getStubSubdivision();
        mPresenter.setProfiles(stub);
        verify(mView).setProfileAdapter(stub);
    }

    @Test
    public void setGroups() {
        List<Item> stub = getStubSubdivision();
        mPresenter.setGroups(stub);
        verify(mView).setGroupAdapter(stub);
    }

    @Test
    public void setRecipients() {
        List<Recipient> stub = getStubRecipients();
        mPresenter.setRecipients(stub);
        verify(mView).setRecipientsList(stub);
    }

    @Test
    public void addBulletin() {
        Bulletin stub = stubBulletin();
        when(mView.formBulletin()).thenReturn(stub);
        mPresenter.addBulletin();
        verify(mView).formBulletin();
        verify(mLoader).addBulletin(stub);
    }

    @Test
     public void editBulletin() {
        Bulletin stub = stubBulletin();
        when(mView.formBulletin()).thenReturn(stub);
        mPresenter.editBulletin();
        verify(mView).formBulletin();
        verify(mLoader).editBulletin(stub);
    }

    @Test
    public void deleteBulletin() {
        String stubId = "1";
        when(mView.getBulletinId()).thenReturn(stubId);
        mPresenter.deleteBulletin();
        verify(mView).getBulletinId();
        verify(mLoader).deleteBulletin(stubId);
    }

    @Test
    public void loadRecipient() {
        String stubId = "1";
        when(mView.getBulletinId()).thenReturn(stubId);
        mPresenter.loadRecipients();
        verify(mView).getBulletinId();
        verify(mLoader).loadRecipients(stubId);
    }

    private List<Item> getStubSubdivision() {
        return new ArrayList<Item>() {{
            add(new Item(1, "s1"));
        }};
    }

    private List<Recipient> getStubRecipients() {
        return new ArrayList<Recipient>() {{
            add(new Recipient());
        }};
    }

    private Bulletin stubBulletin() {
        return new Bulletin("1", "subject", "author", "2016");
    }
}
