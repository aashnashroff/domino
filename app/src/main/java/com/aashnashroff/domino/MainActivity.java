package com.aashnashroff.domino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user touches the play button. */
    public void startPlay(View view) {

        Intent intent = new Intent(this, PlayActivity.class);

//        intent.putExtra("domino_name", "testDominoName");

        startActivity(intent);
        setContentView(R.layout.activity_play);
    }
}
