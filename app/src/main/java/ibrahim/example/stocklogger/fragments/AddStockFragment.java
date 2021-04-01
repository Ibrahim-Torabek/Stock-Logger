package ibrahim.example.stocklogger.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.ActiveStock;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStockFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddStockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStockFragment newInstance(String param1, String param2) {
        AddStockFragment fragment = new AddStockFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_stock, container, false);


        EditText symbolEdit = view.findViewById(R.id.symbolEdit);
        EditText companyNameEdit = view.findViewById(R.id.companyNameEdit);
        EditText priceEdit = view.findViewById(R.id.priceEdit);
        EditText quantityEdit = view.findViewById(R.id.quantityEdit);
        Switch isUSD = view.findViewById(R.id.isUSD);
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if an error occurred.
                boolean error = false;

                if(symbolEdit.getText().toString().equals("")){
                    error = true;
                    Snackbar.make(view, "Symbol Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(companyNameEdit.getText().toString().equals("")){
                    error = true;
                    Snackbar.make(view, "Company Name Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(priceEdit.getText().toString().equals("")){
                    error = true;
                    Snackbar.make(view, "Price Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if(quantityEdit.getText().toString().equals("")){
                    error = true;
                    Snackbar.make(view, "Quantity Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                // If no any errors
                if(!error){
                    StockDatabase db = new StockDatabase(getContext());
                    ActiveStock activeStock = new ActiveStock(
                            symbolEdit.getText().toString(),
                            companyNameEdit.getText().toString(),
                            Double.parseDouble(priceEdit.getText().toString()),
                            Integer.parseInt(quantityEdit.getText().toString()),
                            "2021-03-31"
                    );
                    int activeStockId = db.addActiveStock(activeStock);
                    int stockId = db.getStockId(symbolEdit.getText().toString());
                    if(stockId == -1){
                        Stock stock = new Stock(
                                symbolEdit.getText().toString(),
                                companyNameEdit.getText().toString(),
                                0.0,
                                0.0,
                                Integer.parseInt(quantityEdit.getText().toString())
                        );
                        stockId = db.addStock(stock);
                    }

                    db.addStockActive(activeStockId, stockId);

                    Snackbar.make(view, "Successfully Added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    db.close();
                    Navigation.findNavController(view).popBackStack();
                }

            }
        });


        return view;
    }
}