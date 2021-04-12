package ibrahim.example.stocklogger.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>Api SingleTon class</h3>
 * This class will declare a Volley request singleton class.
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 * @see Volley
 */
public class StockSingleton {
    public static StockSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private StockSingleton(Context context){
        this.context = context;
    }

    public static StockSingleton getInstance(Context context) {
        if(instance == null){
            instance = new StockSingleton(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }
}
