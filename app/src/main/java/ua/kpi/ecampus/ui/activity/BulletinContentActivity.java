package ua.kpi.ecampus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.ui.presenter.BulletinContentPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * This activity represents content of a Bulletin.
 */
public class BulletinContentActivity extends BaseActivity implements
        BulletinContentPresenter.IView {
    @Bind(ua.kpi.ecampus.R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    BulletinContentPresenter mPresenter;
    Bulletin mBulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ua.kpi.ecampus.R.layout.activity_bulletin_content);
        bindViews();

        mBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
        setValuesInViews();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(ua.kpi.ecampus.R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(ua.kpi.ecampus.R.string.bulletin_text_content);
    }

    private void setValuesInViews() {
        if (mBulletin == null)
            return;

        TextView textView;
        textView = findViewById(ua.kpi.ecampus.R.id.text_view_bulletin_theme);
        textView.setText(mBulletin.getSubject());

        textView = findViewById(ua.kpi.ecampus.R.id.text_view_bulletin_text);
        textView.setText(mBulletin.getText());

        textView = findViewById(ua.kpi.ecampus.R.id.text_view_bulletin_start_date);
        textView.setText(mBulletin.getDateStart());

        textView = findViewById(ua.kpi.ecampus.R.id.text_view_bulletin_end_date);
        textView.setText(mBulletin.getDateStop());

        textView = findViewById(ua.kpi.ecampus.R.id.text_view_bulletin_author);
        textView.setText(mBulletin.getCreatorName());
    }
}
