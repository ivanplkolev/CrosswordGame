package main;

import java.io.Serializable;

import example.WordInterface;

public class Word implements WordInterface, Serializable {
    private int startX = 0;
    private int startY = 0;
    private int direction = 0;
    private int length = 0;
    private String answer = "";
    private String hint = "";
    private int[][] coordinates;

    public Word(Long startX, Long startY, Long direction, String answer,
                String hint) {
        this.startX = startX.intValue();
        this.startY = startY.intValue();
        this.direction = direction.intValue();
        this.answer = answer;
        this.hint = hint;
        this.length = answer.length();
        int[][] coordinates = new int[answer.length()][2];
        for (int i = 0; i < answer.length(); i++) {
            if (direction == Word.HORIZONTAL) {
                coordinates[i][0] = startY.intValue();
                coordinates[i][1] = startX.intValue() + i;
            } else {
                coordinates[i][0] = startY.intValue() + i;
                coordinates[i][1] = startX.intValue();
            }
        }
        this.coordinates=coordinates;
    }

    @Override
    public int getStartX() {
        // TODO Auto-generated method stub
        return this.startX;
    }

    @Override
    public int getStartY() {
        // TODO Auto-generated method stub
        return this.startY;
    }

    @Override
    public int getDirection() {
        // TODO Auto-generated method stub
        return this.direction;
    }

    @Override
    public int getLength() {
        // TODO Auto-generated method stub
        return this.length;
    }

    @Override
    public String getAnswer() {
        // TODO Auto-generated method stub
        return this.answer;
    }

    @Override
    public String getHint() {
        // TODO Auto-generated method stub
        return this.hint;
    }

    @Override
    public String toString() {
        String str = String
                .format("The X of the word is: %d, The y is: %d, The direction is: %d, The length is: %d, The answer is: %s, The hint is: %s.\n",
                        this.startX, this.startY, this.direction, this.length,
                        this.answer, this.hint);
        return str;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[][] coordinates) {
        this.coordinates = coordinates;
    }
}
