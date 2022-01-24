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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class   : DivisionActivity
 * Details : Called by the MainActivity after the division operation button
 * User is presented with a screen where user can see one question
 * for the selected operation and user has to enter the answer
 * i.e Quotient and Remainder in the respective Edit Text field.
 * In total there are 5 question to be answered. Next question
 * button is provided at the bottom to go to next question.
 * Home button provided at top corner to go back to Main Screen.
 * Once 5 questions are done user can click on Get Score to get
 * the result for their answers.
 */
public class DivisionActivity extends AppCompatActivity
{
    //Define Instance variables
    private TextView num1TV, num2TV, operationTV, operatorTV, questionNumTV;
    private EditText quotientET, remainderET;
    private Random randomNum = new Random();
    private int index = 0;

    //Instantiate object of class QuestionModel
    private ArrayList<QuestionModel> questionBank;

    //List of integer pairs to store the quotient and remainder
    private List<Integer> pairs;

    //Instantiate Arraylist to store the user answers and the correct Answers
    private ArrayList<List> userAnswers = new ArrayList<List>();
    private ArrayList<Integer> correctAnswers = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        //connect the instance variables with the items on screen
        num1TV = findViewById(R.id.num1TextView);
        num2TV = findViewById(R.id.num2TextView);
        operationTV = findViewById(R.id.titleTextView);
        operatorTV = findViewById(R.id.operatorTextView);
        questionNumTV = findViewById(R.id.qNumTextView);

        //Two edit texts to accept the quotient and remainder
        quotientET = findViewById(R.id.quotientEditText);
        remainderET = findViewById(R.id.remainderEditText);

        //Get the Intent object
        Intent intent = getIntent();

        //Retrieve the information passed from MainActivity
        String operation = intent.getStringExtra("operation"),
                operator = intent.getStringExtra("operator") ;

        //Set the textview on the DivisionActivity Screen based on the values passed
        operationTV.setText(operation);
        operatorTV.setText(operator);

        //Instantiate the question bank
        questionBank = new ArrayList<QuestionModel>();

        //Generate 5 random number pairs(num1, num2) and add them to the
        //questionBank ArrayList
        for ( int i =1 ; i <= 5; i++)
        {
            //Generate the random numbers
            Integer num1 = randomNum.nextInt(20),
                    num2 = randomNum.nextInt(10);

            //Avoid num2 to be 0 to avoid division by zero error
            // regenerate the number until it is not 0
            while (num2 == 0)
            {
                num2 = randomNum.nextInt(10);
            }

            //Set the questionBank class attributes by calling the constructor
            questionBank.add(new QuestionModel(num1, num2, operationResult(operator, num1, num2)));
        }

        //Set the focus on answer EditText
        quotientET.requestFocus();

        //Set the text view with the current question number
        questionNumTV.setText("Question No. " + (index + 1));

        //Call updateQuestion to update the textview with the numbers for first time
        updateQuestion();
    }

    /**
     * Method   : goBack
     * Details : Method to handle button click
     * Returns to MainActivity
     */
    public void goBack(View view)
    {
        finish();
    }

    /**
     * Method   : nextQuestion
     * Details : Method to handle the Next Question button click
     * Based on the size of question bank (5) displays the
     * next questions to user on clicking button. Calls the
     * updateQuestion to display the numbers in textview for
     * each question. Gets the user input and calls checkAnswers().
     */
    public void nextQuestion(View view)
    {
        //If user does not input answers in EditTexts throw toast error msg
        if( quotientET.getText().toString().matches("") || remainderET.getText().toString().matches(""))
        {
            //display an error message /Toast message
            Toast.makeText(this,"The quotient and remainder field cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }//end if

        //Get the user answers for Quotient and Remainder
        Integer ansQuotient = Integer.parseInt(quotientET.getText().toString());
        Integer ansRemainder = Integer.parseInt(remainderET.getText().toString());

        //Create pair of list
        pairs = Arrays.asList(ansQuotient,ansRemainder);

        //Add to array list of answers
        userAnswers.add(pairs);

        //Increase the index/counter for question by 1
        index = (index + 1) % questionBank.size();
        questionNumTV.setText("Question No. " + (index+1));

        //Update the numbers textviews with the next pair of numbers which are generated
        //randomly from the questionBank arraylist
        updateQuestion();

        //Index the counter kept to maintian the question numbers
        //If the index is the last question i.e 5th then change the
        //text on the button to "Get Score" and onclicking that
        // button it calls the checkAnswer()
        if ( index == questionBank.size()-1)
        {
            //Create a button for Get result and set the label to Get Score
            Button btn = (Button)view;
            btn.setText(R.string.result_label);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( quotientET.getText().toString().matches("") || remainderET.getText().toString().matches(""))
                    {
                        //display an error message /Toast message
                        Toast.makeText(view.getContext(),"The quotient and remainder field cannot be empty",Toast.LENGTH_SHORT).show();
                        return;
                    }//end if

                    //Get the user entered values from EditText for Quotient and Remainder
                    Integer ansQuotient = Integer.parseInt(quotientET.getText().toString());
                    Integer ansRemainder = Integer.parseInt(remainderET.getText().toString());

                    //Create a list of pairs of integers
                    pairs = Arrays.asList(ansQuotient,ansRemainder);

                    //Add the pairs of user quotient and remainder to arraylost
                    userAnswers.add(pairs);

                    //Get the result
                    checkAnswers(userAnswers);
                }//end onClick
            });
            return;
        }//end if
    }//end nextQuestion


    /**
     * Method   : updateQuestion
     * Details : Method updates the question
     * Sets the textviews with numbers in the question
     * bank arraylist which has pairs of numbers (num1,num2)
     * randomly generated.
     */
    public void updateQuestion()
    {
        num1TV.setText(((Integer) questionBank.get(index).getNum1()).toString());
        num2TV.setText(((Integer) questionBank.get(index).getNum2()).toString());
        quotientET.setText("");
        remainderET.setText("");
        quotientET.requestFocus();
    }//end updateQuestion

    /**
     * Method   : checkAnswers
     * Details : Method to handle Get Score button click
     *Compare the pairs of user entered Quotient and Remainder
     * with the calcuated answers for Quotient and Remainder
     * Maintain the count and the status of each question
     */
    public void checkAnswers(ArrayList<List> ans)
    {
        //Variable to store the correctly answered questions count
        int count=0;

        //Loop for 5 questions
        for ( int i=0; i<5; i++)
        {
            //Check the user answers with the calculated answers
            if (ans.get(i).equals(questionBank.get(i).getDivisionAnswer() ))
            {
                //If answer is correct add the question number in list
                correctAnswers.add(i + 1);

                //Increase counter to find the total correct answers
                count++;
            }//end if
        }//end for

        //Create intent object
        Intent getResult = new Intent(DivisionActivity.this, ResultActivity.class);

        //Put the details for the score and the question status(incorrect/correct)
        getResult.putExtra("result",count+ "/" + questionBank.size());
        getResult.putIntegerArrayListExtra("status", correctAnswers);

        //Start the ResultActivity
        startActivity(getResult);
    }//end checkAnswers

    /**
     * Method   : operationResult
     * Details : Method to calculate the answer of
     * the question for the division operation.
     * Returns a list of integer pairs quotient and
     * remainder for the division result.
     */
    public List<Integer> operationResult(String operator, int a, int b)
    {
        int quotient = a/b;
        int remainder = a%b;
        return Arrays.asList(quotient,remainder);
    }//end operationResult
}