package ibrahim.example.stocklogger.fragments.credits;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ibrahim.example.stocklogger.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditPictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditPictureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreditPictureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditPictureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditPictureFragment newInstance(String param1, String param2)  {
        CreditPictureFragment fragment = new CreditPictureFragment();
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
        View view = inflater.inflate(R.layout.fragment_credit_picture, container, false);

        ImageView creditCallImage = view.findViewById(R.id.creditCallImage);
        creditCallImage.setOnClickListener(new OpenUrl("https://www.flickr.com/photos/indyplanets/5951860034/in/photostream/"));

        ImageView creditSmsImage = view.findViewById(R.id.creditSmsImage);
        creditSmsImage.setOnClickListener(new OpenUrl("https://www.sme-news.co.uk/how-sms-can-help-your-business-marketing/"));

        ImageView creditEmailImage = view.findViewById(R.id.creditEmailImage);
        creditEmailImage.setOnClickListener(new OpenUrl("https://cms.coronadousd.net/news/email-communication-via-powerschool/"));


        return view;
    }

    class OpenUrl implements View.OnClickListener{
        private String url;

        public OpenUrl(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if(i.resolveActivity(getContext().getPackageManager()) != null){
                getContext().startActivity(i);
            }
        }
    }
}