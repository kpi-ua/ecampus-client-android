package ua.kpi.ecampus.ui.view;

import android.view.View;

/**
 * Interface for listening recycler view list events.
 */
public interface OnItemClickListener {

    void onItemClicked(View view, int position, Object item);

}
