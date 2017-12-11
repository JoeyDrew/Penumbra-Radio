package com.basitple.radioapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button backButton;
    private StringBuilder text = new StringBuilder();
    private StringBuilder text2 = new StringBuilder();

    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fragment selfFrag;

    private OnFragmentInteractionListener mListener;

    public NewsPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsPageFragment newInstance(String param1, String param2) {
        NewsPageFragment fragment = new NewsPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selfFrag = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_page, container, false);

        //Read In Text File to Display on Text View Article 1
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("Article1.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Error reading file!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        TextView output = (TextView) view.findViewById(R.id.textView);
        output.setText((CharSequence) text);

        //Read In Text File to Display on Text View Article 1

        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("Article2.txt")));

            // do reading, usually loop until end of file reading
            String mLine2;
            while ((mLine2 = reader2.readLine()) != null) {
                text2.append(mLine2);
                text2.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Error reading file!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader2.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        TextView output2 = (TextView) view.findViewById(R.id.textView3);
        output2.setText((CharSequence) text2);
        //Create a button destroy fragment
        backButton = (Button) view.findViewById(R.id.NEWS_BACK);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a FragmentManager

                getFragmentManager().popBackStackImmediate();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
