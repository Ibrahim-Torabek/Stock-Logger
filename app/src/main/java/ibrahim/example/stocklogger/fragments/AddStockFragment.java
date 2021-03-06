package ibrahim.example.stocklogger.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ibrahim.example.stocklogger.adapters.SymbolAutoCompleteAdapter;
import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.ActiveStock;
import ibrahim.example.stocklogger.pojos.AutoCompletedStock;
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

    public static TextView boughtDateText;

    // Arraylist of auto completed stocks
    public static ArrayList<AutoCompletedStock> autoCompletedStocks = new ArrayList<>();


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
        boughtDateText = view.findViewById(R.id.boughtDateText);
        Switch isUSD = view.findViewById(R.id.isUSD);
        Button addButton = view.findViewById(R.id.addButton);

        //Set autocomplete adapter
        symbolEdit.setAdapter(new SymbolAutoCompleteAdapter(getContext(), android.R.layout.simple_list_item_1));

        symbolEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // returned auto completed array list.
                if(autoCompletedStocks != null){
                    // get company name and stock country and set them into views
                    companyNameEdit.setText(autoCompletedStocks.get(i).getCompanyName());
                    isUSD.setChecked(autoCompletedStocks.get(i).isUSD());
                }
            }
        });

        Calendar c = Calendar.getInstance();
        boughtDateText.setText(DateFormat.getDateInstance().format(c.getTime()));
        boughtDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Date picker dialog when clicked
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        // If the fragment has bundled argument, it means the user clicked the increase stock button.
        // get information from the given argument
        if( getArguments() != null){
            Stock stock = getArguments().getParcelable("STOCK");

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

                // If any edit box is empty, shake the empty edit text and display an snack bar message
                if(symbolEdit.getText().toString().equals("")){
                    error = true;
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(symbolEdit);
                    Snackbar.make(view, "Symbol Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(companyNameEdit.getText().toString().equals("")){
                    error = true;
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(companyNameEdit);
                    Snackbar.make(view, "Company Name Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(priceEdit.getText().toString().equals("")){
                    error = true;
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(priceEdit);
                    Snackbar.make(view, "Price Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if(quantityEdit.getText().toString().equals("")){
                    error = true;
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(quantityEdit);
                    Snackbar.make(view, "Quantity Can't be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                // If no any errors
                if(!error){
                    double tradingFee = 6.95;

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    tradingFee = Double.parseDouble(sharedPreferences.getString("tradingFee", "6.95"));

                    StockDatabase db = new StockDatabase(getContext());
                    ActiveStock activeStock = new ActiveStock(
                            symbolEdit.getText().toString(),
                            companyNameEdit.getText().toString(),
                            Double.parseDouble(priceEdit.getText().toString()),
                            Integer.parseInt(quantityEdit.getText().toString()),
                            boughtDateText.getText().toString()
                    );
                    int activeStockId = db.addActiveStock(activeStock);
                    int stockId = db.getStockId(symbolEdit.getText().toString());


                    // There is no related stock in stock table. it means that the user logged a new stock information.
                    if(stockId == -1){
                        // insert new stock into stock table
                        double worth = (activeStock.getPrice() * activeStock.getQuantity()+2*tradingFee) / activeStock.getQuantity();
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
                        // calculate the worth, earning and quantity, and update the existing stock record in stock table
                        double worth = (activeStock.getPrice() * activeStock.getQuantity()+ tradingFee) / activeStock.getQuantity();
                        Stock stock = db.getStock(stockId);
                        int totalQuantity = stock.getQuantity() + activeStock.getQuantity();
                        double totalWorth = (stock.getWorth() * stock.getQuantity() + worth * activeStock.getQuantity()) / totalQuantity;

                        stock.setWorth(totalWorth);
                        stock.setQuantity(totalQuantity);

                        db.updateStock(stock);
                    }

                    // add stock and active stock relationship into stock_active database.
                    db.addStockActive(stockId, activeStockId);

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