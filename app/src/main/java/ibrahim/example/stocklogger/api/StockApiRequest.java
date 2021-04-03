package ibrahim.example.stocklogger.api;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 */
public class StockApiRequest extends JsonObjectRequest {
    public static final String URL = "https://www.alphavantage.co/query?";
    public static final String APIKEY = "WMHZQM8S5LZ9EB4W";
    public static final String GLOBAL_QUOTE = "GLOBAL_QUOTE";

    private boolean success = false;

    public StockApiRequest(String url, Stock stock, TextView priceTextxView,  Context context) {
        super(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mainObject = response.getJSONObject("Global Quote");
                            double price = Double.parseDouble(mainObject.getString("05. price"));
                            priceTextxView.setText("$" + String.format("%.2f", price));
                            stock.setLastPrice(price);
                            StockDatabase db = new StockDatabase(context);
                            db.updateStock(stock);
                            db.close();
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
