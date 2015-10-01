package main;

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONManipulatingClass {
    //	private static final String filePath = "D:/CrossWordGame/crossword_bg.txt";
    private static final File root = new File(Environment.getExternalStorageDirectory(), "CrosswordInput");
    private static final File myFile = new File(root, "crossword_bg.txt");
//	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//	URL url = classLoader.getResource("path/to/folder");
//	File file = new File(url.toURI());
//private static final File myFile = new File("file:///android_asset/crossword_bg.txt");


    public static Crossword parseJsonCrossword(String file) throws IOException, ParseException {
//        AssetManager mng;
//        InputStream stream=mng.open("File");
//        InputStreamReader reader=new InputStreamReader(stream);

        File myFile = new File(file);
        InputStreamReader reader = new FileReader(myFile);
        return parseJsonCrossword(reader);
    }

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


    public static Crossword parseJsonCrossword() {
        Crossword cInfo = null;
        List<Word> wordList = new ArrayList<Word>();
        try {
            FileReader reader = new FileReader(myFile);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject crossword = (JSONObject) jsonObject.get("crossword");
            JSONArray wordsJSON = (JSONArray) jsonObject.get("words");
            for (int i = 0; i < wordsJSON.size(); i++) {
            }
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
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return cInfo;
    }


//	public static ArrayList<Word> getWordList() {
//		ArrayList<Word> wordsList = new ArrayList<Word>();
//		try {
//			FileReader reader = new FileReader(myFile);
//			JSONParser jsonParser = new JSONParser();
//			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//			JSONArray wordsJSON = (JSONArray) jsonObject.get("words");
//			for (int i = 0; i < wordsJSON.size(); i++) {
//			}
//			Iterator i = wordsJSON.iterator();
//			while (i.hasNext()) {
//				JSONArray innerObj = (JSONArray) i.next();
//				Word tempWord = new Word((Long) innerObj.get(0),
//						(Long) innerObj.get(1), (Long) innerObj.get(2),
//						(String) innerObj.get(3), (String) innerObj.get(4));
//				wordsList.add(tempWord);
//			}
//		} catch (FileNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} catch (ParseException ex) {
//			ex.printStackTrace();
//		} catch (NullPointerException ex) {
//			ex.printStackTrace();
//		}
//		return wordsList;
//	}

}
