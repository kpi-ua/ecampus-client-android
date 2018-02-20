package ua.kpi.ecampus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import ua.kpi.ecampus.model.dao.BulletinDao;
import ua.kpi.ecampus.model.dao.IDataAccessObject;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.ui.Navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * BulletinBoardPresenter created to manage BulletinBoardActivity.
 */
public class BulletinBoardPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;
    private IDataAccessObject<Bulletin> mDataAccess;

    @Inject
    public BulletinBoardPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDataAccess = new BulletinDao();
    }

    public void setView(IView view) {
        mView = view;
    }

    public void setDao(IDataAccessObject dao) {
        mDataAccess = dao;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    /**
     * Get icons of BulletinBoard tabs.
     * @return array of resources
     */
    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(ua.kpi.ecampus.R.array.bulletin_board_tab_icon);
    }

    /**
     * Return whether current user is Moderator of BulletinBoard
     * @return true if user is moderator, false otherwise
     */
    public boolean isModerator() {
        return User.getInstance().isBulletinBoardModerator;
    }

    /**
     * Start BulletinBoardModeratorActivity
     */
    public void openBulletinModeratorActivity() {
        mNavigator.startBulletinBoardModeratorActivity();
    }

    /**
     * Handles click on recyclerview item.
     * @param item an item that is clicked on
     */
    public void onItemClick(Object item) {
        mNavigator.startBulletinContentActivity((Bulletin) item);
    }

    /**
     * Get Bulletin data from DataAccessObject
     * @return list of bulletins
     */
    public Collection<Bulletin> getData() {
        return mDataAccess.getData();
    }

    /**
     * Get Bulletin data filtered by profile which matches User profile
     * @return list of bulletins filtered by User profile
     */
    public Collection<Bulletin> getSelectedByProfile() {
        return ((BulletinDao) mDataAccess).getFilteredByProfile();
    }

    /**
     * Get Bulletin data filtered by subdivision which matches User subdivision
     * @return list of bulletins filtered by User subdivision
     */
    public Collection<Bulletin> getSelectedBySubdivision() {
        return ((BulletinDao) mDataAccess).getFilteredBySubdiv();
    }

    /**
     * Return access object to the list of Bulletins
     * @return
     */
    public IDataAccessObject getDao() {
        return mDataAccess;
    }

    /**
     * Filter list by specified query
     * @param list initial list
     * @param query request that filters list
     * @return filtered list
     */
    public List<Bulletin> filterData(Collection<Bulletin> list, String query) {
        query = query.toLowerCase();

        final List<Bulletin> filteredList = new ArrayList<>();
        for (Bulletin b : list) {
            final String text = b.getSubject().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(b);
            }
        }
        return filteredList;
    }

    public interface IView {
        void setViewComponent();
    }
}
