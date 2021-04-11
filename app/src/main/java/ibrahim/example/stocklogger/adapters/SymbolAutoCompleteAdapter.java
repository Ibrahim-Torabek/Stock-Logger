package ibrahim.example.stocklogger.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ibrahim.example.stocklogger.api.StockApiRequest;
import ibrahim.example.stocklogger.api.StockSingleton;
import ibrahim.example.stocklogger.fragments.AddStockFragment;
import ibrahim.example.stocklogger.pojos.AutoCompletedStock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>This class is an Array adapter for auto complete the stock symbol</h3>
 * It will filter relative symbols from entered characters in symbol edit box.
 * It extends ArrayAdapter and implements Filterable protocol.
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 04/04/21
 * @see ArrayAdapter
 * @see Filterable
 * @see AutoCompleteTextView
 */
public class SymbolAutoCompleteAdapter extends ArrayAdapter implements Filterable {
    ArrayList<String> symbols;// = new ArrayList<>();
    public ArrayList<String> companyNames;

    int resource;
    Context context;

    public SymbolAutoCompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return symbols.size();
    }

    @Override
    public String getItem(int position) {
        // get selected symbols' position
        return symbols.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {

        // Perform filtering
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                // Get filter results.
                FilterResults results = new FilterResults();
                if(charSequence != null){
                    // Generate alphavantage's auto complete api url.
                    String requestUrl = StockApiRequest.URL +
                            "function=" + StockApiRequest.SYMBOL_SEARCH +
                            "&keywords=" + charSequence.toString().toUpperCase() +
                            "&apikey=" + StockApiRequest.APIKEY;

                    // Request an api JSONObject
                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.GET,
                            requestUrl,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("bestMatches");

                                        symbols = new ArrayList<>();
                                        companyNames = new ArrayList<>();
                                        ArrayList<AutoCompletedStock> companies = new ArrayList<>();

                                        for(int i=0; i < jsonArray.length(); i++){
                                            JSONObject stock = jsonArray.getJSONObject(i);
                                            String currency = stock.getString("8. currency");
                                            symbols.add(stock.getString("1. symbol"));
                                            companies.add(new AutoCompletedStock(
                                                    stock.getString("1. symbol"),
                                                    stock.getString("2. name"),
                                                    currency.equals("USD") ? true : false));
                                        }
                                        notifyDataSetChanged();
                                        AddStockFragment.autoCompletedStocks = companies;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("JSON_SYMBOL", "Request Error" + e.getLocalizedMessage());
                                        Log.d("JSON_SYMBOL", "Requested URL" + requestUrl);
                                        //success = false;
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    );
                    StockSingleton.getInstance(getContext()).getRequestQueue().add(request);

                    results.values = symbols;
                    results.count = symbols.size();

                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults != null && filterResults.count > 0){
                    // I moved the following code to onResponse method of JSONRequest.
                    //notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }


}
