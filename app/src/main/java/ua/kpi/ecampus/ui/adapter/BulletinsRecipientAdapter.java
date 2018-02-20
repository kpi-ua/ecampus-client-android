package ua.kpi.ecampus.ui.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.ui.presenter.SaveBulletinPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * BulletinsRecipientAdapter manages recipient data (profile, subsystem,
 * group) and adapts it to RecyclerView, which is in New/Edit BulletinActivity.
 * This recycler view contains buffer list of recipients which have not added
 * to bulletin's recipient list yet.
 */
public class BulletinsRecipientAdapter extends RecyclerView
        .Adapter<BulletinsRecipientAdapter.ViewHolder> {

    private final SaveBulletinPresenter.IView mView;
    private final int NOTIFY_DELAY = 500;
    private List<Recipient> mDataList = new ArrayList<>();

    public BulletinsRecipientAdapter(SaveBulletinPresenter.IView view) {
        mView = view;
    }

    public void setItems(List<Recipient> list) {
        mDataList = list;
        mView.updateBadgeCounter(getItemCount());
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList = new ArrayList<>();
        mView.updateBadgeCounter(getItemCount());
        notifyDataSetChanged();
    }

    public boolean contains(Recipient r) {
        return mDataList.contains(r);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_buffer_recipient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Recipient item = mDataList.get(position);
        holder.tvWhere.setText(item.getSubdivisionName());
        String profileName = item.getProfileName();
        if (profileName != null && !profileName.isEmpty()) {
            holder.tvWhom.setText(profileName);
        } else if (item.getStudyGroupName() != null) {
            holder.tvWhom.setText(item.getStudyGroupName());
        }
        holder.btnDelete.setOnClickListener(v -> removeItem(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<Recipient> getItems() {
        return mDataList;
    }

    public void addItem(final Recipient item) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mDataList.add(item);
            mView.updateBadgeCounter(getItemCount());
            notifyItemInserted(mDataList.size() - 1);
        }, NOTIFY_DELAY);

    }

    public void removeItem(final int position) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mDataList.remove(position);
            mView.updateBadgeCounter(getItemCount());
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDataList.size());
        }, NOTIFY_DELAY);

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_where) TextView tvWhere;
        @Bind(R.id.tv_whom) TextView tvWhom;
        @Bind(R.id.button_delete_buffer_recipient) ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
