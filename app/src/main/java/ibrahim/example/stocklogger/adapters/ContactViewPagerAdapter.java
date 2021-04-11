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
import ibrahim.example.stocklogger.fragments.credits.CreditIconFragment;
import ibrahim.example.stocklogger.fragments.credits.CreditOtherFragment;
import ibrahim.example.stocklogger.fragments.credits.CreditPictureFragment;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 10/04/21
 */
public class ContactViewPagerAdapter extends FragmentStateAdapter {
    public static final int PAGE_CONTACT = 1;
    public static final int PAGE_CREDITS = 2;

    int currentPage;


    public ContactViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int currentPage) {
        super(fragmentActivity);

        this.currentPage = currentPage;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:

                return currentPage == PAGE_CONTACT ? new ContactCallFragment() : new CreditIconFragment();
            case 1:

                return currentPage == PAGE_CONTACT ? new ContactMessageFragment(): new CreditPictureFragment();
            case 2:

                return currentPage == PAGE_CONTACT ? new ContactEmailFragment(): new CreditOtherFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
