/*************************************
 * Name       : Snehal Utage
 * Course     : CSCI 522
 * Z-ID       : Z1888637
 * Project    : Final Project
 * Due Date   : 04/28/2021
 * Details    : Math Tutor App
 * ***********************************/

package edu.niu.cs.snehal.graduateproject;

//Import the required packages
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class : SplashActivity
 * Details : Called when the app is launched
 * First Activity that displays the app information screen to user
 * for 5sec and then calls the MainActivity
 */
public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Create the TimerTask object for displaying the splash screen and going to mainActivity
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                //Finish splash activity execution
                finish();

                //Goto main activity create explicit intent
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                //Start the activity
                startActivity(mainIntent);
            }//end run

        };//end timerTask

        //Setup the timer for how long the splash screen should display
        Timer timer = new Timer();
        timer.schedule(timerTask,5000); //5sec

    }//end onCreate

}