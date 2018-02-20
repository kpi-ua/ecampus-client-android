package ua.kpi.ecampus.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.adapter.VoteAdapter;
import ua.kpi.ecampus.ui.presenter.VotingStudentPresenter;
import ua.kpi.ecampus.ui.view.OnItemClickListener;

public class VoteCurrentFragment extends Fragment {

    @Bind(R.id.recyclerview_teachers) RecyclerView mRecyclerView;
    @Bind(R.id.spinner_terms) Spinner mSpinnerTerms;

    @Inject VotingStudentPresenter mPresenter;

    private VoteAdapter mAdapter;

    public VoteCurrentFragment() {
        // Required empty public constructor
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote_current, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private OnItemClickListener onItemClickListener =
            (view, position, item) -> mPresenter.onItemClick(item);

    private void setRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSaveEnabled(true);
    }

    private void setVotingAdapter(List<VoteTeacher> teachers) {
        mAdapter = new VoteAdapter();
        mAdapter.setAllItems(teachers);
        mAdapter.setHasStableIds(true);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }
}