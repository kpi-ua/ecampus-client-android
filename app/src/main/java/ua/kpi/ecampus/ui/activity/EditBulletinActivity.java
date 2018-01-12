package ua.kpi.ecampus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.ui.presenter.SaveBulletinPresenter;
import ua.kpi.ecampus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for edition of a Bulletin.
 * Created by Administrator on 22.04.2016.
 */
public class EditBulletinActivity extends SaveBulletinActivity implements
        SaveBulletinPresenter.IView {

    private Bulletin mCurrentBulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mCurrentBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_bulletin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_delete:
                mPresenter.onStartRequest(() -> mPresenter.deleteBulletin());
                break;
            case R.id.action_clear:
                clearValues();
                break;
            case R.id.action_done:
                if (isValidInput())
                    mPresenter.onStartRequest(() -> mPresenter.editBulletin());
                break;
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
        setRadioGroup();
        setAdapter();
        setDateListener();
        setInitialViewValues();
    }

    @Override
    public void showResponse(int code, String msg) {
        switch (code) {
            case 0:
                ToastUtil.showShortMessage(msg, this);
                break;
            case 200:
                ToastUtil.showShortMessage(getString(R.string
                        .bulletin_is_modified), this);
                break;
            case 400:
                ToastUtil.showShortMessage(getString(R.string.bad_bulletin),
                        this);
                break;
            case 401:
                ToastUtil.showShortMessage(getString(R.string.unauthorized),
                        this);
                break;
            case 500:
                ToastUtil.showShortMessage(getString(R.string.server_error),
                        this);
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void setRecipientsList(List<Recipient> list) {
        mAdapter.setItems(list);
    }

    @Override
    public Bulletin formBulletin() {
        mCurrentBulletin.setCreatorId(User.getInstance().id);
        mCurrentBulletin.setSubject(mSubject.getText().toString());
        mCurrentBulletin.setText(mText.getText().toString());
        mCurrentBulletin.setDateStart(mStartDate.getText().toString());
        mCurrentBulletin.setDateStop(mEndDate.getText().toString());
        mCurrentBulletin.setRecipientList(mAdapter.getItems());
        return mCurrentBulletin;
    }

    @Override
    public String getBulletinId() {
        return mCurrentBulletin.getId();
    }

    private void setInitialViewValues() {
        if (mCurrentBulletin == null)
            return;
        mSubject.setText(mCurrentBulletin.getSubject());
        mText.setText(mCurrentBulletin.getText());
        mStartDate.setText(mCurrentBulletin.getDateStart());
        mEndDate.setText(mCurrentBulletin.getDateStop());
        mCreateDate.setText(mCurrentBulletin.getDateCreate());

        TextView tv = findViewById(R.id.text_view_actuality_value);
        if (mCurrentBulletin.getActuality())
            tv.setText(R.string.yes);
        else
            tv.setText(R.string.no);
        tv = findViewById(R.id
                .text_view_change_actuality_date_value);
        tv.setText(mCurrentBulletin.getDateCreate());
        tv = findViewById(R.id.text_view_author_name);
        tv.setText(User.getInstance().name);

        mPresenter.loadRecipients();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.edit_bulletin);
    }
}
