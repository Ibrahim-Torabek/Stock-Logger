package ibrahim.example.stocklogger.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a class for sold stocks. It stores information of stock
 * which user sold.
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-03-20
 */


public class SoldStock{
    private int id;
    private String symbol;
    private String companyName;
    private double soldPrice;
    private double earning;
    private String soldDate;

    public SoldStock() {
    }

    public SoldStock(String symbol, String companyName, double soldPrice, double earning, String soldDate) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.soldPrice = soldPrice;
        this.earning = earning;
        this.soldDate = soldDate;
    }

    public SoldStock(int id, String symbol, String companyName, double soldPrice, double earning, String soldDate) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.soldPrice = soldPrice;
        this.earning = earning;
        this.soldDate = soldDate;
    }

    protected SoldStock(Parcel in) {
        id = in.readInt();
        symbol = in.readString();
        companyName = in.readString();
        soldPrice = in.readDouble();
        earning = in.readDouble();
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

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    @Override
    public String toString() {
        return companyName;
    }

}
