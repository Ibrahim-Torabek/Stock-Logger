package ibrahim.example.stocklogger.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private static Double investment = 0.0;

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

        // add a floating button to add a new stock.
        FloatingActionButton addFab = view.findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the AddStockFragment
                Navigation.findNavController(view).navigate(R.id.addStockFragment);
            }
        });

        // Get investing amount from preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        investment = Double.parseDouble(sharedPreferences.getString("investing_amount","0.0"));
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

    /**
     * Refresh the recycler view when back to the fragment
     */
    @Override
    public void onResume() {
        StockDatabase db = new StockDatabase(getContext());

        ArrayList<Stock> stocks = db.getAllStocks();

        RecyclerView stockRecyclerView = view.findViewById(R.id.stocksRecyclerView);

        StocksRecyclerAdapter adapter = new StocksRecyclerAdapter(stocks, getContext());
        stockRecyclerView.setAdapter(adapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        investment = Double.parseDouble(sharedPreferences.getString("investing_amount","0.0"));
        refreshDashboard();
        db.close();

        super.onResume();
    }

    /**
     * Hide soft keyboard when back to the main fragment
     * @param savedInstanceState saved Instance
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /**
     * Re calculate and refresh all dash board data
     */
    public static void refreshDashboard(){
        int totalQuantity = 0;
        double totalActiveEarning = 0;
        double totalSoldEarning = 0;
        double totalEarning;
        TextView totalStocksTextView = view.findViewById(R.id.totalStocksTextView);

        // Use PriceTextView to show dependent colors
        PriceTextView activeStocksTextView = view.findViewById(R.id.activeStocksTextView);
        PriceTextView soldStocksTextView = view.findViewById(R.id.soldStocksTextView);
        PriceTextView totalEarningsTextView = view.findViewById(R.id.totalEarningsTextView);
        TextView balanceTitleTextView = view.findViewById(R.id.balanceTitleTextView);
        TextView balanceTextView = view.findViewById(R.id.balanceTextView);


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
        balanceTextView.setText(String.valueOf(investment + totalEarning));

        if(investment != 0){
            balanceTextView.setVisibility(View.VISIBLE);
            balanceTitleTextView.setVisibility(View.VISIBLE);
        } else {
            balanceTitleTextView.setVisibility(View.GONE);
            balanceTextView.setVisibility(View.GONE);
        }

        db.close();

    }

}