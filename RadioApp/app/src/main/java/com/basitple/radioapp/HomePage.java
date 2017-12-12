package com.basitple.radioapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button newsButton;
    private Fragment newsView;
    private Button alarmButton;
    private Fragment alarmView;
    private Button musicButton;
    private Fragment musicView;
    private Button profileButton;
    private Fragment profileView;
    private Button logOutButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    SlidingUpPanelLayout slidingLayout;
    public static List<Song> songs;
    private View inflatedView;

    private OnFragmentInteractionListener mListener;

    public HomePage() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
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
        this.inflatedView = inflater.inflate(R.layout.fragment_home_page, container, false);
        newsButton = (Button)inflatedView.findViewById(R.id.NEWS_BUTTON);
        alarmButton = (Button)inflatedView.findViewById(R.id.ALARM_BUTTON);
        musicButton = (Button)inflatedView.findViewById(R.id.MUSIC_BUTTON);
        profileButton = (Button)inflatedView.findViewById(R.id.PROFILE_BUTTON);
        logOutButton = (Button)inflatedView.findViewById(R.id.LOG_OUT_BUTTON);
        slidingLayout = (SlidingUpPanelLayout)getActivity().findViewById(R.id.SLIDING_LAYOUT);

        inflatedView.setFocusableInTouchMode(true);
        inflatedView.requestFocus();
        inflatedView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
                    if(slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                        getActivity().finish();
                    } else {
                        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    }
                    return true;
                }
                return false;
            }
        });



        newsButton.setOnClickListener(this);
        alarmButton.setOnClickListener(this);
        logOutButton.setOnClickListener(this);
        musicButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);
        return inflatedView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        fragmentManager = getActivity().getSupportFragmentManager();
        switch (vId){
            case R.id.NEWS_BUTTON:
                if(newsView == null) newsView = new NewsPageFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragContainer, newsView);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.MUSIC_BUTTON:
                if(musicView == null) musicView = new MusicPageFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragContainer, musicView);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.PROFILE_BUTTON:
                if(profileView == null) profileView = new ProfilePageFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragContainer, profileView);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.ALARM_BUTTON:
                if(alarmView == null) alarmView = new AlarmPageFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragContainer, alarmView);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.LOG_OUT_BUTTON:
                getActivity().finish();
                break;


        }
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
