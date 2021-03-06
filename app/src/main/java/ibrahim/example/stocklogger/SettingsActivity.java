package ibrahim.example.stocklogger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3> This is a setting activity to display settings preferences.</h3>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 07/04/21
 *
 * @see PreferenceFragment
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MySettingsFragment())
                .commit();
    }

    public static class MySettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d("SETTINGS","User Left");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Double usdRating = Double.parseDouble(preferences.getString("edit_text_preference_1","1.25"));

        Log.d("SETTINGS",usdRating.toString());
        System.out.println(usdRating.toString());
    }
}
