package ibrahim.example.stocklogger.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ibrahim.example.stocklogger.adapters.StocksRecyclerAdapter;
import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.SoldStock;
import ibrahim.example.stocklogger.pojos.Stock;
import ibrahim.example.stocklogger.views.PriceTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static View view;
    private static Context context;


    private TextView totalStocksTextView;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        context = getContext();

        FloatingActionButton addFab = view.findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.addStockFragment);
            }
        });

        refreshDashboard();


        StockDatabase db = new StockDatabase(getContext());

        ArrayList<Stock> stocks = db.getAllStocks();

        RecyclerView stockRecyclerView = view.findViewById(R.id.stocksRecyclerView);

        StocksRecyclerAdapter adapter = new StocksRecyclerAdapter(stocks, getContext());
        stockRecyclerView.setAdapter(adapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db.close();

        return view;
    }

    @Override
    public void onResume() {
        StockDatabase db = new StockDatabase(getContext());

        ArrayList<Stock> stocks = db.getAllStocks();

        RecyclerView stockRecyclerView = view.findViewById(R.id.stocksRecyclerView);

        StocksRecyclerAdapter adapter = new StocksRecyclerAdapter(stocks, getContext());
        stockRecyclerView.setAdapter(adapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db.close();

        super.onResume();
    }

    public static void refreshDashboard(){
        int totalQuantity = 0;
        double totalActiveEarning = 0;
        double totalSoldEarning = 0;
        double totalEarning = 0;
        TextView totalStocksTextView = view.findViewById(R.id.totalStocksTextView);

        // Use PriceTextView to show dependent colors
        PriceTextView activeStocksTextView = view.findViewById(R.id.activeStocksTextView);
        PriceTextView soldStocksTextView = view.findViewById(R.id.soldStocksTextView);
        PriceTextView totalEarningsTextView = view.findViewById(R.id.totalEarningsTextView);


        // Calculate Total stocks in holding
        StockDatabase db = new StockDatabase(context);
        ArrayList<Stock> stocks = db.getAllStocks();

        for (Stock stock :
                stocks) {
            totalQuantity += stock.getQuantity();
            totalActiveEarning += (stock.getLastPrice() - stock.getWorth()) * stock.getQuantity();
        }

        totalStocksTextView.setText(String.valueOf(totalQuantity));
        activeStocksTextView.setText(String.format("$%.02f", totalActiveEarning));

        // Calculate Active Stocks earnings
        ArrayList<SoldStock> soldStocks = db.getAllSoldStocks();

        for (SoldStock stock :
                soldStocks) {
            totalSoldEarning += (stock.getEarning());
        }

        soldStocksTextView.setText(String.format("$%.02f", totalSoldEarning));

        totalEarning = totalActiveEarning + totalSoldEarning;
        totalEarningsTextView.setText(String.format("$%.02f", totalEarning));

        db.close();

    }

}