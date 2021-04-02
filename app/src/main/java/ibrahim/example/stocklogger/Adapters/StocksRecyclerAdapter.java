package ibrahim.example.stocklogger.Adapters;

/**
 * TODO:
 *  - Complete JavaDoc
 */

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.api.StockApi;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.ActiveStock;
import ibrahim.example.stocklogger.pojos.Stock;

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

        StockApi api = new StockApi(stock.getSymbol(), context);

        holder.symbolTextView.setText(stock.getSymbol());
        holder.currencyTextView.setText(stock.isUSD() ? "USD" : "CAD");
        holder.recentPriceTextView.setText("$" + String.format("%.2f", api.getPrice()));
//        holder.recentPriceTextView.setText("$" + String.valueOf(lastPrice));
        holder.erningTextView.setText("$" + String.format("%.2f", earning));
        holder.quantityTextView.setText(String.valueOf(quantity));

        holder.addStockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("STOCK",stock);
                Navigation.findNavController(view).navigate(R.id.addStockFragment, bundle);
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
        protected TextView erningTextView;
        protected TextView recentPriceTextView;
        protected TextView quantityTextView;
        protected ImageView addStockImageView;

        protected LinearLayout activeStockLinearLayout;


        public StockCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            currencyTextView = itemView.findViewById(R.id.currencyTextView);
            erningTextView = itemView.findViewById(R.id.erningTextView);
            recentPriceTextView = itemView.findViewById(R.id.recentPriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            activeStockLinearLayout = itemView.findViewById(R.id.activeStockLinearLayout);
            addStockImageView = itemView.findViewById(R.id.addStockImageView);

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
