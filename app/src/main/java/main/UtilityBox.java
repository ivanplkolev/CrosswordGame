package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikolev on 7/29/2015.
 */
public class UtilityBox {

    public static Box[][] getArrayOfBoxes(Crossword crossword) {
        List<Word> allWords = crossword.getWords();
        // Creating array of chars to use them to fill the Box[][] with chars
        char[][] crosswordInChars = new char[crossword.getHeight()][crossword.getWidth()];
        for (int i = 0; i < crosswordInChars.length; i++) {
            for (int j = 0; j < crosswordInChars[0].length; j++) {
                crosswordInChars[i][j] = '*';
            }
        }
        for (Word word : allWords) {
            if (word.getDirection() == Word.HORIZONTAL) {
                for (int i = 0; i < word.getLength(); i++) {
                    crosswordInChars[word.getStartY()][i + word.getStartX()] = word.getAnswer().charAt(i);
                }
            }
            if (word.getDirection() == Word.VERTICAL) {
                for (int i = 0; i < word.getLength(); i++) {
                    crosswordInChars[i + word.getStartY()][word.getStartX()] = word.getAnswer().charAt(i);
                }
            }
        }

        // Create arrey of Boxes
        Box[][] boxes = new Box[crossword.getHeight()][crossword.getWidth()];

        // Creating Box objects, willing them with information and filling the Box[][] with them
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[0].length; j++) {
                Box tempBox = new Box();
                if (crosswordInChars[i][j] != '*') {
                    tempBox.setIsActive(true);
                    tempBox.setIsPressed(0);
                    tempBox.setIsFilled(false);
                    tempBox.setX(j);
                    tempBox.setY(i);
                    tempBox.setAnswerChar(crosswordInChars[i][j]+"");
                    ArrayList<Word> words = crossword.getWordsAt(i, j);
                    tempBox.setWordsAtBox(words);
                }
                boxes[i][j] = tempBox;
            }
        }
        return boxes;
    }
}