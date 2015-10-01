package example;

import java.util.List;

public interface CrosswordInterface{
    /**
    * Returns the number of squares on the horizontal axis of the crossword
    */
    public int getWidth();
    /**
    * Returns the number of squares on the vertical axis of the crossword
    */
    public int getHeight();
    
    /**
    * Returns all the words that are part of this crossword
    */
    public List<? extends WordInterface> getWords();
    
    /**
    * Returns the list of words that share a letter at the given coordinates
    * Note: the function can return an empty list, but should never return NULL
    */
    public List<? extends WordInterface> getWordsAt(int x, int y);
}
