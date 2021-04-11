package ibrahim.example.stocklogger.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.pojos.CreditIcon;

/**
 * <h1>App for Stock Logger</h1>
 * <h2>Android Final Project of MAD405 Course</h2>
 *
 * @author Ibrahim (Wusiman Yibuulayin)
 * @version 1.0
 * @since 10/04/21
 */
public class CreditIconAdapter extends RecyclerView.Adapter<CreditIconAdapter.CreditIconRecyclerHolder> {
    ArrayList<CreditIcon> creditIcons;
    Context context;

    public CreditIconAdapter(ArrayList<CreditIcon> creditIcons, Context context) {
        this.creditIcons = creditIcons;
        this.context = context;
    }

    @NonNull
    @Override
    public CreditIconRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_credit_icon, parent, false);

        return new CreditIconRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditIconRecyclerHolder holder, int position) {
        CreditIcon creditIcon = creditIcons.get(position);

        holder.iconCreditImageView.setImageResource(creditIcon.getIconResource());
        holder.iconCreditTextView.setText(creditIcon.toString());
        holder.iconCreditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(creditIcon.getIconURL()));
                //i.setData();
                context.startActivity(i);
                if(i.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return creditIcons.size();
    }

    class CreditIconRecyclerHolder extends RecyclerView.ViewHolder{
        ImageView iconCreditImageView;
        TextView iconCreditTextView;

        public CreditIconRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            iconCreditImageView = itemView.findViewById(R.id.iconCreditImageView);
            iconCreditTextView = itemView.findViewById(R.id.iconCreditTextView);
        }
    }
}
