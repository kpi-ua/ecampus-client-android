package ua.kpi.ecampus;

import android.app.Application;

import ua.kpi.ecampus.di.RootModule;

import java.util.List;

import dagger.ObjectGraph;

public class CampusApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
    }

    /**
     * Extend the dependency container graph will new dependencies provided by the modules passed as
     * arguments.
     *
     * @param modules used to populate the dependency container.
     */
    public ObjectGraph plus(List<Object> modules) {
        if(modules == null) {
            throw new IllegalArgumentException("Module is null. Review your getModules() implementation");
        }
        return objectGraph.plus(modules.toArray());
    }

    /**
     * Inject every dependency declared in the object with the @Inject annotation if the dependency
     * has been already declared in a module and already initialized by Dagger.
     *
     * @param object to inject.
     */
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    private void initializeDependencyInjector() {
        objectGraph = ObjectGraph.create(new RootModule(this));
        objectGraph.inject(this);
        objectGraph.injectStatics();
    }

}
