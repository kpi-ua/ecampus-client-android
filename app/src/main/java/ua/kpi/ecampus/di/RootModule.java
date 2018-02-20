package ua.kpi.ecampus.di;

import android.content.Context;
import android.view.LayoutInflater;

import ua.kpi.ecampus.CampusApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module created to work as junction of every module with an
 * application scope.
 */
@Module(injects = {CampusApplication.class}, library = true)
public final class RootModule {

    private final Context context;

    public RootModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideApplicationContext() {
        return context;
    }

    @Provides
    LayoutInflater provideLayputInflater() {
        return LayoutInflater.from(context);
    }

}
