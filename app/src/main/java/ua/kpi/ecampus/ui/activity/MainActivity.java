package ua.kpi.ecampus.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.ui.adapter.GridSubsystemAdapter;
import ua.kpi.ecampus.ui.presenter.MainPresenter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Main activity for application.
 */
public class MainActivity extends BaseActivity implements MainPresenter.IView {

    @Bind(ua.kpi.ecampus.R.id.toolbar)
    Toolbar mToolbar;
    @Bind(ua.kpi.ecampus.R.id.grid_view_subsystem)
    GridView mGridSubsystem;
    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ua.kpi.ecampus.R.layout.activity_main);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkUserIsLogged();
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
        getMenuInflater().inflate(ua.kpi.ecampus.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ua.kpi.ecampus.R.id.action_logout:
                showLogoutDialog();
            case ua.kpi.ecampus.R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(ua.kpi.ecampus.R.string.logout_confirmation);
        alertDialogBuilder.setPositiveButton(ua.kpi.ecampus.R.string.yes, new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.logout();
            }
        });
        alertDialogBuilder.setNegativeButton(ua.kpi.ecampus.R.string.no, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void setViewComponent() {
        setToolbar();
        setGridView();
    }

    private void setGridView() {
        List<Subsystem> data = mPresenter.getData();
        mGridSubsystem.setAdapter(new GridSubsystemAdapter(this, data));
        mGridSubsystem.setOnItemClickListener((adapterView, view, position, l) -> {
            ScaleAnimation sc = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, 1, 0.5f, 1, 0.5f);
            sc.setDuration(100);
            sc.setRepeatCount(1);
            sc.setRepeatMode(2);
            sc.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    mPresenter.startActivityBasedOn(position);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(sc);
        });
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //mToolbar.setNavigationIcon(R.mipmap.ic_action_menu);
        getSupportActionBar().setTitle(R.string.activity_name_main);
    }
}
