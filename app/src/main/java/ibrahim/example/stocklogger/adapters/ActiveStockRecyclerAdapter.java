package ibrahim.example.stocklogger.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.ActiveStock;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * <h3>This class is a recycler view custom adapter class</h3>
 * This class extends RecyclerView.Adapter class\n
 * It will display all transitions of an active stock.
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 02/04/21
 * @see RecyclerView.Adapter
 * @see StocksRecyclerAdapter
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

        holder.deleteActiveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle(stock.getSymbol())
                        .setMessage("Do you want to delete " + stock.getBoughtDate() + " record of " + stock.getSymbol() + "?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StockDatabase db = new StockDatabase(context);
                                db.deleteActiveStock(stock.getId());
                                db.close();
                                activeStocks.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return activeStocks.size();
    }

    class ActiveStockRecyclerHolder extends RecyclerView.ViewHolder{

        protected TextView activeDateTextView;
        protected TextView activePriceTextView;
        protected TextView activeQuantityTextView;
        protected ImageView deleteActiveImageView;

        public ActiveStockRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            activeDateTextView = itemView.findViewById(R.id.activeDateTextView);
            activePriceTextView = itemView.findViewById(R.id.activePriceTextView);
            activeQuantityTextView = itemView.findViewById(R.id.activeQuantityTextView);
            deleteActiveImageView = itemView.findViewById(R.id.deleteActiveImageView);
        }
    }
}
