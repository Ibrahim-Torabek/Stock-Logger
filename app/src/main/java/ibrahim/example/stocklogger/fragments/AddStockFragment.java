package ibrahim.example.stocklogger.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ibrahim.example.stocklogger.Adapters.SymbolAutoCompleteAdapter;
import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.api.StockApiRequest;
import ibrahim.example.stocklogger.api.StockAutoCompleteRequest;
import ibrahim.example.stocklogger.api.StockSingleton;
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

    private String[] symbols;

    // Declare private to use the stock object in different methods.
    private Stock stock;

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


        AutoCompleteTextView symbolEdit = view.findViewById(R.id.symbolEdit);
        EditText companyNameEdit = view.findViewById(R.id.companyNameEdit);
        EditText priceEdit = view.findViewById(R.id.priceEdit);
        EditText quantityEdit = view.findViewById(R.id.quantityEdit);
        Switch isUSD = view.findViewById(R.id.isUSD);
        Button addButton = view.findViewById(R.id.addButton);

        symbolEdit.setAdapter(new SymbolAutoCompleteAdapter(getContext(), android.R.layout.simple_list_item_1));

        if( getArguments() != null){
            stock = getArguments().getParcelable("STOCK");

            symbolEdit.setEnabled(false);
            symbolEdit.setText(stock.getSymbol());

            companyNameEdit.setEnabled(false);
            companyNameEdit.setText(stock.getCompanyName());

            isUSD.setEnabled(false);
            isUSD.setChecked(stock.isUSD());
        }

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
                        double worth = (activeStock.getPrice() * activeStock.getQuantity()+2*6.95) / activeStock.getQuantity();
                        Stock stock = new Stock(
                                symbolEdit.getText().toString(),
                                companyNameEdit.getText().toString(),
                                activeStock.getPrice(),
                                worth,
                                Integer.parseInt(quantityEdit.getText().toString())
                        );
                        if(isUSD.isChecked())
                            stock.setUSD(true);
                        stockId = db.addStock(stock);
                    } else {
                        double worth = (activeStock.getPrice() * activeStock.getQuantity()+ 6.95) / activeStock.getQuantity();
                        Stock stock = db.getStock(stockId);
                        int totalQuantity = stock.getQuantity() + activeStock.getQuantity();
                        double totalWorth = (stock.getWorth() * stock.getQuantity() + worth * activeStock.getQuantity()) / totalQuantity;

                        stock.setWorth(totalWorth);
                        stock.setQuantity(totalQuantity);
                        db.updateStock(stock);
                    }

                    db.addStockActive(stockId, activeStockId);

                    Snackbar.make(view, "Successfully Added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    db.close();
                    symbolEdit.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    companyNameEdit.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    priceEdit.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    quantityEdit.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Navigation.findNavController(view).popBackStack();
                }



            }
        });


        return view;
    }
}