package com.example.michal.pm_lab02;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {

    private TextView textView1, textView2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public SendFragment() {
        // Required empty public constructor
    }

    public static SendFragment newInstance(String param1, String param2) {
        SendFragment fragment = new SendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        String mParam1 = "problem";
        String mParam2 = "problem";
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        this.textView1 = (TextView) view.findViewById(R.id.sms_send_num);
        this.textView1.setText(mParam2);
        this.textView2 = (TextView) view.findViewById(R.id.sms_send_text);
        this.textView2.setText(mParam1);



        return view;
    }

}
