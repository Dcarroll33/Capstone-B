package com.hw1.devlyn.thewateringhole;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.internal.app.ToolbarActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.IntentSender.SendIntentException;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.concurrent.ExecutionException;


public class Login_Screen extends Activity implements View.OnClickListener {

/*Field declarations for the buttons login and register*/
    Button login;

    Button register;
    EditText userNameText;
    EditText userNamePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        /*Assigns the login and register button to the buttons in the XML layout by their ID's*/
        login = (Button) this.findViewById(R.id.login_button);
        register = (Button) this.findViewById(R.id.register_button);
        userNameText = (EditText) this.findViewById(R.id.userNameText);
        userNamePass = (EditText) this.findViewById(R.id.userNamePass);

        /*Runs the listener for button clicks in this instance for login and register*/
        login.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /*This method is used to listen for user clicks on the login & register buttons that are
        displayed on the screen*/
    public void onClick(View view) {
        ConnectDb conDb = new ConnectDb();
        /*Checks to see if the click is the login else if the click is on the register button*/
        if (view == login) {
            String userName = userNameText.getText().toString();
            String userPass = userNamePass.getText().toString();
            String[] params = {"login", userName, userPass};
            try {
                conDb.execute(params).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            int userId = conDb.getUserId();
            if(userId != -1){
                Toast.makeText(getBaseContext(), "Logged in", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(this, MainActivity.class );
                login.putExtra("userId", userId);

                this.startActivity(login);
            }else {
                Toast.makeText(getBaseContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

        }else if (view == register) {
            Intent register = new Intent(this, RegisterActivity.class);

            this.startActivity(register);
        }
    }
}
