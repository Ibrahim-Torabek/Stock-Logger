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
 * <h3>This class is a custom adapter of View Pager 2</h3>
 * This class extends FragmentStateAdapter class\n
 * Contact Fragment and Credits fragment will share this adapter to show information.
 * It will display three categories of contact or credits information.
 *
 * categories:
 * <ul>
 *     <li>Phone Call Information</li>
 *     <li>Text Message Information</li>
 *     <li>Email Information</li>
 * </ul>
 *
 * Credits:
 * <ul>
 *     <li>Icons</li>
 *     <li>Pictures</li>
 *     <li>Other</li>
 * </ul>
 *
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 10/04/21
 * @see FragmentStateAdapter
 * @see ContactFragment
 * @see ibrahim.example.stocklogger.fragments.CreditsFragment
 */
public class ContactViewPagerAdapter extends FragmentStateAdapter {
    public static final int PAGE_CONTACT = 1;
    public static final int PAGE_CREDITS = 2;

    int currentPage;


    public ContactViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int currentPage) {
        super(fragmentActivity);
        // Receive current page from parent Fragment(Contact or Credit) and determine witch Fragment to show.
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
