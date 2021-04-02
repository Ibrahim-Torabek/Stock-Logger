package ibrahim.example.stocklogger.api;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 */
public class StockApi {
    private final String url = "https://www.alphavantage.co/query?";
    private final String apikey = "WMHZQM8S5LZ9EB4W";
    private String function;
    private String symbol;
    private double price;
    private Context context;

    private boolean success = false;

    public StockApi(String symbol, Context context) {
        this.symbol = symbol;
        this.context = context;
        function = "GLOBAL_QUOTE";

        String requestUrl = url +
                "function=" +function +
                "&symbol=" + symbol +
                "&apikey=" + apikey;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mainObject = response.getJSONObject("Global Quote");
                            price = Double.parseDouble(mainObject.getString("05. price"));
                            success = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON", "Request Error" + e.getLocalizedMessage());
                            Log.d("JSON", "Requested URL" + requestUrl);
                            success = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", "Request Error" + error.getLocalizedMessage());
                        success = false;
                    }
                });

        StockSingleton.getInstance(context).getRequestQueue().add(request);

    }

    public void request(Context context){

    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
