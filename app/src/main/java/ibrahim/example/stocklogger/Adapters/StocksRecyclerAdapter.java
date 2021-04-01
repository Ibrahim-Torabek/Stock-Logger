package ibrahim.example.stocklogger.Adapters;

/**
 * TODO:
 *  - Complete JavaDoc
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Lab1, Lab2, Lab3, Lab4 of MAD405 Course</h2>
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

        holder.symbolTextView.setText(stock.getSymbol());
        holder.currencyTextView.setText(stock.isUSD() ? "USD" : "CAD");
        holder.recentPriceTextView.setText("$" + String.valueOf(lastPrice));
        holder.erningTextView.setText("$" + String.format("%.2f", earning));
//        holder.erningTextView.setText("Hello");
        holder.quantityTextView.setText(String.valueOf(quantity));

    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    class StockCustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView symbolTextView;
        protected TextView currencyTextView;
        protected TextView erningTextView;
        protected TextView recentPriceTextView;
        protected TextView quantityTextView;


        public StockCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            currencyTextView = itemView.findViewById(R.id.currencyTextView);
            erningTextView = itemView.findViewById(R.id.erningTextView);
            recentPriceTextView = itemView.findViewById(R.id.recentPriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);

        }
    }

}
