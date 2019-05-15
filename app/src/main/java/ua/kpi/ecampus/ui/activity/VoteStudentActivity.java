package ua.kpi.ecampus.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.adapter.VoteAdapter;
import ua.kpi.ecampus.ui.adapter.VoteFragmentPagerAdapter;
import ua.kpi.ecampus.ui.presenter.VoteStudentPresenter;

public class VoteStudentActivity extends BaseActivity implements VoteStudentPresenter.IView {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    //@BindView(R.id.recyclerview_teachers) RecyclerView mRecyclerView;
    //@BindView(R.id.spinner_terms) Spinner mSpinnerTerms;
    @BindView(R.id.viewpager) ViewPager mViewpager;
    @BindView(R.id.sliding_tabs) TabLayout mSlidingTabs;

    @Inject
    VoteStudentPresenter mPresenter;

    private VoteAdapter mAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_student);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();

        mPresenter.loadVoting();

        VoteFragmentPagerAdapter voteFragmentPagerAdapter =
                new VoteFragmentPagerAdapter(this, getSupportFragmentManager());
        mViewpager.setAdapter(voteFragmentPagerAdapter);
        mSlidingTabs.setupWithViewPager(mViewpager);
    }

    @Override protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override public void setViewComponent() {
        setToolbar();
    }

    @Override public void setVoteInProgressAdapter(List<VoteTeacher> teachers) {
        setVoteAdapter(teachers);
    }

    @Override public void setVoteEndedAdapter(List<VoteTeacher> teachers) {

    }

    @Override public void setTermsSpinner(List<Item> list) {
        //ArrayAdapter<Item> adapter =
        //    new ItemSpinnerAdapter(this, R.layout.spinner_item, R.layout.spinner_dropdown_item, list);
        //mSpinnerTerms.setAdapter(
        //    new NothingSelectedAdapter(adapter, R.layout.spinner_nothing_selected_terms, this));
        //mSpinnerTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //
        //  @Override
        //  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //    Item item = (Item) parent.getItemAtPosition(position);
        //    if (item != null) {
        //      if (mAdapter == null) {
        //        (findViewById(R.id.tv_title_teachers)).setVisibility(View.VISIBLE);
        //        setRecyclerView();
        //        mPresenter.setSpecificAdapter();
        //      }
        //      mAdapter.filterByTerm(item.getId());
        //    }
        //  }
        //
        //  @Override public void onNothingSelected(AdapterView<?> parent) {
        //    // N/A
        //  }
        //});
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Config.REQUEST_CODE) {
            VoteTeacher teacher = data.getParcelableExtra(Config.KEY_TEACHER);
            mAdapter.updateItem(teacher);
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_voting);
    }

    private void setRecyclerView() {
        //mRecyclerView.setVisibility(View.VISIBLE);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setSaveEnabled(true);
    }

    private void setVoteAdapter(List<VoteTeacher> teachers) {
        //mAdapter = new VotingAdapter();
        //mAdapter.setAllItems(teachers);
        //mAdapter.setHasStableIds(true);
        //mAdapter.setOnItemClickListener(onItemClickListener);
        //mRecyclerView.setAdapter(mAdapter);
    }
}