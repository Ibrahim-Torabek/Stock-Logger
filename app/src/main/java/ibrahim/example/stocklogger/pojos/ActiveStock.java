package ibrahim.example.stocklogger.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a class for active stocks. It stores information of stock
 * which user bought. We will pass the object of this class among fragments,
 * so we need to implement Parcelable interface.
 *
 * @see Parcelable
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-03-20
 */


public class ActiveStock implements Parcelable {
    private int id;
    private String symbol;
    private String companyName;
    private double price;
    private int quantity;

    public ActiveStock() {
    }

    public ActiveStock(String symbol, String companyName, double price, int quantity) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.quantity = quantity;
    }

    public ActiveStock(int id, String symbol, String companyName, double price, int quantity) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.quantity = quantity;
    }


    protected ActiveStock(Parcel in) {
        id = in.readInt();
        symbol = in.readString();
        companyName = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<ActiveStock> CREATOR = new Creator<ActiveStock>() {
        @Override
        public ActiveStock createFromParcel(Parcel in) {
            return new ActiveStock(in);
        }

        @Override
        public ActiveStock[] newArray(int size) {
            return new ActiveStock[size];
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
        parcel.writeDouble(price);
        parcel.writeInt(quantity);
    }
}
