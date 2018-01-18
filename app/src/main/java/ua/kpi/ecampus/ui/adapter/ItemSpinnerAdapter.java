package ua.kpi.ecampus.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ua.kpi.ecampus.model.pojo.Item;

import java.util.List;

/**
 * ItemSpinnerAdapter manages Item data model and adapts it to Spinner view.
 * Created by Administrator on 14.04.2016.
 */
public class ItemSpinnerAdapter extends ArrayAdapter<Item> {

    protected Context context;
    private List<Item> values;
    protected int dropdownLayoutId;
    protected int layoutId;

    public ItemSpinnerAdapter(Context context, int layoutId, int
            dropdownLayoutId, List<Item> values) {
        super(context, layoutId, values);
        this.context = context;
        this.values = values;
        this.dropdownLayoutId = dropdownLayoutId;
        this.layoutId = layoutId;
    }

    public int getCount() {
        return values.size();
    }

    public Item getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(context.getResources().getColor(ua.kpi.ecampus.R.color
                .colorTextDark));
        label.setTextSize(14);
        label.setText(values.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate
                (dropdownLayoutId, parent,
                        false);
        TextView textView = convertView.findViewById(android.R.id
                .text1);
        textView.setTextColor(context.getResources().getColor(ua.kpi.ecampus.R.color
                .colorTextDark));
        textView.setTextSize(14);
        textView.setText(values.get(position).getName());
        return convertView;
    }
}
