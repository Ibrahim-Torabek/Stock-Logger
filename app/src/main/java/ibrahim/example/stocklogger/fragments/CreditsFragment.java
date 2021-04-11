package ibrahim.example.stocklogger.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.adapters.ContactViewPagerAdapter;
import ibrahim.example.stocklogger.views.DepthPageTransformer;
import ibrahim.example.stocklogger.views.ZoomOutPageTransformer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreditsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditsFragment newInstance(String param1, String param2) {
        CreditsFragment fragment = new CreditsFragment();
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
        View view = inflater.inflate(R.layout.fragment_credits, container, false);


        ViewPager2 creditViewPager = view.findViewById(R.id.creditViewPager);
        creditViewPager.setAdapter(new ContactViewPagerAdapter(getActivity(), ContactViewPagerAdapter.PAGE_CREDITS));
        //contactViewPager.setPageTransformer(new ContactFragment.DepthPageTransformer());
        creditViewPager.setPageTransformer(new ZoomOutPageTransformer());

        TabLayout tabLayout = view.findViewById(R.id.creditTabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout,
                creditViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:
                                tab.setText("Icons");

                                break;
                            case 1:
                                tab.setText("Pictures");

                                break;
                            case 2:
                                tab.setText("other");

                                break;
                        }
                    }
                }
        );

        tabLayoutMediator.attach();

        return view;
    }
}