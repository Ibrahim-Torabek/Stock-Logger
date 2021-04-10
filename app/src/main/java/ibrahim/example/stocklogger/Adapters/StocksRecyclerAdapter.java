package ibrahim.example.stocklogger.Adapters;

/**
 * TODO:
 *  - Complete JavaDoc
 */

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.api.StockApiRequest;
import ibrahim.example.stocklogger.api.StockSingleton;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.ActiveStock;
import ibrahim.example.stocklogger.pojos.Stock;
import ibrahim.example.stocklogger.views.PriceTextView;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;


/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-01-27
 */
public class StocksRecyclerAdapter extends RecyclerView.Adapter<StocksRecyclerAdapter.StockCustomViewHolder> {
    private ArrayList<Stock> stocks;
    private Context context;

    public StocksRecyclerAdapter(ArrayList<Stock> stocks, Context context) {
        this.stocks = stocks;
        this.context = context;
    }

    @NonNull
    @Override
    public StockCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_active_stock,parent,false);

        return new StockCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockCustomViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        double lastPrice = stock.getLastPrice();
        double worth = stock.getWorth();
        int quantity = stock.getQuantity();
        double earning = (lastPrice - worth) * quantity;


        // request url
        String requestUrl = StockApiRequest.URL +
                "function=" + StockApiRequest.GLOBAL_QUOTE +
                "&symbol=" + stock.getSymbol() +
                "&apikey=" + StockApiRequest.APIKEY;

        // pass url, stock and textview as parameters
        // pass stock to update the stock database when it successfully get the price.
        // pass priceTextView to update the view when it successfully get the price.
        StockApiRequest request = new StockApiRequest(requestUrl, stock, holder.recentPriceTextView, holder.earningTextView, context);
        StockSingleton.getInstance(context).getRequestQueue().add(request);


        holder.symbolTextView.setText(stock.getSymbol());
        holder.currencyTextView.setText(stock.isUSD() ? "USD" : "CAD");
        holder.recentPriceTextView.setText("$" + String.format("%.2f", lastPrice));
        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.earningTextView.setText(String.format("$%.2f", earning));

        if(earning > 0){
            holder.earningTextView.setTextColor(Color.BLUE);
        }

        holder.addStockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("STOCK",stock);
                Navigation.findNavController(view).navigate(R.id.addStockFragment, bundle);
            }
        });

        holder.removeStockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = stock.getSymbol();
                new AlertDialog.Builder(context,THEME_DEVICE_DEFAULT_DARK)
//                new MaterialAlertDialogBuilder(context)
                        .setTitle(stock.getSymbol())
                        .setMessage("Did you sell all stocks of " + symbol + "?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("STOCK",stock);
                                Navigation.findNavController(view).navigate(R.id.sellStockFragment, bundle);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        ArrayList<ActiveStock> activeStocks = new ArrayList<>();
        StockDatabase db = new StockDatabase(context);
        activeStocks = db.getAllActiveStocks(stock.getId());
        db.close();

        RecyclerView activeStockRecyclerView = holder.itemView.findViewById(R.id.activeStockRecyclerView);
        activeStockRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        activeStockRecyclerView.setAdapter(new ActiveStockRecyclerAdapter(activeStocks, context));


    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }


    class StockCustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView symbolTextView;
        protected TextView currencyTextView;
        protected PriceTextView earningTextView;
        protected TextView recentPriceTextView;
        protected TextView quantityTextView;
        protected ImageView addStockImageView;
        protected ImageView removeStockImageView;

        protected LinearLayout activeStockLinearLayout;


        public StockCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            symbolTextView = itemView.findViewById(R.id.soldSymbolTextView);
            currencyTextView = itemView.findViewById(R.id.currencyTextView);
            earningTextView = itemView.findViewById(R.id.soldEarningTextView);
            recentPriceTextView = itemView.findViewById(R.id.soldPriceTextView);
            quantityTextView = itemView.findViewById(R.id.soldDateTextView);
            activeStockLinearLayout = itemView.findViewById(R.id.activeStockLinearLayout);
            addStockImageView = itemView.findViewById(R.id.addStockImageView);
            removeStockImageView = itemView.findViewById(R.id.removeStockImageView);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            float textSize = sharedPreferences.getInt("text_size",30);

            symbolTextView.setTextSize(textSize);
            currencyTextView.setTextSize(textSize);
            earningTextView.setTextSize(textSize);
            recentPriceTextView.setTextSize(textSize);
            quantityTextView.setTextSize(textSize);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            activeStockLinearLayout.setVisibility(
                    activeStockLinearLayout.getVisibility() == View.GONE ?
                            View.VISIBLE : View.GONE
            );
        }
    }

}
