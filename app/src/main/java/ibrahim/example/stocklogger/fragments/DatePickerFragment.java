package ibrahim.example.stocklogger.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>This is a custom dialog fragment to pick a date. </h3>
 *
 * It extends DialogFragment class
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 04/04/21
 * @see DialogFragment
 */
public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public DatePickerFragment() {}

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get current date
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // return user selected date
        return new DatePickerDialog(
                getActivity(),
                (DatePickerDialog.OnDateSetListener) getActivity(),
                year,
                month,
                day
        );
    }
}
