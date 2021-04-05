package ibrahim.example.stocklogger.Adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
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
import ibrahim.example.stocklogger.api.StockAutoCompleteRequest;
import ibrahim.example.stocklogger.api.StockSingleton;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 04/04/21
 */
public class SymbolAutoCompleteAdapter extends ArrayAdapter implements Filterable {
    ArrayList<String> symbols;// = new ArrayList<>();

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
        return symbols.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence != null){
                    String requestUrl = StockAutoCompleteRequest.URL +
                            "function=" + StockAutoCompleteRequest.SYMBOL_SEARCH +
                            "&keywords=" + charSequence.toString().toUpperCase() +
                            "&apikey=" + StockApiRequest.APIKEY;

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

                                        for(int i=0; i < jsonArray.length(); i++){
                                            JSONObject stock = jsonArray.getJSONObject(i);
                                            symbols.add(stock.getString("1. symbol"));
                                        }
                                        notifyDataSetChanged();


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
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
