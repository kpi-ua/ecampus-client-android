package ua.kpi.ecampus.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import ua.kpi.ecampus.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * DatePickerFragment hosts each date picker which provides controls for the
 * user to a date as ready-to-use dialogs.
 */
public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    EditText view;

    public void setView(EditText et) {
        view = et;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DateUtil
                .FORMAT,
                Locale.GERMAN);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);
        if (view != null) {
            view.setText(dateFormatter.format(newDate.getTime()));
        }
    }

}