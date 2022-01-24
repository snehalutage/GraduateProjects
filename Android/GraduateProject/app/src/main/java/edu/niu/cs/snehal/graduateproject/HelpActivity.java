/*************************************
 * Name       : Snehal Utage
 * Course     : CSCI 522
 * Z-ID       : Z1888637
 * Project    : Final Project
 * Due Date   : 04/28/2021
 * Details    : Math Tutor App
 * ***********************************/
package edu.niu.cs.snehal.graduateproject;

//import the required packages
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/**
 * Class : HelpActivity
 * Details : Called by the MainActivity when the
 * Floating Action Button for help at the corner
 * of Main screen is clicked.
 * It provides basic details of app
 */
public class HelpActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }//end onCreate


    /**
     * Method   : goBack
     * Details : Method to handle button click
     * Returns to MainActivity
     */
    public void goBack(View view)
    {
        finish();
    }//end goBack

}//end HelpActivity