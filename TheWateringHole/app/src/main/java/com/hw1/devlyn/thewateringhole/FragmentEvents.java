package com.hw1.devlyn.thewateringhole;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class FragmentEvents extends Fragment implements  View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private boolean mIntentInProgress;

    /*Fields for the buttons to be used in this class.*/
    Button LocateEvents;
    EditText eventDescription;
    EditText eventTitle;
    EditText numParticipants;
    String description;
    String title;
    String numPeople;

    /**
     * Method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEvents.
     */
    public static FragmentEvents newInstance(String param1, String param2) {
        FragmentEvents fragment = new FragmentEvents();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentEvents() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        getButtons(rootView);

        LocateEvents = (Button) rootView.findViewById(R.id.locate_events_btn);
        eventDescription = (EditText) rootView.findViewById(R.id.eventDescription);
        eventTitle = (EditText) rootView.findViewById(R.id.eventName);
        numParticipants = (EditText) rootView.findViewById(R.id.numParticipants);

        return rootView;
    }

    public void getButtons(View v){
        if(v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;

            for (int i = 0; i <vg.getChildCount(); i++) {
                View v1 = vg.getChildAt(i);
                if (v1 instanceof Button) {
                    Button b = (Button) v1;
                    b.setOnClickListener(this);
                }
                else if(v1 instanceof ViewGroup) {
                    getButtons(v1);
                }
            }
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*This method is for the on screen clicks by the user depending on which button is pushed
        in this case the Events, Friends, Profile or Settings buttons. Once one button has been
        clicked depending on their relationship the screen will switch to the appropriate screen.*/
    @Override
    public void onClick(View view) {
        if (view == LocateEvents) {
            /*Intent events = new Intent(getActivity(), LocateEventsActivity.class);

            Button b = (Button) view;
            this.startActivity(events);*/
            description = eventDescription.getText().toString();
            title = eventTitle.getText().toString();
            numPeople = numParticipants.getText().toString();

            /*if (description != null && likes_dislikes != null){*/

            ConnectDb conDb = new ConnectDb();
            try {
                String[] params = {"eventSave" /*,String.valueOf(conDb.getUserId())*/, title, description, numPeople};
                conDb.execute(params).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            int eventSave = conDb.getEventSave();
            if (eventSave > -1) {
                Log.d("EDIT PROFILE", "Sent: " + description + "name" + title + "numPeople" + numPeople);
                Toast.makeText(getActivity(), "Event Saved!", Toast.LENGTH_SHORT).show();
            } else if (eventSave == -1) {
                Log.d("Description", "Sent: " + description + "name" + title + "numPeople" + numPeople);
                Toast.makeText(getActivity(), "Save Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        }
        }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}

