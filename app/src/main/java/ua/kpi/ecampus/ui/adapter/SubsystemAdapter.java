package ua.kpi.ecampus.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.util.CollectionValidator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SubsystemAdapter manages Subsystem data model and adapts it to
 * RecyclerView, which is in MainNotAuthActivity.
 */
public class SubsystemAdapter extends RecyclerView.Adapter<SubsystemAdapter
        .ViewHolder> {

    private Context mContext;
    private List<Subsystem> mSubsystems;

    public SubsystemAdapter(Context context, List<Subsystem> subsystem) {
        mContext = context;
        mSubsystems = subsystem;
    }

    public void setData(List<Subsystem> list) {
        CollectionValidator.validateOnNull(list);
        mSubsystems = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recycler_item_subsystem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subsystem subsystem = mSubsystems.get(position);
        holder.name.setText(subsystem.getName());
        holder.image.setImageDrawable(ContextCompat.getDrawable(mContext,
                subsystem.getIconId()));
    }

    @Override
    public int getItemCount() {
        return (mSubsystems != null) ? mSubsystems.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_subsystem_name) TextView name;
        @Bind(R.id.image_view_subsystem_image) ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
