package ua.kpi.ecampus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.ui.adapter.SubsystemAdapter;
import ua.kpi.ecampus.ui.presenter.MainNotAuthPresenter;
import ua.kpi.ecampus.di.UIModule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Main activity when user is not logged into a app.
 */
public class MainNotAuthActivity extends BaseActivity implements
        MainNotAuthPresenter.IView {

    @Bind(ua.kpi.ecampus.R.id.toolbar) Toolbar mToolbar;
    @Bind(ua.kpi.ecampus.R.id.recycler_view_subsystems) RecyclerView mRecyclerView;
    @Inject
    MainNotAuthPresenter mPresenter;

    SubsystemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ua.kpi.ecampus.R.layout.activity_main_not_auth);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(ua.kpi.ecampus.R.menu.menu_main_not_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ua.kpi.ecampus.R.id.action_login:
                mPresenter.openLogin();
                break;
            case ua.kpi.ecampus.R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setViewComponent() {
        setToolbar();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SubsystemAdapter(this, new ArrayList<Subsystem>());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(mPresenter.getData());
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ua.kpi.ecampus);
        getSupportActionBar().setTitle(ua.kpi.ecampus.R.string.activity_name_main);
    }
}
