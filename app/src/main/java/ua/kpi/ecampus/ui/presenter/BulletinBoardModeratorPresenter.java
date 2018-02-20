package ua.kpi.ecampus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.dao.BulletinModeratorDao;
import ua.kpi.ecampus.model.dao.IDataAccessObject;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.ui.Navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ua.kpi.ecampus.util.BulletinPredicates;

/**
 * BulletinBoardModeratorPresenter created to manage BulletinBoardModeratorActivity.
 */
public class BulletinBoardModeratorPresenter extends BasePresenter {
    private IView mView;
    private Context mContext;
    private Navigator mNavigator;
    private IDataAccessObject<Bulletin> mDataAccess;

    @Inject
    public BulletinBoardModeratorPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDataAccess = new BulletinModeratorDao();
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
     * Get array of icons of BulletinBoardModerator tabs.
     *
     * @return typed array of icons
     */
    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(R.array.bulletin_board_moderator_tab_icon);
    }

    /**
     * Handles click on Add button.
     * Open NewBulletinActivity class.
     */
    public void onButtonAddClick() {
        mNavigator.startNewBulletinActivity();
    }

    /**
     * Handles click on recyclerview item.
     *
     * @param item an item that is clicked on
     */
    public void onItemClick(Object item) {
        mNavigator.startBulletinContentActivity((Bulletin) item);
    }

    /**
     * Handles click on edit menu item.
     * Open EditBulletinActivity class.
     *
     * @param bulletin recyclerview item which has to be edited
     */
    public void onEditMenuClick(Bulletin bulletin) {
        mNavigator.startEditBulletinActivity(bulletin);
    }

    /**
     * Return access object to the list of Bulletins
     *
     * @return access object
     */
    public IDataAccessObject getDao() {
        return mDataAccess;
    }

    /**
     * Get Bulletin data from DataAccessObject
     *
     * @return list of bulletins
     */
    public Collection<Bulletin> getData() {
        return mDataAccess.getData();
    }

    /**
     * Get Bulletin data filtered by profile which matches User profile
     *
     * @return list of bulletins filtered by User profile
     */
    public Collection<Bulletin> getFilteredByProfile() {
        return ((BulletinModeratorDao) mDataAccess).getFilteredByProfile();
    }

    /**
     * Get Bulletin data filtered by subdivision which matches User subdivision
     *
     * @return list of bulletins filtered by User subdivision
     */
    public Collection<Bulletin> getFilteredBySubdivision() {
        return ((BulletinModeratorDao) mDataAccess).getFilteredBySubdiv();
    }

    /**
     * Get Bulletins which already started and not expired
     *
     * @return list of bulletins filtered by date
     */
    public Collection<Bulletin> getFilteredByDate() {
        return ((BulletinModeratorDao) mDataAccess).getNotExpired();
    }

    /**
     * Get deleted Bulletins
     *
     * @return list of deleted bulletins
     */
    public Collection<Bulletin> getDeletedBulletins() {
        return ((BulletinModeratorDao) mDataAccess).getDeleted();
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

        filteredList.addAll(BulletinPredicates.filterBulletins(list,
                BulletinPredicates.isMatchesQuerySubject(query)));
        return filteredList;
    }

    public interface IView {
        void setViewComponent();
    }
}
