package ibrahim.example.stocklogger.fragments.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import ibrahim.example.stocklogger.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactEmailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactEmailFragment newInstance(String param1, String param2) {
        ContactEmailFragment fragment = new ContactEmailFragment();
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
        View view =  inflater.inflate(R.layout.fragment_contact_email, container, false);


        Spinner emailSpinner = view.findViewById(R.id.emailSpinner);
        ArrayAdapter<CharSequence> topicAdapter = ArrayAdapter.createFromResource(getContext(),R.array.smsTitles, android.R.layout.simple_spinner_item);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emailSpinner.setAdapter(topicAdapter);


        Button emailButton = view.findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topic = emailSpinner.getSelectedItem().toString();
                Log.d("SPINNER", topic);

                Intent i = new Intent(Intent.ACTION_SENDTO);
                Uri email = Uri.parse("mailto:");
                String[] emailAddress = {getResources().getString(R.string.text_email_address)};

                i.setData(email);
                i.putExtra(Intent.EXTRA_EMAIL,emailAddress);
                i.putExtra(Intent.EXTRA_SUBJECT, topic);


                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(i);
                } else {
                    startActivity(i);
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),"Cannot send an email", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        return view;
    }
}