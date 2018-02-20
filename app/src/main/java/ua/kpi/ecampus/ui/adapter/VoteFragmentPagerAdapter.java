package ua.kpi.ecampus.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.ui.fragment.VoteArchiveFragment;
import ua.kpi.ecampus.ui.fragment.VoteCurrentFragment;

public class VoteFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public VoteFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new VoteCurrentFragment();
        } else {
            return new VoteArchiveFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.category_current);
            case 1:
                return mContext.getString(R.string.category_archive);
            default:
                return null;
        }
    }

}