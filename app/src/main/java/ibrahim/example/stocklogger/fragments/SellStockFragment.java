package ibrahim.example.stocklogger.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import ibrahim.example.stocklogger.R;
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
        EditText soldPriceEditText = view.findViewById(R.id.soldPriceEditText);
        EditText soldQuantityEditText = view.findViewById(R.id.soldQuantityEditText);
        calendarTextDate = view.findViewById(R.id.calendarTextDate);

        Calendar c = Calendar.getInstance();

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

        }

        return view;
    }


}