package ibrahim.example.stocklogger.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a class for stocks. It stores information of stock
 * which the user holds.
 *
 * @see Parcelable
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-03-20
 */


public class Stock implements Parcelable {

    private int id;
    private String symbol;
    private String companyName;
    private double lastPrice;
    private double worth;
    private int quantity;
    private boolean isUSD = false;

    public Stock() {
    }

    public Stock(String symbol, String companyName, double lastPrice, double worth, int quantity) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.lastPrice = lastPrice;
        this.worth = worth;
        this.quantity = quantity;
    }

    public Stock(int id, String symbol, String companyName, double lastPrice, double worth, int quantity) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.lastPrice = lastPrice;
        this.worth = worth;
        this.quantity = quantity;
    }

    public Stock(int id, String symbol, String companyName, double lastPrice, double worth, int quantity, boolean isUSD) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.lastPrice = lastPrice;
        this.worth = worth;
        this.quantity = quantity;
        this.isUSD = isUSD;
    }

    protected Stock(Parcel in) {
        id = in.readInt();
        symbol = in.readString();
        companyName = in.readString();
        lastPrice = in.readDouble();
        worth = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

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

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isUSD() {
        return isUSD;
    }

    public void setUSD(boolean USD) {
        isUSD = USD;
    }

    @Override
    public String toString() {
        return companyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(symbol);
        parcel.writeString(companyName);
        parcel.writeDouble(lastPrice);
        parcel.writeDouble(worth);
        parcel.writeInt(quantity);
    }
}
