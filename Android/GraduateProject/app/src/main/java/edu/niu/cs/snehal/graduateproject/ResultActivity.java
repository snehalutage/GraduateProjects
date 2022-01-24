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
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class : ResultActivity
 * Details : Called by the DivisionActivity/QuestionActivity
 * after the Get Score button is clicked
 * Provides the details for score report of the questions
 * answered by user. Total correct questions and the status of
 * each question as correct/incorrect
 */
public class ResultActivity extends AppCompatActivity
{

    //Define instance variables
    private TextView resultTV, q1TV, q2TV, q3TV, q4TV, q5TV;
    private ArrayList<Integer> answerStatus;
    private ArrayList<TextView> printStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //connect the instance varaibles with items on screen
        q1TV = findViewById(R.id.q1TextView);
        q2TV = findViewById(R.id.q2TextView);
        q3TV = findViewById(R.id.q3TextView);
        q4TV = findViewById(R.id.q4TextView);
        q5TV = findViewById(R.id.q5TextView);

        resultTV = findViewById(R.id.resultTextView);

        //Get the intent object
        Intent getResult = getIntent();

        //Retrieve the information passed and set the text in TextView
        String result = getResult.getStringExtra("result");
        resultTV.setText(result);

        //Get the ArrayLit of the status of each question and print it
        answerStatus = getResult.getIntegerArrayListExtra("status");

        //Initialize the Arraylist
        printStatus = new ArrayList<>();

        //Add the status(correct/incorrect) in the ArrayList for each question
        printStatus.add(q1TV);
        printStatus.add(q2TV);
        printStatus.add(q3TV);
        printStatus.add(q4TV);
        printStatus.add(q5TV);

        TextView textView;

        //Print the arraylist i.e set the respective textviews
        for (int i=0; i<5; i++)
        {
            textView = printStatus.get(i);

            //If the passed answerStatus arraylist has the cuurent index
            //then it is answered correctly so set the text as Correct
            //else incorrect
            if(answerStatus.contains( i + 1 ))
            {
                textView.setText("Correct");
            }
            else
            {
                textView.setText("Incorrect");
            }
        }//end for
    }//end onCreate

    /**
     * Method   : goBack
     * Details : Method to handle button click
     * Goes to MainActivity
     */
    public void goHome(View view)
    {
        //Create the intent object to got to MainActivity
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);

        //Start the activity
        startActivity(intent);
    }//end goHome

}//end ResultActivity