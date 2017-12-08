package com.basitple.radioapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class news extends Fragment {
    Button backButton;
    private StringBuilder text = new StringBuilder();
    private StringBuilder text2 = new StringBuilder();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

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
                FragmentManager fm = getFragmentManager();
                        // create a FragmentTransaction to begin the transaction and replace the Fragment
                        Fragment f = getFragmentManager().findFragmentByTag("first");

                        //if the fragment exists remove it
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        if (f != null) fragmentTransaction.remove(f);

                        //commit
                        fragmentTransaction.commit();
                    }
                });

                return view;
            }

        }
