package ibrahim.example.stocklogger.pojos;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 10/04/21
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

    @Override
    public String toString() {
        return iconName;
    }
}
