package com.example.ikolev.crosswordgame;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.ikolev.crosswordgame.views.GridViewAdapter;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.Crossword;
import main.JSONManipulatingClass;

/**
 * Created by ikolev on 8/11/2015.
 */
public class GameChooserActivity extends AppCompatActivity {

    public final static String CROSSWORD_FILE_NAME = "crossword file name";
    public final static String CROSSWORD_OBJECT = "crossword object";
    private GridView thumbinalView;
    private String[] files;
    private final List<Crossword> crosswords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_chooser_activity_layout);
        AssetManager assetManager = getAssets();
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String file : files) {
            Crossword tempCross = null;
            try {
                InputStream stream = getAssets().open(file);
                tempCross = JSONManipulatingClass.parseJsonCrossword(new InputStreamReader(stream));
            } catch (IndexOutOfBoundsException e) {
                System.err.println("IndexOutOfBoundsException: " + e.getMessage());
            } catch (ParseException e) {
                System.err.println("ParseException: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
            if (tempCross != null) {
                crosswords.add(tempCross);
            }
        }
        GridViewAdapter adapter = new GridViewAdapter(this, crosswords);
        thumbinalView = (GridView) findViewById(R.id.gridView1);
        thumbinalView.setAdapter(adapter);
        thumbinalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                startCrossword(i);
            }
        });
    }

    private void startCrossword(int i) {
        Intent intent = new Intent(this, GamingActivity.class);
        intent.putExtra(CROSSWORD_OBJECT, crosswords.get(i));
        intent.putExtra(CROSSWORD_FILE_NAME, files[i]);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game_chooser_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}




