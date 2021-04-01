//package ibrahim.example.stocklogger.databases;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//
//import ibrahim.example.stocklogger.pojos.ActiveStock;
//import ibrahim.example.stocklogger.pojos.SoldStock;
//
///**
// * <h1>App for Stock Logger</h1>
// * <h2>Android project of MAD405 Course</h2>
// *
// * This is a database class for active stocks. It executes CRUD operations for the database.
// * This class extends SQLiteOpenHelper class to operate the SQLite database.
// *
// * @see android.database.sqlite.SQLiteOpenHelper
// * @see SoldStock
// *
// * @author Ibrahim (Wusiman Yibuulayin)
// * @version 1.0
// * @since 2021-01-27
// */
//
//public class SoldStockDatabase extends SQLiteOpenHelper {
//    // Set database constants
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "stock";
//    public static final String TABLE_SOLD = "sold";
//
//    // Set database columns
//    public static final String COLUMN_ID = "id";
//    public static final String COLUMN_SYMBOL = "symbol";
//    public static final String COLUMN_COMPANY_NAME = "companyName";
//    public static final String COLUMN_SOLD_PRICE = "soldPrice";
//    public static final String COLUMN_EARNING = "earning";
//
//    // Set create database syntax
//    public static final String CREATE_SOLD_TABLE = "CREATE TABLE " +
//            TABLE_SOLD + "(" +
//            COLUMN_ID + " INTEGER PRIMARY KEY," +
//            COLUMN_SYMBOL + " TEXT, " +
//            COLUMN_COMPANY_NAME + " TEXT, " +
//            COLUMN_SOLD_PRICE + " TEXT, " +
//            COLUMN_EARNING + " TEXT)";
//
//    // Constructor for super class
//    public SoldStockDatabase(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    // Implement methods
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    // CRUD operations
//    // Add a new Stock to the sold table
//    public void addStock(SoldStock stock){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(COLUMN_SYMBOL,stock.getSymbol());
//        values.put(COLUMN_COMPANY_NAME,stock.getCompanyName());
//        values.put(COLUMN_SOLD_PRICE,stock.getSoldPrice());
//        values.put(COLUMN_EARNING,stock.getEarning());
//
//        db.insert(TABLE_SOLD,null,values);
//        db.close();
//    }
//
//    // get all stocks from the sold table
//    public ArrayList<SoldStock> getAllStocks(){
//        ArrayList<SoldStock> stocks = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SOLD, null);
//        while (cursor.moveToNext()){
//            stocks.add(
//                    new SoldStock(
//                            cursor.getInt(0),
//                            cursor.getString(1),
//                            cursor.getString(2),
//                            cursor.getDouble(3),
//                            cursor.getDouble(4)
//                    )
//            );
//        }
//
//        db.close();
//        return  stocks;
//    }
//
//    // get a stock from sold table by id
//    public SoldStock getStock(int id){
//        SQLiteDatabase db = getReadableDatabase();
//        SoldStock stock = null;
//
//        Cursor cursor = db.query(
//                TABLE_SOLD,
//                new String[]{
//                        COLUMN_ID,
//                        COLUMN_SYMBOL,
//                        COLUMN_COMPANY_NAME,
//                        COLUMN_SOLD_PRICE,
//                        COLUMN_EARNING
//                },
//                COLUMN_ID + " = ?",
//                new String[]{
//                        String.valueOf(id)
//                },
//                null,
//                null,
//                null
//        );
//
//        if(cursor.moveToFirst()){
//            stock = new SoldStock(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getDouble(3),
//                    cursor.getDouble(4)
//            );
//        }
//
//        db.close();
//        return stock;
//    }
//
//    // We only need add, get operations for this database, So I didn't create update and delete methods.
//
//}
