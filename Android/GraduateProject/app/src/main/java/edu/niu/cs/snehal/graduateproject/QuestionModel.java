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
import java.util.List;

/**
 * Class : QuestionModel
 * Details : Helper class it stores the data
 * member for each question, i.e the num1, num2
 * answer, and the division answer
 * Respective getter, setters
 */
public class QuestionModel
{
    //Declare the data members
    private int num1, num2;
    private int answer;
    private List<Integer> divisionAnswer;

    //constructor parameterized with 3 params( for +, *, -)
    public QuestionModel(int num1, int num2, int answer)
    {
        this.num1 = num1;
        this.num2 = num2;
        this.answer = answer;
    }

    //Constructor parameterized with 3 params (for /)
    public QuestionModel(int num1, int num2, List<Integer>divisionAnswer)
    {
        this.num1 = num1;
        this.num2 = num2;
        this.divisionAnswer = divisionAnswer;
    }

    //Getters
    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public int getAnswer() {
        return answer;
    }

    public List<Integer> getDivisionAnswer() {
        return divisionAnswer;
    }

    //Setters
    public void setDivisionAnswer(List<Integer> divisionAnswer)
    {
        this.divisionAnswer = divisionAnswer;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

}//end QuestionModel
