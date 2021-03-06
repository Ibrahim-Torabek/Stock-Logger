package ibrahim.example.stocklogger.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ibrahim.example.stocklogger.adapters.ContactViewPagerAdapter;
import ibrahim.example.stocklogger.R;
import ibrahim.example.stocklogger.views.DepthPageTransformer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static ImageView titleImage;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        titleImage = view.findViewById(R.id.imageHead);

        // use viewpager2 to display three fragments
        ViewPager2 contactViewPager = view.findViewById(R.id.contactViewPager);

        // set the common viewPager2 adapter to display three contact fragments, and provide PAGE_CONNACT to identify the target fragments
        contactViewPager.setAdapter(new ContactViewPagerAdapter(getActivity(), ContactViewPagerAdapter.PAGE_CONTACT));

        // set Transformer
        contactViewPager.setPageTransformer(new DepthPageTransformer());
        contactViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // set animation to the head image
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .playOn(titleImage);

                // set the head image that placed above the viewpager2 when the pager changed
                // three different images for each page.
                switch (position){
                    case 0:
                        ContactFragment.titleImage.setImageResource(R.drawable.contact_call);
                        break;
                    case 1:
                        ContactFragment.titleImage.setImageResource(R.drawable.contact_sms);
                        break;
                    case 2:
                        ContactFragment.titleImage.setImageResource(R.drawable.contact_email);
                }
            }
        });


        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout,
                contactViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        // Display tab title and icon for each tab.
                        switch (position){
                            case 0:
                                tab.setText("Call");
                                tab.setIcon(R.drawable.ic_baseline_call_24);
                                break;
                            case 1:
                                tab.setText("SMS");
                                tab.setIcon(R.drawable.ic_baseline_sms_24);
                                break;
                            case 2:
                                tab.setText("Email");
                                tab.setIcon(R.drawable.ic_baseline_alternate_email_24);
                                break;
                        }
                    }
                }
        );

        tabLayoutMediator.attach();

        return view;
    }




}