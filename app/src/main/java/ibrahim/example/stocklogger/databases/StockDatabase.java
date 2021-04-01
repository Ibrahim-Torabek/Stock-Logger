package ibrahim.example.stocklogger.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ibrahim.example.stocklogger.pojos.SoldStock;

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

    /**
     * Table columns
     */

    public static final String COLUMN_ID = "";

    // Stock Table
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_COMPANY_NAME = "company_name";
    public static final String COLUMN_LAST_PRICE = "last_price";
    public static final String COLUMN_WORTH = "worth";
    public static final String COLUMN_QUANTITY = "quantity";

    // Active Table
    public static final String COLUMN_STOCK_ID = "stock_id";
    public static final String COLUMN_PRICE = "price";

    // Create Active Table
    public static final String CREATE_ACTIVE_TABLE = "CREATE TABLE " +
            TABLE_ACTIVE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_STOCK_ID + " INTEGER, " +
            COLUMN_QUANTITY + " TEXT, " +
            COLUMN_PRICE + " TEXT)";




    public StockDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ACTIVE_TABLE);
        /**
         * TODO:
         *   - Create other tables
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
