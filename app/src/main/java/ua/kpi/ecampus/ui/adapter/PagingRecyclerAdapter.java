package ua.kpi.ecampus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.ui.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Manages Bulletin data model and adapts it to RecyclerView, which is in
 * BulletinBoardActivity, BulletinBoardModeratorActivity..
 */
public class PagingRecyclerAdapter extends RecyclerView
        .Adapter<PagingRecyclerAdapter.ViewHolder> {

    private List<Bulletin> mData = new ArrayList<>();
    private OnItemClickListener mListener;
    private PopupMenu.OnMenuItemClickListener mMenuListener;
    private boolean mIsModerator = false;
    // after reorientation test this member
    // or one extra request will be sent after each reorientation
    private boolean mAllItemsLoaded;
    private Bulletin mClickedItem;

    public PagingRecyclerAdapter(boolean isModerator) {
        mIsModerator = isModerator;
    }

    public void addNewItems(List<Bulletin> items) {
        if (items.size() == 0) {
            mAllItemsLoaded = true;
            return;
        }
        mData.addAll(items);
        notifyItemInserted(getItemCount() - items.size());
    }

    public void setItems(List<Bulletin> list) {
        mData = list;
        notifyDataSetChanged();
    }

    public List<Bulletin> getItems() {
        return mData;
    }

    public boolean isAllItemsLoaded() {
        return mAllItemsLoaded;
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(getItem(position).getId());
    }

    public Bulletin getItem(int position) {
        return mData.get(position);
    }

    public Bulletin getLastItem() {
        return mData.get(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public PagingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(ua.kpi
                .ecampus.R.layout
                .recycler_item_bulletin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PagingRecyclerAdapter.ViewHolder holder, int
            position) {
        Bulletin bul = mData.get(position);
        holder.date.setText(bul.getDateCreate());
        holder.theme.setText(bul.getSubject());
        holder.author.setText(bul.getCreatorName());

        if (mIsModerator) {

            holder.btnOverflow.setVisibility(View.VISIBLE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener
                                                   listener) {
        mMenuListener = listener;
    }

    public Bulletin getClickedItem() {
        return mClickedItem;
    }

    public void setFilter(List<Bulletin> bulletins) {
        mData.clear();
        mData.addAll(bulletins);
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(ua.kpi.ecampus.R.id.text_view_bulletin_date)
        TextView date;
        @Bind(ua.kpi.ecampus.R.id.text_view_bulletin_theme)
        TextView theme;
        @Bind(ua.kpi.ecampus.R.id.text_view_bulletin_author)
        TextView author;
        @Bind(ua.kpi.ecampus.R.id.button_overflow)
        ImageButton btnOverflow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(ua.kpi.ecampus.R.id.layout_bulletin)
        public void itemClick(View view) {
            if (mListener != null) {
                int pos = getAdapterPosition();
                mListener.onItemClicked(view, pos, mData.get(pos));
            }
        }

        @OnClick(ua.kpi.ecampus.R.id.button_overflow)
        public void btnOverflowClick(final View view) {
            int pos = getAdapterPosition();
            mClickedItem = mData.get(pos);

            PopupMenu menu = new PopupMenu(view.getContext(), view);
            menu.inflate(ua.kpi.ecampus.R.menu.menu_popup_bulletin);
            menu.show();
            menu.setOnMenuItemClickListener(mMenuListener);
        }
    }
}
