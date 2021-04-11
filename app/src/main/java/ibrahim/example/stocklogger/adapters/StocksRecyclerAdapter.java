package ibrahim.example.stocklogger.adapters;

/**
 * TODO:
 *  - Complete JavaDoc
 */

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
import ibrahim.example.stocklogger.fragments.MainFragment;
import ibrahim.example.stocklogger.pojos.ActiveStock;
import ibrahim.example.stocklogger.pojos.Stock;
import ibrahim.example.stocklogger.views.PriceTextView;


/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>This is a recycler view custom adapter class for main fragment</h3>
 *
 * It will display on hold stocks in card view and each card view also include
 * detailed transactions of each stock with ActiveStockRecyclerView.
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 2021-01-27
 * @see RecyclerView.Adapter
 * @see MainFragment
 * @see ActiveStockRecyclerAdapter
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
        holder.companyNameTextView.setText(stock.getCompanyName());
        // determine which flag to display The US or Canada based on isUSD property
        holder.flagImageView.setImageResource(stock.isUSD() ? R.drawable.ic_united_states : R.drawable.ic_canada);
        holder.recentPriceTextView.setText("$" + String.format("%.2f", lastPrice));
        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.earningTextView.setText(String.format("$%.2f", earning));
        holder.worthTextView.setText(String.format("$%.2f", worth));

        // Delete a stock card view
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle(stock.getSymbol())
                        .setMessage("Do you want to delete all transaction records of " + stock.getSymbol() + "?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StockDatabase db = new StockDatabase(context);
                                db.deleteStock(stock.getId());
                                db.close();
                                stocks.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        // Increase a stock holding number
        holder.addStockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set bundle
                Bundle bundle = new Bundle();
                bundle.putParcelable("STOCK",stock);
                Navigation.findNavController(view).navigate(R.id.addStockFragment, bundle);
            }
        });

        // This will mark a stock as sold.
        // In present, it only mark whole stock as sold.
        holder.removeStockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbol = stock.getSymbol();
//                new AlertDialog.Builder(context,THEME_DEVICE_DEFAULT_DARK)
                new MaterialAlertDialogBuilder(context)
                        .setTitle(stock.getSymbol())
                        .setMessage("Did you sell all stocks of " + symbol + "?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // set bundle
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("STOCK",stock);

                                //Navigate to sellStock fragment with bundle.
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

        // Set transactions recycler view to display all transactions from active stock table
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
        protected PriceTextView earningTextView;
        protected TextView recentPriceTextView;
        protected TextView quantityTextView;
        protected ImageView addStockImageView;
        protected ImageView removeStockImageView;
        protected ImageView deleteImageView;
        protected ImageView flagImageView;
        protected TextView worthTextView;
        protected TextView companyNameTextView;

        protected LinearLayout activeStockLinearLayout;


        public StockCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            earningTextView = itemView.findViewById(R.id.earningTextView);
            recentPriceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            activeStockLinearLayout = itemView.findViewById(R.id.activeStockLinearLayout);
            addStockImageView = itemView.findViewById(R.id.addStockImageView);
            removeStockImageView = itemView.findViewById(R.id.removeStockImageView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            flagImageView = itemView.findViewById(R.id.flagImageView);
            worthTextView = itemView.findViewById(R.id.worthTextView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);

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
