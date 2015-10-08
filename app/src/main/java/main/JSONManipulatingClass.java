package main;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONManipulatingClass {
    private static final File root = new File(Environment.getExternalStorageDirectory(), "CrosswordInput");
    private static final File myFile = new File(root, "crossword_bg.txt");

    public static Crossword parseJsonCrossword(InputStreamReader reader) throws IOException, ParseException {
        Crossword cInfo = null;
        List<Word> wordList = new ArrayList<Word>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONObject crossword = (JSONObject) jsonObject.get("crossword");
        JSONArray wordsJSON = (JSONArray) jsonObject.get("words");
        Iterator i = wordsJSON.iterator();
        while (i.hasNext()) {
            JSONArray innerObj = (JSONArray) i.next();
            Word tempWord = new Word((Long) innerObj.get(0),
                    (Long) innerObj.get(1), (Long) innerObj.get(2),
                    (String) innerObj.get(3), (String) innerObj.get(4));
            wordList.add(tempWord);
        }
        Crossword info = new Crossword(
                (Long) crossword.get("width"),
                (Long) crossword.get("height"),
                (String) crossword.get("language"),
                (String) crossword.get("encoding"),
                (String) crossword.get("source"), wordList);
        cInfo = info;
        return cInfo;
    }

}
