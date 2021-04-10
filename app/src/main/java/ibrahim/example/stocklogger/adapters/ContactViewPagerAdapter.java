package ibrahim.example.stocklogger.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.fragments.ContactFragment;
import ibrahim.example.stocklogger.fragments.contact.ContactCallFragment;
import ibrahim.example.stocklogger.fragments.contact.ContactEmailFragment;
import ibrahim.example.stocklogger.fragments.contact.ContactMessageFragment;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 10/04/21
 */
public class ContactViewPagerAdapter extends FragmentStateAdapter {
    public ContactViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:

                return  new ContactCallFragment();
            case 1:

                return new ContactMessageFragment();
            case 2:

                return new ContactEmailFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
