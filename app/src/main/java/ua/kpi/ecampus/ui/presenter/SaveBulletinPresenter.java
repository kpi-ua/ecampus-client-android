package ua.kpi.ecampus.ui.presenter;

import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.rx.BulletinRxLoader;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 25.04.2016.
 */
public class SaveBulletinPresenter extends BasePresenter {
    private IView mView;
    protected BulletinRxLoader mLoader;

    @Inject
    public SaveBulletinPresenter() {
        mLoader = new BulletinRxLoader(this);
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
        //loadSpinnerAdapterData();
    }

    public void setView(IView view) {
        mView = view;
    }

    public void setLoader(BulletinRxLoader loader) {
        mLoader = loader;
    }

    public void onStartRequest(CudAction action) {
        mView.showProgressDialog();

        action.execute();
    }

    public void onFinishRequest(int responseCode, String responseMsg) {
        mView.dismissProgressDialog();
        mView.showResponse(responseCode, responseMsg);
    }
/*
    public void loadGroupsOfSubdivision(String subdivisionId) {
        mLoader.loadGroupsOf(subdivisionId);
    }
*/
    public void setDescSubdivisions(List<Item> list) {
        mView.setSubdivisionAdapter(list);
    }

    public void setProfiles(List<Item> list) {
        mView.setProfileAdapter(list);
    }

    public void setGroups(List<Item> list) {
        mView.setGroupAdapter(list);
    }

    public void setRecipients(List<Recipient> list) {
        mView.setRecipientsList(list);
    }

    public void addBulletin() {
        Bulletin bulletin = mView.formBulletin();
        mLoader.addBulletin(bulletin);
    }

    public void editBulletin() {
        Bulletin bulletin = mView.formBulletin();
        mLoader.editBulletin(bulletin);
    }

    public void deleteBulletin() {
        String id = mView.getBulletinId();
        mLoader.deleteBulletin(id);
    }
/*
    private void loadSpinnerAdapterData() {
        User u = User.getInstance();
        List<Item> subdivisions = u.getSubdivision();
        if (subdivisions != null && !subdivisions.isEmpty()) {
            Item mainSubdiv = subdivisions.get(0);
            mLoader.loadDescSubdivisions(mainSubdiv.getId().toString());
            mLoader.loadGroupsOf(mainSubdiv.getId().toString());
        }
        mLoader.loadProfiles();
    }
*/
    public void loadRecipients() {
        String bulId = mView.getBulletinId();
        mLoader.loadRecipients(bulId);
    }


    public interface IView {
        void setViewComponent();

        void showProgressDialog();

        void dismissProgressDialog();

        void showResponse(int code, String msg);

        void setSubdivisionAdapter(List<Item> list);

        void setProfileAdapter(List<Item> list);

        void setGroupAdapter(List<Item> list);

        void setRecipientsList(List<Recipient> list);

        void updateBadgeCounter(int count);

        Bulletin formBulletin();

        String getBulletinId();
    }
}
