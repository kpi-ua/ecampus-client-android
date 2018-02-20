package ua.kpi.ecampus.ui.presenter;

/**
 * Abstract presenter to work as base for every presenter created in the application.
 * This presenter declares some methods to attach the fragment/activity lifecycle.
 */
public abstract class BasePresenter {

    /**
     * Called when activity/fragment (view) must be initialized.
     */
    public abstract void initializeViewComponent();
}
