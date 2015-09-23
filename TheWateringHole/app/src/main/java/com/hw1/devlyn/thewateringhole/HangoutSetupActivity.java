package com.hw1.devlyn.thewateringhole;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HangoutSetupActivity extends Activity implements View.OnClickListener {

    Button Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangout_setup);

        Finish = (Button) this.findViewById(R.id.finish_btn);

        Finish.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == Finish) {
            Intent Finish = new Intent(this, HangoutPageActivity.class);

            Button b = (Button) v;
            this.startActivity(Finish);
        }
    }


}
