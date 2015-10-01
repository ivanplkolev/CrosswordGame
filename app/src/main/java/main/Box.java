package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Box implements Serializable {
    private float x;
    private float y;
    private boolean isActive = false;
    private int isPressed = 0;
    private boolean isSelected = false;
    private boolean isFilled = false;
    private String writtenChar ="";
    private String answerChar;
    private boolean isCorrect=true;
    private List<Word> wordsAtBox;

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {return this.x;}

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {return this.y;}


    public void setIsPressed(int isPressed) {
        this.isPressed = isPressed;
    }

    public int getisPressed(){
        return this.isPressed;
    }

    public void setWordsAtBox(List<Word> wordsAtBox){
        this.wordsAtBox=wordsAtBox;
    }

    public List<Word> getWordsAtBox(){
        return this.wordsAtBox;
    }


    public void setIsFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public void setWordsAtBox(ArrayList<Word> wordsAtBox) {
        this.wordsAtBox = wordsAtBox;
    }

    public void setAnswerChar(String answerChar) {
        this.answerChar = answerChar;
    }

    @Override
    public String toString() {
        String str = String.format("The X of the Box is: %s, The y is: %s.\n", this.x, this.y);
        return str;
    }

    public String getWrittenChar() {
        return writtenChar;
    }

    public void setWrittenChar(String writtenChar) {
        this.writtenChar = writtenChar;
    }

    public void incrementIsPressed(){
        this.isPressed++;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAnswerChar() {
        return answerChar;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
