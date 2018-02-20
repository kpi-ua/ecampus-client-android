package ua.kpi.ecampus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.view.OnItemClickListener;

import static ua.kpi.ecampus.util.TermPredicates.filter;
import static ua.kpi.ecampus.util.TermPredicates.isMatchesId;

/**
 * VoteAdapter manages VoteTeacher data model and adapts it to
 * RecyclerView, which is in VoteStudentActivity.
 */
public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private List<VoteTeacher> mAllData = new ArrayList<>();
    private List<VoteTeacher> mCurrentData = new ArrayList<>();

    /**
     * Set data to adapter
     *
     * @param list
     */
    public void setAllItems(List<VoteTeacher> list) {
        mAllData = list;
        notifyItemRangeInserted(0, list.size());
    }

    /**
     * Update item in adapter
     *
     * @param item
     */
    public void updateItem(VoteTeacher item) {
        int pos = getItemPosition(item);
        VoteTeacher adapterItem = getItem(pos);
        adapterItem.setCriteria(item.getCriteria());
        adapterItem.setIsVoted(item.isVoted());
        adapterItem.setAvgResult(item.getAvgResult());

        mCurrentData.remove(pos);
        notifyItemRemoved(pos);
        mCurrentData.add(pos, adapterItem);
        notifyItemInserted(pos);
    }

    /**
     * Collect and set as current data that matches specified term
     *
     * @param termId term identifier
     */
    public void filterByTerm(Integer termId) {
        mCurrentData.clear();
        mCurrentData.addAll(filter(mAllData, isMatchesId(termId)));
        notifyItemRangeInserted(0, mCurrentData.size());
    }

    @Override
    public VoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_vote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VoteAdapter.ViewHolder holder, int position) {
        VoteTeacher teacher = getItem(position);
        holder.tvTeacherName.setText(teacher.getTeacherName());
        if (!teacher.isVoted())
            holder.imageVoted.setVisibility(View.INVISIBLE);
        else
            holder.imageVoted.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mCurrentData.size();
    }

    /**
     * Get item by specified position
     *
     * @param pos - position
     * @return item
     */
    public VoteTeacher getItem(int pos) {
        return mCurrentData.get(pos);
    }

    /**
     * Set listener which is invoked when a item is clicked.
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Get position in adapter of item with specified id
     *
     * @param item
     * @return position
     */
    private int getItemPosition(VoteTeacher item) {
        int position = -1;
        for (VoteTeacher t : mCurrentData) {
            position++;
            if (t.getTeacherId().equals(item.getTeacherId()))
                break;
        }
        return position;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_teacher_name) TextView tvTeacherName;
        @BindView(R.id.image_voted) ImageView imageVoted;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_feedback)
        public void itemClick(View view) {
            if (mListener != null) {
                int pos = getAdapterPosition();
                mListener.onItemClicked(view, pos, mCurrentData.get(pos));
            }
        }
    }
}
