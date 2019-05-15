package ua.kpi.ecampus.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.dao.IDataAccessObject;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.rx.BulletinModeratorResponseManager;
import ua.kpi.ecampus.ui.adapter.PagingRecyclerAdapter;
import ua.kpi.ecampus.ui.presenter.BulletinBoardModeratorPresenter;
import ua.kpi.ecampus.ui.view.ExtendedRecyclerView;
import ua.kpi.ecampus.ui.view.OnItemClickListener;
import ua.kpi.ecampus.util.ToastUtil;
import ua.kpi.ecampus.util.pagination.PaginationTool;

/**
 * Bulletin Board activity for Moderator of BulletinBoard.
 */
public class BulletinBoardModeratorActivity extends BaseActivity implements
        BulletinBoardModeratorPresenter.IView {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(android.R.id.list) ExtendedRecyclerView mRecyclerView;
    @BindView(R.id.progress_bar_bulletin) ProgressBar mProgressLoader;
    @Inject
    BulletinBoardModeratorPresenter mPresenter;
    private PagingRecyclerAdapter mAdapter;
    private Subscription mPagingSubscription;

    /**
     * The list of bulletins which are currently in adapter.
     * Designed for filtering purposes.
     */
    private Collection<Bulletin> mBulletins;

    /**
     * Is this activity is moderator activity.
     * Always true. Affects on recycler item view (enable Edit button).
     */
    private final boolean IS_MODERATOR_MODE = true;
    /**
     * Tabs indices
     */
    private final int TAB_ALL = 0;
    private final int TAB_ACTUAL = 1;
    private final int TAB_PROFILE = 2;
    private final int TAB_SUBDIVISION = 3;
    private final int TAB_DELETED = 4;
    /**
     * Limit for the number of loaded items
     */
    private final static int LIMIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board_moderator);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    private OnItemClickListener onItemClickListener =
            new OnItemClickListener() {
                @Override
                public void onItemClicked(View view, int position, Object
                        item) {
                    mPresenter.onItemClick(item);
                }
            };

    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new
            PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.edit:
                            Bulletin b = mAdapter.getClickedItem();
                            mPresenter.onEditMenuClick(b);
                            break;
                    }
                    return true;
                }
            };

    private SearchView.OnQueryTextListener onQueryTextChangeListener = new
            SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    final List<Bulletin> filteredList = mPresenter.filterData
                            (mBulletins, newText);
                    mAdapter.setFilter(filteredList);
                    return false;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bulletin_board, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(onQueryTextChangeListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void setViewComponent() {
        setToolbar();
        setTabLayout();
        setRecyclerView();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_bulletin_moderator_mode_title);
    }

    private void setTabLayout() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId
                (TAB_ALL, -1)), true);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId
                (TAB_ACTUAL, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId
                (TAB_PROFILE, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId
                (TAB_SUBDIVISION, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId
                (TAB_DELETED, -1)));
        mTabLayout.setOnTabSelectedListener(new TabLayout
                .OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // N/A
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // N/A
            }
        });
    }

    private void setCurrentTabFragment(int tabPosition) {
        switch (tabPosition) {
            case TAB_ALL:
                mBulletins = mPresenter.getData();
                break;
            case TAB_ACTUAL:
                mBulletins = mPresenter.getFilteredByDate();
                break;
            case TAB_PROFILE:
                mBulletins = mPresenter.getFilteredByProfile();
                break;
            case TAB_SUBDIVISION:
                mBulletins = mPresenter.getFilteredBySubdivision();
                break;
            case TAB_DELETED:
                mBulletins = mPresenter.getDeletedBulletins();
                break;
        }
        mAdapter.setItems(new ArrayList<>(mBulletins));

    }

    @OnClick(R.id.fab_add)
    public void fabAddOnClick() {
        mPresenter.onButtonAddClick();
    }

    private void setVisible(View... views) {
        for (View v : views)
            v.setVisibility(View.VISIBLE);
    }

    private void setInvisible(View... views) {
        for (View v : views)
            v.setVisibility(View.GONE);
    }

    private void setRecyclerView() {
        setInvisible(mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager
                (getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(findViewById(android.R.id.empty));

        mAdapter = new PagingRecyclerAdapter(IS_MODERATOR_MODE);
        mAdapter.setHasStableIds(true);

        mRecyclerView.setSaveEnabled(true);

        mAdapter.setOnItemClickListener(onItemClickListener);
        mAdapter.setOnMenuItemClickListener(onMenuItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        // if all items was loaded we don't need Pagination
        if (mAdapter.isAllItemsLoaded()) {
            return;
        }
        setRecyclerViewPagination();
    }

    /**
     * RecyclerView pagination
     */
    private void setRecyclerViewPagination() {
        PaginationTool<List<Bulletin>> paginationTool = PaginationTool
                .buildPagingObservable(mRecyclerView, lastId -> new
                        BulletinModeratorResponseManager().getResponse
                        (lastId, LIMIT)).setLimit(LIMIT).build();

        mPagingSubscription = paginationTool
                .getPagingObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bulletin>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null)
                            //Log.e(Config.LOG, e.getMessage());
                            ToastUtil.showError(getString(R.string.error_while_data_obtaining),
                                    getApplicationContext());

                        setViewsVisibility();
                    }

                    @Override
                    public void onNext(List<Bulletin> items) {
                        IDataAccessObject<Bulletin> dao = mPresenter.getDao();
                        dao.setData(items);
                        mBulletins = new ArrayList<>(dao.getData());
                        setViewsVisibility();
                        mAdapter.setItems(new ArrayList<>(mBulletins));
                    }
                });
    }

    private void setViewsVisibility() {
        setInvisible(mProgressLoader);
        setVisible(mRecyclerView);
    }

    @Override
    public void onDestroy() {
        if (mPagingSubscription != null && !mPagingSubscription.isUnsubscribed()) {
            mPagingSubscription.unsubscribe();
        }
        // for memory leak prevention (RecycleView is not unsubscibed from
        // adapter DataObserver)
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(null);
        }
        super.onDestroy();
    }
}
