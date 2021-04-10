package ibrahim.example.stocklogger.fragments.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import ibrahim.example.stocklogger.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactCallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactCallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int PERMISSION_CALL_PHONE = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactCallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactCallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactCallFragment newInstance(String param1, String param2) {
        ContactCallFragment fragment = new ContactCallFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_call, container, false);

        TextView callTextView = view.findViewById(R.id.callTextView);
        callTextView.setOnClickListener(new PhoneCall(callTextView.getText().toString()));

        return view;
    }

    /**
     * <h1>App for Stock Logger</h1>
     * <h2>Android Final Project of MAD305 Course</h2>
     *
     *  OnClickListener class implemets {@link android.view.View.OnClickListener}<br>
     *  get call number as string in constructor method<br>
     *  open phone app with call number in onClick method<br>
     *
     * @author Wusiman Yibuulayin
     * @version 1.0
     * @since 10/04/21
     *
     */
    class PhoneCall implements View.OnClickListener{
        private String callNumber;

        /**
         * Constructor to get the call number
         * @param callNumber
         */
        public PhoneCall(String callNumber) {
            this.callNumber = callNumber;
            //this.view = view;
        }

        @Override
        public void onClick(View v) {
            if(ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),"Permission NOT Granted", Snackbar.LENGTH_LONG);
                snackbar.show();

                if(ActivityCompat.shouldShowRequestPermissionRationale(
                        getActivity(),
                        Manifest.permission.CALL_PHONE
                )){

                } else {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[] {Manifest.permission.CALL_PHONE},
                            PERMISSION_CALL_PHONE
                    );
                }
            } else {

                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri call = Uri.parse("tel:" + callNumber);
                i.setData(call);

                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(i);
                } else {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),"Cannot make a call", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        }
    }
}