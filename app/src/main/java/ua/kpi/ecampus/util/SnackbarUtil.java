package ua.kpi.ecampus.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * SnackbarUtil contains utility methods related with the Snackbar class.
 */
public class SnackbarUtil {

    private SnackbarUtil() {}

    public static void show(final String message, final View view) {
        getSnackbar(message, view).show();
    }

    private static Snackbar getSnackbar(String message, View view) {
        return Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
    }
}
