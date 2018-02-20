package ua.kpi.ecampus.di;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module to provide some activity scope module dependencies.
 * This module is going to be added to the graph generated for every activity
 * while the activity
 * creation lifecycle.
 */
@Module(
        library = true
)
public final class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityContext
    @Provides
    Context provideActivityContext() {
        return activity;
    }
}
