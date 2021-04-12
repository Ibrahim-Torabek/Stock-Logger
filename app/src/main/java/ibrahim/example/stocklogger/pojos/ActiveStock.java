package ibrahim.example.stocklogger.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a class for active stocks. It stores information of stock
 * which user bought.
 *
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-03-20
 */


public class ActiveStock  {
    private int id;
    private String symbol;
    private String companyName;
    private double price;
    private int quantity;
    private String boughtDate;

    public ActiveStock() {
    }

    public ActiveStock(String symbol, String companyName, double price, int quantity, String boughtDate) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.quantity = quantity;
        this.boughtDate = boughtDate;
    }

    public ActiveStock(int id, String symbol, String companyName, double price, int quantity, String boughtDate) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.quantity = quantity;
        this.boughtDate = boughtDate;
    }

    protected ActiveStock(Parcel in) {
        id = in.readInt();
        symbol = in.readString();
        companyName = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate = boughtDate;
    }

    @Override
    public String toString() {
        return companyName;
    }
}
