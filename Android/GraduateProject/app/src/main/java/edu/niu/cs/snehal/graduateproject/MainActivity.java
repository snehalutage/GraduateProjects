/*************************************
 * Name           : Snehal Utage
 * Course:        : CSCI 522
 * Z-ID:          : Z1888637
 * Project:       : Final Project
 * Due Date:      : 04/28/2021
 * Project Details: Math Tutor App
 *
 * This application is used to demonstrate a
 * Math Tutor App for kids. It provides basic
 * Math Operation questions for Addition,
 * Subtraction, Multiplication, Division to
 * users. User is displayed menu of buttons
 * for each operation. On clicking a button
 * user is provided with 5 question for which
 * user need to provide answers. At the end
 * the user will get the report for their
 * answers.
 * Number for the  questions are generated using
 * Random Number Generator.(Kept Max bound - 15)
 *************************************/

package edu.niu.cs.snehal.graduateproject;

//import the required packages
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Class : MainActivity
 * Details : Called by the SplashActivity after the splash screen is displayed
 */
public class MainActivity extends AppCompatActivity
{
    //Define instance variables
    private static final String ADDITION = "+",
                                SUBTRACTION = "-",
                                MULTIPLICATION = "*",
                                DIVISION = "/";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.helpFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Create Intent to go to HelpActivity
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);

                //Start the HelpActivity
                startActivity(intent);
            }//end onClick
        });
    }//end onCreate


    /**
     * Method:  getDivision
     * Details: Handles the division button click
     * Onclick calls the DivisionActivity which
     * displays the questions to user.
     * Kept separate method as for division user
     * need to input 2 values - one for quotient
     * and one for remainder.
     */
    public void getDivision(View view)
    {
        //Get the content description (Operation) selected operation
        String operation = view.getContentDescription().toString();

        //Create Intent object that calls DivisionActivity
        Intent divisionIntent = new Intent(MainActivity.this, DivisionActivity.class);

        //Put the details for operation (used to display text on DivisionActivity)
        divisionIntent.putExtra("operation",operation);
        divisionIntent.putExtra("operator",DIVISION);

        //Start the activity
        startActivity(divisionIntent);
    }//end getDivision

    /**
     * Method:  getQuestions
     * Details: Handles the addition, subtraction , multiplication
     * button click.
     * On clicking the QuestionsActivity is called which
     * displays the questions for the respective operations to user.
     */
    public void getQuestions(View view)
    {
        //Get the content description (Operation) selected operation
        String operation = view.getContentDescription().toString();

        //Create the intent object
        Intent operationIntent = new Intent(MainActivity.this, QuestionsActivity.class);

        //Put the operation(Operation Name) in intent object
        operationIntent.putExtra("operation",operation);

        //Put the operator(symbol) in intent object
        switch(operation)
        {
            case "ADDITION":
                operationIntent.putExtra("operator", ADDITION);
                break;

            case "SUBTRACTION":
                operationIntent.putExtra("operator", SUBTRACTION);
                break;

            case "MULTIPLICATION":
                operationIntent.putExtra("operator", MULTIPLICATION);
                break;

            default:
                Toast.makeText(view.getContext(),"A PROBLEM OCCURRED",Toast.LENGTH_SHORT).show();
                return;
        }//end switch

        //start the QuestionsActivity
        startActivity(operationIntent);
    }//end getQuestions

}//end MainActivity