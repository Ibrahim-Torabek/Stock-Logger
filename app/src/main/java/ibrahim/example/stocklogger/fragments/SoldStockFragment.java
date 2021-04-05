package ibrahim.example.stocklogger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ibrahim.example.stocklogger.Adapters.SoldStockRecyclerAdapter;
import ibrahim.example.stocklogger.Adapters.StocksRecyclerAdapter;
import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.SoldStock;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoldStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoldStockFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SoldStockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoldStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoldStockFragment newInstance(String param1, String param2) {
        SoldStockFragment fragment = new SoldStockFragment();
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
        View view = inflater.inflate(R.layout.fragment_sold_stock, container, false);

        StockDatabase db = new StockDatabase(getContext());

        ArrayList<SoldStock> stocks = db.getAllSoldStocks();

        RecyclerView soldStockRecyclerView = view.findViewById(R.id.soldStockRecyclerView);

        SoldStockRecyclerAdapter adapter = new SoldStockRecyclerAdapter(stocks, getContext());
        soldStockRecyclerView.setAdapter(adapter);
        soldStockRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db.close();


        return view;
    }
}