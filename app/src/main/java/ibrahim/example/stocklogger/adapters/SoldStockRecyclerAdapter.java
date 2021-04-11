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
import ibrahim.example.stocklogger.fragments.SoldStockFragment;
import ibrahim.example.stocklogger.pojos.SoldStock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>This class is a recycler view adapter for SoldStockFragment</h3>
 * It will display all sold stocks and their detailed information in card view.
 *
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 04/04/21
 * @see RecyclerView.Adapter
 * @see SoldStockFragment
 */
public class SoldStockRecyclerAdapter extends RecyclerView.Adapter<SoldStockRecyclerAdapter.SoldStockSuctomViewHolder> {
    ArrayList<SoldStock> soldStocks;
    Context context;

    public SoldStockRecyclerAdapter(ArrayList<SoldStock> soldStocks, Context context) {
        this.soldStocks = soldStocks;
        this.context = context;
    }

    @NonNull
    @Override
    public SoldStockSuctomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sold_stock, parent, false);

        return new SoldStockRecyclerAdapter.SoldStockSuctomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoldStockSuctomViewHolder holder, int position) {
        SoldStock soldStock = soldStocks.get(position);

        holder.soldSymbolTextView.setText(soldStock.getSymbol());
        holder.soldCompanyNameTextView.setText(soldStock.getCompanyName());
        holder.soldPriceTextView.setText(String.format("$%.2f",soldStock.getSoldPrice()));
        holder.soldEarningTextView.setText(String.format("$%.2f",soldStock.getEarning()));
        holder.soldDateTextView.setText(soldStock.getSoldDate());
    }

    @Override
    public int getItemCount() {
        return soldStocks.size();
    }

    class SoldStockSuctomViewHolder extends RecyclerView.ViewHolder{

        protected TextView soldSymbolTextView;
        protected TextView soldCompanyNameTextView;
        protected TextView soldPriceTextView;
        protected TextView soldEarningTextView;
        protected TextView soldDateTextView;

        public SoldStockSuctomViewHolder(@NonNull View itemView) {
            super(itemView);

            soldSymbolTextView = itemView.findViewById(R.id.symbolTextView);
            soldCompanyNameTextView = itemView.findViewById(R.id.soldCompanyNameTextView);
            soldPriceTextView = itemView.findViewById(R.id.priceTextView);
            soldEarningTextView = itemView.findViewById(R.id.earningTextView);
            soldDateTextView = itemView.findViewById(R.id.quantityTextView);

        }
    }
}
