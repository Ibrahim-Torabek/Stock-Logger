package ibrahim.example.stocklogger.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.databases.StockDatabase;
import ibrahim.example.stocklogger.pojos.SoldStock;
import ibrahim.example.stocklogger.pojos.Stock;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellStockFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static TextView calendarTextDate;

    EditText soldPriceEditText;

    public SellStockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellStockFragment newInstance(String param1, String param2) {
        SellStockFragment fragment = new SellStockFragment();
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
        View view = inflater.inflate(R.layout.fragment_sell_stock, container, false);

        TextView soldNameTextView = view.findViewById(R.id.soldNameTextView);
        soldPriceEditText = view.findViewById(R.id.soldPriceEditText);
        EditText soldQuantityEditText = view.findViewById(R.id.soldQuantityEditText);
        calendarTextDate = view.findViewById(R.id.calendarTextDate);
        Button soldButton = view.findViewById(R.id.soldButton);

        Calendar c = Calendar.getInstance();

        soldPriceEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.d("TEST_RESPONSE", "Action ID = " + i + "KeyEvent = " + keyEvent);
                return false;
            }
        });


        // Set current date
        calendarTextDate.setText(DateFormat.getDateInstance().format(c.getTime()));
        calendarTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });



        if(getArguments() != null) {
            Stock stock = getArguments().getParcelable("STOCK");

            soldNameTextView.setText(stock.getCompanyName());
            soldQuantityEditText.setText(String.valueOf(stock.getQuantity()));


            soldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean error = false;

                    if(soldPriceEditText.getText().toString().equals("")){
                        error = true;
                        YoYo.with(Techniques.Shake)
                                .duration(700)
                                .playOn(soldPriceEditText);
                        Snackbar.make(view, "Sold price can't be blank", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    if(!error){

                        double soldPrice = Double.parseDouble(soldPriceEditText.getText().toString());
                        double worth = stock.getWorth();
                        double rating = 1;

                        if(stock.isUSD()){
                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                            rating = Double.parseDouble(sp.getString("isUSD","1.26"));
                        }

                        int quantity = Integer.parseInt(soldQuantityEditText.getText().toString());

                        double earned = (soldPrice - worth) * quantity * rating;

                        StockDatabase db = new StockDatabase(getContext());

                        db.addSoldStock(
                                new SoldStock(
                                        stock.getSymbol(),
                                        stock.getCompanyName(),
                                        soldPrice,
                                        earned,
                                        calendarTextDate.getText().toString()
                                )
                        );

                        db.deleteStock(stock.getId());

                        db.close();

                        Navigation.findNavController(view).popBackStack();
                    }
                }
            });
        }




        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d("FRAGMENT","Destroyed");
        soldPriceEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        super.onDestroyView();
    }
}