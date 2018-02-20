package ua.kpi.ecampus.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import ua.kpi.ecampus.CampusApplication;
import ua.kpi.ecampus.di.ActivityModule;

/**
 * Base activity created to be extended by every activity class.
 * This class provides dependency injection configuration, ButterKnife
 * Android library configuration and some methods common to every activity.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);

    }

    /**
     * Create a new Dagger ObjectGraph to add new dependencies using a plus
     * operation and inject the declared one in the activity. This new graph
     * will be destroyed once the activity lifecycle finish.
     * <p>
     * This is the key of how to use Activity scope dependency injection.
     */
    private void injectDependencies() {
        CampusApplication application = (CampusApplication) getApplication();
        List<Object> modules = getModules();
        modules.add(new ActivityModule(this));
        objectGraph = application.plus(modules);
        inject(this);
    }

    /**
     * Method used to resolve dependencies provided by Dagger modules. Inject
     * an object to provide
     * every @Inject annotation contained.
     *
     * @param object to inject.
     */
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    /**
     * Get a list of Dagger modules with Activity scope needed to this Activity.
     *
     * @return modules with new dependencies to provide.
     */
    protected abstract List<Object> getModules();

    /**
     * Replace every field annotated with ButterKnife annotations like @Bind
     * with the proper
     * value.
     */
    protected void bindViews() {
        ButterKnife.bind(this);
    }
}
