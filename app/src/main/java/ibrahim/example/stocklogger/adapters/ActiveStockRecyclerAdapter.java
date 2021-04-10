package ibrahim.example.stocklogger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.pojos.ActiveStock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 */
public class ActiveStockRecyclerAdapter extends RecyclerView.Adapter<ActiveStockRecyclerAdapter.ActiveStockRecyclerHolder> {
    private ArrayList<ActiveStock> activeStocks;
    private Context context;

    public ActiveStockRecyclerAdapter(ArrayList<ActiveStock> activeStocks, Context context) {
        this.activeStocks = activeStocks;
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveStockRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_active_stock, parent, false);

        return new ActiveStockRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveStockRecyclerHolder holder, int position) {

        ActiveStock stock = activeStocks.get(position);

        holder.activeDateTextView.setText(stock.getBoughtDate());
        holder.activePriceTextView.setText(String.format("$%.2f", stock.getPrice()));
        holder.activeQuantityTextView.setText(String.valueOf(stock.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return activeStocks.size();
    }

    class ActiveStockRecyclerHolder extends RecyclerView.ViewHolder{

        protected TextView activeDateTextView;
        protected TextView activePriceTextView;
        protected TextView activeQuantityTextView;

        public ActiveStockRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            activeDateTextView = itemView.findViewById(R.id.activeDateTextView);
            activePriceTextView = itemView.findViewById(R.id.activePriceTextView);
            activeQuantityTextView = itemView.findViewById(R.id.activeQuantityTextView);

        }
    }
}
