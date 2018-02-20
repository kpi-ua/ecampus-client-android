package ua.kpi.ecampus.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import ua.kpi.ecampus.model.Recipient;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage loading and storage complements.
 * Implements Filterable interface to capture user input AutoCompleteTextView
 * and pass it as a search term into a web service.
 */
public class RecipientAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 20;

    private final Context mContext;
    private List<Recipient> mResults;

    public RecipientAutoCompleteAdapter(Context context) {
        mContext = context;
        mResults = new ArrayList<Recipient>();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Recipient getItem(int index) {
        return mResults.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        Recipient recipient = getItem(position);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText("Filter");

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Recipient> recipients = findRecipients(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = recipients;
                    filterResults.count = recipients.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                if (results != null && results.count > 0) {
                    mResults = (List<Recipient>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    /**
     * Returns a search result for the given profile.
     */
    private List<Recipient> findRecipients(String profile) {
        //return service.findRecipient(profile);
        return null;
    }

}
