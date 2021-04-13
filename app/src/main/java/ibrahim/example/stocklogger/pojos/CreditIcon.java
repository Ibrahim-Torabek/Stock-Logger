package ibrahim.example.stocklogger.pojos;

import androidx.annotation.NonNull;

import ibrahim.example.stocklogger.fragments.credits.CreditIconFragment;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * This class stores icon credits to list all icons in CreditIconFragment
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 10/04/21
 * @see CreditIconFragment
 */
public class CreditIcon {
    private int iconResource;
    private String iconName;
    private String iconURL;

    public CreditIcon(int iconResource, String iconName, String iconURL) {
        this.iconResource = iconResource;
        this.iconName = iconName;
        this.iconURL = iconURL;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getIconName() {
        return iconName;
    }

    public String getIconURL() {
        return iconURL;
    }

    @NonNull
    @Override
    public String toString() {
        return iconName;
    }
}
