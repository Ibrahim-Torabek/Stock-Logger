package ibrahim.example.stocklogger.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

import ibrahim.example.stocklogger.pojos.ActiveStock;
import ibrahim.example.stocklogger.pojos.SoldStock;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android project of MAD405 Course</h2>
 *
 * This is a database class for active stocks. It executes CRUD operations for the database.
 * This class extends SQLiteOpenHelper class to operate the SQLite database.
 *
 * @see android.database.sqlite.SQLiteOpenHelper
 * @see SoldStock
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-01-27
 */


public class StockDatabase extends SQLiteOpenHelper {
    // Set database constants
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stock";

    /**
     * Table names
     */
    public static final String TABLE_STOCK = "stock";
    public static final String TABLE_ACTIVE = "active";
    public static final String TABLE_SOLD = "sold";
    public static final String TABLE_STOCK_ACTIVE = "stock_active";

    /**
     * Table columns
     */

    public static final String COLUMN_ID = "id";

    // Stock Table
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_COMPANY_NAME = "company_name";
    public static final String COLUMN_LAST_PRICE = "last_price";
    public static final String COLUMN_WORTH = "worth";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IS_USD = "is_usd";

    // Active Table
    public static final String COLUMN_STOCK_ID = "stock_id";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_BOUGHT_DATE = "bought_date";

    // Sold Table
    public static final String COLUMN_SOLD_PRICE = "sold_price";
    public static final String COLUMN_EARNING = "earning";
    public static final String COLUMN_SOLD_DATE = "soldDate";

    // Stock_active table
    public static final String COLUMN_STOCK = "stock";
    public static final String COLUMN_ACTIVE = "active";

    /**
     * Create tables
     */

    // Create Stock Table
    public static final String CREATE_STOCK_TABLE = "CREATE TABLE " +
            TABLE_STOCK + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_SYMBOL + " TEXT, " +
            COLUMN_COMPANY_NAME + " TEXT, " +
            COLUMN_LAST_PRICE + " TEXT, " +
            COLUMN_WORTH + " TEXT, " +
            COLUMN_QUANTITY + " TEXT ," +
            COLUMN_IS_USD + " INTEGER)";

    // Create Active Table
    public static final String CREATE_ACTIVE_TABLE = "CREATE TABLE " +
            TABLE_ACTIVE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_SYMBOL + " TEXT, " +
            COLUMN_COMPANY_NAME + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_QUANTITY + " TEXT, " +
            COLUMN_BOUGHT_DATE + " TEXT)";

    // Create Sold Table
    public static final String CREATE_SOLD_TABLE = "CREATE TABLE " +
            TABLE_SOLD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_SYMBOL + " TEXT, " +
            COLUMN_COMPANY_NAME + " TEXT, " +
            COLUMN_SOLD_PRICE + " TEXT, " +
            COLUMN_EARNING + " TEXT, " +
            COLUMN_SOLD_DATE + " TEXT)";

    // Create Active_Sold Table
    public static final String CREATE_STOCK_ACTIVE_TABLE = "CREATE TABLE " +
            TABLE_STOCK_ACTIVE + " (" +
            COLUMN_STOCK + " INTEGER REFERENCES " + TABLE_STOCK + "(" + COLUMN_ID + "), " +
            COLUMN_ACTIVE + " INTEGER REFERENCES " + TABLE_ACTIVE + "(" + COLUMN_ID +"))";

    public StockDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STOCK_TABLE);
        sqLiteDatabase.execSQL(CREATE_ACTIVE_TABLE);
        sqLiteDatabase.execSQL(CREATE_SOLD_TABLE);
        //System.out.println("DATABASE: " + CREATE_STOCK_ACTIVE_TABLE);
        sqLiteDatabase.execSQL(CREATE_STOCK_ACTIVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int addStock(Stock stock){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SYMBOL,stock.getSymbol());
        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
        values.put(COLUMN_LAST_PRICE,stock.getLastPrice());
        values.put(COLUMN_WORTH,stock.getWorth());
        values.put(COLUMN_QUANTITY,stock.getQuantity());
        if(stock.isUSD())
            values.put(COLUMN_IS_USD,1);

        db.insert(TABLE_STOCK,null,values);
        db.close();
        return getLastRowId();
    }
    public int addActiveStock(ActiveStock stock){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SYMBOL,stock.getSymbol());
        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
        values.put(COLUMN_PRICE,stock.getPrice());
        values.put(COLUMN_QUANTITY,stock.getQuantity());
        values.put(COLUMN_BOUGHT_DATE,stock.getBoughtDate());

        db.insert(TABLE_ACTIVE,null,values);
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            db.close();
            return id;
        }
        db.close();
        return -1;


//        db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
//        if(cursor.moveToFirst()){
//            int id = cursor.getInt(0);
//            db.close();
//            return id;
//        }
//        db.close();
//        return -1;
    }
    public void addSoldStock(SoldStock stock){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SYMBOL,stock.getSymbol());
        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
        values.put(COLUMN_SOLD_PRICE,stock.getSoldPrice());
        values.put(COLUMN_EARNING,stock.getEarning());
        values.put(COLUMN_SOLD_DATE,stock.getSoldDate());


        db.insert(TABLE_SOLD,null,values);
        db.close();
    }
    public void addStockActive(int stock, int active){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STOCK, stock);
        values.put(COLUMN_ACTIVE, active);

        db.insert(TABLE_STOCK_ACTIVE, null, values);
        db.close();
    }


    public int getStockId(String symbol){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_STOCK +
                " WHERE " + COLUMN_SYMBOL + " = '" + symbol + "'";

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            db.close();
            return id;
        }

        db.close();
        return -1;
    }

    public Stock getStock(int id){
        Stock stock = new Stock();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_STOCK +
                        " WHERE " + COLUMN_ID + " = " + id,
                null
        );

        if(cursor.moveToFirst()){
            return new Stock(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5),
                    cursor.getInt(6) == 1 ? true : false
            );
        }


        db.close();
        return null;
    }

    public ArrayList<Stock> getAllStocks(){
        ArrayList<Stock> stocks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STOCK, null);
        while (cursor.moveToNext()){
            // get is_usd column as integer, and save as boolen.
            boolean isUSD = cursor.getInt(6) == 1 ? true : false;
            stocks.add(
                    new Stock(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getDouble(4),
                            cursor.getInt(5),
                            isUSD
                    )
            );
        }

        db.close();
        return  stocks;
    }
    // get all stocks from active stock table
    public ArrayList<ActiveStock> getAllActiveStocks(){
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
                            cursor.getInt(4),
                            cursor.getString(5)
                    )
            );
        }

        db.close();
        return  stocks;
    }

    public ArrayList<ActiveStock> getAllActiveStocks(int stock){
        ArrayList<ActiveStock> stocks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_STOCK_ACTIVE +
                " WHERE " + COLUMN_STOCK + " = " + stock;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String innerQuery = "SELECT * FROM " + TABLE_ACTIVE +
                        " WHERE " + COLUMN_ID + " = " +
                        cursor.getInt(1);
                Cursor innerCursor = db.rawQuery(innerQuery, null);
                if (innerCursor.moveToFirst()) {
                    stocks.add(
                            new ActiveStock(
                                    innerCursor.getInt(0),
                                    innerCursor.getString(1),
                                    innerCursor.getString(2),
                                    innerCursor.getDouble(3),
                                    innerCursor.getInt(4),
                                    innerCursor.getString(5)
                            )
                    );
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return stocks;
    }


    // get all stocks from active stock table
    public ArrayList<SoldStock> getAllSoldStocks(){
        ArrayList<SoldStock> stocks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SOLD, null);
        while (cursor.moveToNext()){
            stocks.add(
                    new SoldStock(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getDouble(4),
                            cursor.getString(5)
                    )
            );
        }

        db.close();
        return  stocks;
    }

    public int updateStock(Stock stock){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SYMBOL,stock.getSymbol());
        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
        values.put(COLUMN_LAST_PRICE,stock.getLastPrice());
        values.put(COLUMN_WORTH,stock.getWorth());
        values.put(COLUMN_QUANTITY,stock.getQuantity());

        return db.update(TABLE_STOCK,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(stock.getId())});
    }

    private int getLastRowId(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            db.close();
            return id;
        }
        db.close();
        return -1;
    }

}
