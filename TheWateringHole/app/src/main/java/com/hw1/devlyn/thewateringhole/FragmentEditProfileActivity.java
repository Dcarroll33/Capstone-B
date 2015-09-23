package com.hw1.devlyn.thewateringhole;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by drcar on 9/20/2015.
 */
public class FragmentEditProfileActivity extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = 0 ;
    private static final int SELECT_IMAGE = 0 ;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*Fields for the buttons to be used in this class.*/

    EditText Description;
    EditText Likes_Dislikes;
    EditText UserName;

    String description;
    String likes_dislikes;
    String name;

    Button Save;
    Button UploadImage;

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;

    private static AsyncTask<String, Void, String> dbCon;

    /**
     * Method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEvents.
     */
    public static FragmentEditProfileActivity newInstance(String param1, String param2) {
        FragmentEditProfileActivity fragment = new FragmentEditProfileActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentEditProfileActivity() {
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
        View rootView = inflater.inflate(R.layout.fragment_edit_profile_activity, container, false);

        getButtons(rootView);

        Description = (EditText) rootView.findViewById(R.id.Description);
        Likes_Dislikes = (EditText) rootView.findViewById(R.id.Likes_Dislikes);
        UserName = (EditText) rootView.findViewById(R.id.Username);
        Save = (Button) rootView.findViewById(R.id.save_btn);
        UploadImage = (Button) rootView.findViewById(R.id.uploadImage);

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
        if (view == Save) {
            description = Description.getText().toString();
            likes_dislikes = Likes_Dislikes.getText().toString();
            name = UserName.getText().toString();

            /*if (description != null && likes_dislikes != null){*/

            ConnectDb conDb = new ConnectDb();
            try {
                String[] params = {"save", String.valueOf(conDb.getUserId()), description, likes_dislikes, name};
                conDb.execute(params).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            int save = conDb.getSave();
            if (save > -1) {
                Log.d("EDIT PROFILE", "Sent: " + description + ", " + likes_dislikes + "," + name);
                Toast.makeText(getActivity(), "Updated Profile!", Toast.LENGTH_SHORT).show();
            } else if (save == -1) {
                Log.d("Description", "Sent: " + description + ", " + "Likes/Dislikes " + likes_dislikes + "," + name);
                Toast.makeText(getActivity(), "Save Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        }
        if (view == UploadImage) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
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
