package com.hw1.devlyn.thewateringhole;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FragmentUserProfile extends Fragment implements  View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private boolean mIntentInProgress;

    /*Fields for the buttons to be used in this class.*/
    Button save;
    Button load;
    StringBuilder str;
    String filename = "UserProfile.txt";
    EditText username;
    EditText paintPad;

    /**
     * Method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEvents.
     */
    public static FragmentUserProfile newInstance(String param1, String param2) {
        FragmentUserProfile fragment = new FragmentUserProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentUserProfile() {
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
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        getButtons(rootView);

        final Button save = (Button) rootView.findViewById(R.id.save_btn);
        final Button load = (Button) rootView.findViewById(R.id.load_btn);

        username = (EditText) rootView.findViewById(R.id.editText);

        load.setOnClickListener(this);

        save.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                /*Intent myIntent = new Intent(getActivity(), Save.class);
                startActivity(myIntent);    // change to startActivity*/
               if (v == save) {
                   save(filename);
               }
            }
        });
        str = new StringBuilder();

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
    public void onClick(View v) {
        if (v == save) {
            // When clicked, show a toast with the TextView text
            Toast.makeText(this.getActivity().getApplicationContext(), ((Button) v).getText(),
                    Toast.LENGTH_SHORT).show();

            //Append the text to the string builder
            str.append(((Button) v).getText());

            //Display the result.
            username.setText(str.toString());

            save(filename);
        } else if (v == load){
                Toast.makeText(this.getActivity().getApplicationContext(), ((Button) v).getText(),
                        Toast.LENGTH_SHORT).show();

                //Append the text to the string builder
                str.append(((Button) v).getText());

                //Display the result.
            /*results.setText(str.toString());*/
                load(filename);
        }
    }


    public void save(String filename)
    {
        File file = new File(getActivity().getFilesDir(), filename);

        String string = "";

        if(username != null) string = username.getText().toString();

        FileOutputStream outputStream;

        try{
            outputStream  = getActivity().openFileOutput(
                    filename, getActivity().MODE_PRIVATE);
            /*outputStream  = ctx.openFileOutput(filename, Context.MODE_PRIVATE);*/
            outputStream.write(string.getBytes());
            outputStream.close();

            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "Error File Not Found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch (IOException e) {
            Toast.makeText(getActivity(), "Error IOException", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        /*catch (Exception e) {
            Toast.makeText(getActivity(), "Error Exception", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/
    }

    public void load(String filename) {
        int ch;
        StringBuffer fileContent = new StringBuffer("");
        FileInputStream fis;

       /* File file = new File(getActivity().getFilesDir(), filename);*/

        try {
            fis = getActivity().openFileInput(filename);
            try {
                while ((ch = fis.read()) != -1)
                    fileContent.append((char) ch);
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error loading file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "There was no data to load", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String data = new String(fileContent);

        username.setText(data);
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