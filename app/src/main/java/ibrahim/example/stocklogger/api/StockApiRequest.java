package ibrahim.example.stocklogger.api;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.fragments.MainFragment;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>API Request class</h3>
 * It will complete stock api request, and get the stock price.
 * The api provider is alphavantage.co
 * It extends JsonObjectRequest class.
 *
 * Important: The Api key saved to .gitignore file ApiKey.java
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 * @see JsonObjectRequest
 */
public class StockApiRequest extends JsonObjectRequest {
    public static final String URL = "https://www.alphavantage.co/query?";
    public static final String APIKEY = ApiKey.ApiKey;  // ApiKey.java file
    public static final String GLOBAL_QUOTE = "GLOBAL_QUOTE";
    public static final String SYMBOL_SEARCH = "SYMBOL_SEARCH";

    private boolean success = false;

    /**
     * This is constructor of class.
     *
     * @param url  provided url
     * @param stock Stock class to get it'sprice
     * @param priceTextView TextView to put the gotten price
     * @param earningTextView TextView to put the earning
     * @param context Context for update TextViews
     */
    public StockApiRequest(String url, Stock stock, TextView priceTextView, TextView earningTextView,  Context context) {
        super(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get the given stock's price
                            JSONObject mainObject = response.getJSONObject("Global Quote");
                            double price = Double.parseDouble(mainObject.getString("05. price"));
                            double earning;

                            // update the last price column of the stock table in database
                            stock.setLastPrice(price);
                            StockDatabase db = new StockDatabase(context);
                            db.updateStock(stock);
                            db.close();

                            // Update price and earning textViews
                            earning = (stock.getLastPrice() - stock.getWorth()) * stock.getQuantity();
                            priceTextView.setText(String.format("$%.2f", price));
                            earningTextView.setText(String.format("$%.2f", earning));

                            // Refresh dashboard in the MainFragment to update the total amount of actie stock.
                            MainFragment.refreshDashboard();
                            //success = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON", "Request Error" + e.getLocalizedMessage());
                            Log.d("JSON", "Requested URL" + url);
                            //success = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", "Request Error" + error.getLocalizedMessage());
                        //success = false;
                    }
                });
    }
}
