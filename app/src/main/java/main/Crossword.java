package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import example.CrosswordInterface;

public class Crossword implements CrosswordInterface, Serializable {

    private int width = 1;
    private int height = 1;
    private String language = "bg_BG";
    private String encoding = "UTF-8";
    private String source = "";
    private List<Word> allWords;

    public Crossword(Long width, Long height, String language,
                     String encoding, String source, List<Word> allWords) {
        this.width = width.intValue();
        this.height = height.intValue();
        this.language = language;
        this.encoding = encoding;
        this.source = source;
        this.allWords = allWords;
    }

    public Crossword() {
        this.width = 0;
        this.height = 0;
        this.language = "bg_BG";
        this.encoding = "UTF-8";
        this.source = "";
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public List<Word> getWords() {
        return allWords;
    }

    @Override
    public ArrayList<Word> getWordsAt(int y, int x) {
        ArrayList<Word> selectedWords = new ArrayList<Word>();
        for (Word word : allWords) {
            if ((word.getDirection() == 0 && word.getStartY() == y) && (word.getStartX() <= x && (word.getStartX() + word.getLength()) >= x)) {
                selectedWords.add(word);
            }
            if ((word.getDirection() == 1 && word.getStartX() == x) && (word.getStartY() <= y && (word.getStartY() + word.getLength()) >= y)) {
                selectedWords.add(word);
            }
        }
        return selectedWords;
    }

    @Override
    public String toString() {
        String str = String
                .format("Width: %d, Height: %d, Language: %s.\n",
                        this.width, this.height, this.language);
        return str;
    }

    public String getLanguage() {
        return this.language;
    }

}
