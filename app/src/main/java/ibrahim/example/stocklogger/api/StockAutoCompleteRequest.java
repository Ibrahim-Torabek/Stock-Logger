package ibrahim.example.stocklogger.api;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.fragments.MainFragment;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 */
public class StockAutoCompleteRequest extends JsonObjectRequest {
    public static final String URL = "https://www.alphavantage.co/query?";
    public static final String APIKEY = "WMHZQM8S5LZ9EB4W";
    public static final String GLOBAL_QUOTE = "GLOBAL_QUOTE";
    public static final String SYMBOL_SEARCH = "SYMBOL_SEARCH";

    private boolean success = false;

    public StockAutoCompleteRequest(String url,AutoCompleteTextView textView, Context context) {
        super(
                Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONObject mainObject = response.getJSONObject("bestMatches");

                            JSONArray jsonArray = response.getJSONArray("bestMatches");
                            ArrayList<String> symbols = new ArrayList<>();


                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject stock = jsonArray.getJSONObject(i);
                                symbols.add(stock.getString("1. symbol"));

                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_list_item_1,
                                    symbols);
                            textView.setAdapter(adapter);
                            //success = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON_SYMBOL", "Request Error" + e.getLocalizedMessage());
                            Log.d("JSON_SYMBOL", "Requested URL" + url);
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
