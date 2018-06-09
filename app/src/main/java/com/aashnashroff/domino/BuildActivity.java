package com.aashnashroff.domino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class BuildActivity extends AppCompatActivity {

    private Applet currApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);

        //get challenge description from intent params
        Intent intent = getIntent();
        String challenge_desc = intent.getStringExtra("challenge_desc");
        Log.d("oncreate for build", "description: "+ challenge_desc);

        //set textview to contain description
        TextView desc_textbox = findViewById(R.id.challenge_description);
        desc_textbox.setText(challenge_desc);

    }

}
