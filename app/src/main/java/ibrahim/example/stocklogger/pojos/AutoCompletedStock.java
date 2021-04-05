package ibrahim.example.stocklogger.pojos;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 04/04/21
 */
public class AutoCompletedStock {
    private String symbol;
    private String companyName;
    private boolean isUSD = false;

    public AutoCompletedStock(String symbol, String companyName, boolean isUSD) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.isUSD = isUSD;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isUSD() {
        return isUSD;
    }

    public void setUSD(boolean USD) {
        isUSD = USD;
    }
}
