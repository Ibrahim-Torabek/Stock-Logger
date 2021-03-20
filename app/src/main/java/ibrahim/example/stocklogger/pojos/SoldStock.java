package ibrahim.example.stocklogger.pojos;

import android.os.Parcelable;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a class for sold stocks. It stores information of stock
 * which user sold. We will pass the object of this class among fragments,
 * so we need to implement Parcelable interface.
 *
 * @see Parcelable
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-03-20
 */


public class SoldStock {
    private int id;
    private String symbol;
    private String companyName;
    private double soldPrice;
    private double earning;

    public SoldStock() {
    }

    public SoldStock(String symbol, String companyName, double soldPrice, double earning) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.soldPrice = soldPrice;
        this.earning = earning;
    }

    public SoldStock(int id, String symbol, String companyName, double soldPrice, double earning) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.soldPrice = soldPrice;
        this.earning = earning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    @Override
    public String toString() {
        return companyName;
    }
}
