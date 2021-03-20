package ibrahim.example.stocklogger.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import ibrahim.example.stocklogger.pojos.ActiveStock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a database class for active stocks. It executes CRUD operations for the database.
 * This class extends SQLiteOpenHelper class to operate the SQLite database.
 *
 * @see android.database.sqlite.SQLiteOpenHelper
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-01-27
 */


public class ActiveStockDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stock";
    public static final String TABLE_ACTIVE = "active";


    public ActiveStockDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_COMPANY_NAME = "companyName";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";

    public static final String CREATE_ACTIVE_TABLE = "CREATE TABLE " +
            TABLE_ACTIVE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_SYMBOL + " TEXT, " +
            COLUMN_COMPANY_NAME + " TEXT, " +
            COLUMN_QUANTITY + " TEXT, " +
            COLUMN_PRICE + " TEXT)";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ACTIVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // CRUD operations

    public void addStock(ActiveStock stock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SYMBOL,stock.getSymbol());
        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
        values.put(COLUMN_QUANTITY,stock.getQuantity());
        values.put(COLUMN_PRICE,stock.getPrice());

        db.insert(TABLE_ACTIVE,null,values);
        db.close();
    }

    public ArrayList<ActiveStock> getAllStocks(){
        ArrayList<ActiveStock> stocks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACTIVE, null);
        while (cursor.moveToNext()){
            stocks.add(
                    new ActiveStock(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getInt(4)
                    )
            );
        }

        db.close();
        return  stocks;
    }

    public ArrayList<ActiveStock> getStockBySymbol(String symbol){
        ArrayList<ActiveStock> activeStocks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACTIVE + " WHERE symbol = ?", new String[]{symbol});
        while (cursor.moveToNext()) {
            activeStocks.add(
                    new ActiveStock(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getInt(4)
                    )
            );
        }

        db.close();

        return activeStocks;
    }

    public ArrayList<String> getStockSymbols(){
        ArrayList<String> symbols = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_SYMBOL + " FROM " + TABLE_ACTIVE, null);
        symbols.add(cursor.getString(0));

        db.close();
        return symbols;
    }

    public int updateStock(ActiveStock stock){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SYMBOL,stock.getSymbol());
        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
        values.put(COLUMN_PRICE,stock.getPrice());
        values.put(COLUMN_QUANTITY,stock.getQuantity());
        return db.update(TABLE_ACTIVE, values, COLUMN_ID + " = ?", new String[]{String.valueOf(stock.getId())});
    }

    public void deleteStock(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACTIVE,COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteStockBySymbol(String symbol){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ACTIVE, COLUMN_SYMBOL + " = ?", new String[]{symbol});
        db.close();
    }

}
