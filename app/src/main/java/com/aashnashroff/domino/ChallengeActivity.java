package com.aashnashroff.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;

public class ChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //get LinearLayout to display buttons on
        LinearLayout layout = findViewById(R.id.challenges_roadmap);

        Intent intent = getIntent();
        String[] challenges = intent.getStringArrayExtra("challenges_names");
        String[] challenges_desc = intent.getStringArrayExtra("challenges_desc");
        //Iterate through challenges. For each challenge, add and display one button.
        for(int i = 0; i < challenges.length; i++){
            Button challenge_button = new Button(this);
            challenge_button.setText(challenges[i]);
            setOnClick(challenge_button, challenges_desc[i]);
            challenge_button.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(challenge_button);
        }
    }


    public void setOnClick(Button btn, final String challenge_desc){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengeActivity.this, BuildActivity.class);
                intent.putExtra("challenge_desc", challenge_desc);
                startActivity(intent);
                setContentView(R.layout.activity_build);
            }
        });
    }

    /** Called when the user touches the play button. */
    public void startPlay(View view) {
        Intent intent = new Intent(this, LightPlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_play);
    }


}
