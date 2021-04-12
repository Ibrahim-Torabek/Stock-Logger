package ibrahim.example.stocklogger.fragments.credits;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.adapters.ActiveStockRecyclerAdapter;
import ibrahim.example.stocklogger.adapters.CreditIconAdapter;
import ibrahim.example.stocklogger.pojos.CreditIcon;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditIconFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditIconFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreditIconFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditIconFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditIconFragment newInstance(String param1, String param2) {
        CreditIconFragment fragment = new CreditIconFragment();
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
        View view = inflater.inflate(R.layout.fragment_credit_icon, container, false);

        // Create Recycler View's array list to display icon and source name, and redirect to the url
        ArrayList<CreditIcon> creditIcons = new ArrayList<>();
        creditIcons.add(new CreditIcon(R.drawable.ic_canada,"Freepik","https://www.flaticon.com/free-icon/canada_197430"));
        creditIcons.add(new CreditIcon(R.drawable.ic_united_states,"Freepik","https://www.flaticon.com/free-icon/united-states-of-america_197484"));
        creditIcons.add(new CreditIcon(R.drawable.ic_information,"Freepik","https://www.flaticon.com/free-icon/information_4293726"));
        creditIcons.add(new CreditIcon(R.drawable.ic_contact,"Smashicons","https://www.flaticon.com/free-icon/contact_1034153"));
        creditIcons.add(new CreditIcon(R.drawable.ic_credits,"Eucalyp","https://www.flaticon.com/free-icon/descriptor_2245222"));

        RecyclerView creditIconRecyclerView = view.findViewById(R.id.creditIconRecyclerView);
        creditIconRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        creditIconRecyclerView.setAdapter(new CreditIconAdapter(creditIcons, getContext()));
        return view;
    }
}