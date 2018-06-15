package com.aashnashroff.domino;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LightInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_info);
    }


    /** Called when the user touches the back button. */
    public void onClickBack(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_play);
    }

    /** Called when the user touches the forward (next) button. */
    public void onClickForward(View view) {
        Intent intent = new Intent(this, LightPlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_play);
    }
}
