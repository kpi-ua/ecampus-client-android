package ua.kpi.ecampus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.ui.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * MainNotAuthPresenter created to manage MainNotAuthActivity.
 */
public class MainNotAuthPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;

    @Inject
    public MainNotAuthPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public List<Subsystem> getData() {
        List<Subsystem> subsystems = new ArrayList<>();

        Resources res = getResources();
        String[] names = getSubsystemNames(res);
        TypedArray icons = getSubsystemIcon(res);
        for (int i = 0; i < names.length && i < icons.length(); i++) {
            Subsystem s = new Subsystem(names[i], icons.getResourceId(i, -1));
            subsystems.add(s);
        }
        return subsystems;
    }

    private String[] getSubsystemNames(Resources res) {
        return res.getStringArray(R.array.partial_subsystem);
    }

    private TypedArray getSubsystemIcon(Resources res) {
        return res.obtainTypedArray(R.array.partial_subsystem_image);
    }

    private Resources getResources() {
        return mContext.getResources();
    }

    public void openLogin() {
        mNavigator.startLoginActivity();
    }

    public interface IView {
        void setViewComponent();
    }
}
