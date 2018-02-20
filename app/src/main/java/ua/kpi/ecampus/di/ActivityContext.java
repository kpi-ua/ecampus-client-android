package ua.kpi.ecampus.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation created to improve Context injection. This annotation is used
 * with @Inject for
 * Context class to return the current Activity context.
 * <p>
 * This annotation can be replaced with a @Named annotation, but configure
 * before the
 * ActivityModule.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ActivityContext {
}
