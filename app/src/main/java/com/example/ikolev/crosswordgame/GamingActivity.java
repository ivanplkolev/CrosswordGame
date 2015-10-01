package com.example.ikolev.crosswordgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Vector;

import main.Box;
import main.Crossword;
import main.UtilityBox;
import main.Word;

public class GamingActivity extends AppCompatActivity {
    private PagerAdapter pagerAdapter;
    private  ViewPager pager;
    private Crossword crossword;
    private List<Word> allWords;
    private static Box[][] boxes;
    private int[][] coord;
    private String crosswordFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaming_activity_layout);
        Intent intent = getIntent();
        crossword = (Crossword) intent.getSerializableExtra(GameChooserActivity.CROSSWORD_OBJECT);
        crosswordFileName = intent.getStringExtra(GameChooserActivity.CROSSWORD_FILE_NAME);
        this.allWords = crossword.getWords();
        setBoxes();
        initialisePaging();
        showKeyboard();
    }

    private void setBoxes() {
        Box[][] savedBoxes = (Box[][]) LocalPersistence.readObjectFromFile(this, crosswordFileName);
        if (savedBoxes != null) {
            this.boxes = savedBoxes;
        } else {
            this.boxes = UtilityBox.getArrayOfBoxes(crossword);
        }
    }

    public Crossword getCrossword() {
        return this.crossword;
    }

    public List<Word> getAllWords() {
        return this.allWords;
    }

    public static Box[][] getBoxes() {
        return boxes;
    }

    private void initialisePaging() {
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, StandartViewFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ListViewFragment.class.getName()));
        pagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), fragments);
         pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                clearSelection();
                invalidateOptionsMenu();
            }
        });
    }

    public void Verify() {
        boolean thereaAreIncorrectWords = false;
        for (int i = 0; i < crossword.getHeight(); i++) {
            for (int j = 0; j < crossword.getWidth(); j++) {
                if (!boxes[i][j].getIsCorrect()) {
                    thereaAreIncorrectWords = true;
                }
            }
        }
        if (thereaAreIncorrectWords) {
            for (int i = 0; i < crossword.getHeight(); i++) {
                for (int j = 0; j < crossword.getWidth(); j++) {
                    boxes[i][j].setIsCorrect(true);
                }
            }
        } else {
            boolean theCrosswordIsCorrect = true;
            for (int i = 0; i < crossword.getHeight(); i++) {
                for (int j = 0; j < crossword.getWidth(); j++) {
                    if (boxes[i][j].getIsActive()) {
                        if (!boxes[i][j].getWrittenChar().equals(boxes[i][j].getAnswerChar())) {
                            boxes[i][j].setIsCorrect(false);
                            theCrosswordIsCorrect = false;
                        }
                    }
                }
            }
            if (theCrosswordIsCorrect) {
                ((StandartViewFragment) pagerAdapter.getItem(1)).setTextView("Congratulations You are ready for the next level !!!");
            }
        }
        ((StandartViewFragment) pagerAdapter.getItem(0)).updateView();
    }

    public void Clear() {
        for (int i = 0; i < crossword.getHeight(); i++) {
            for (int j = 0; j < crossword.getWidth(); j++) {
                boxes[i][j].setWrittenChar("");
                boxes[i][j].setIsPressed(0);
                boxes[i][j].setIsSelected(false);
            }
        }
        ((StandartViewFragment) pagerAdapter.getItem(0)).updateView();
        ((ListViewFragment) pagerAdapter.getItem(1)).updateView();
    }

    public void clearSelection() {
        for (int i = 0; i < crossword.getHeight(); i++) {
            for (int j = 0; j < crossword.getWidth(); j++) {
                boxes[i][j].setIsPressed(0);
                boxes[i][j].setIsSelected(false);
            }
        }
        ((StandartViewFragment) pagerAdapter.getItem(0)).updateView();
        ((ListViewFragment) pagerAdapter.getItem(1)).updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gaming_activity, menu);
        menu.findItem(R.id.action_change_fragment).setTitle((pager.getCurrentItem() == 0) ? "List" : "Standart");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_fragment:
                pager.setCurrentItem((pager.getCurrentItem() + 1)%2);
                return true;
            case R.id.action_clear:
                Clear();
                return true;
            case R.id.action_verify:
                Verify();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearSelection();
            showKeyboard();
        } else {
            String result = CustomCyrilicKeyboard.getChar(keyCode);
            sendKeyEvent(result);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void sendKeyEvent(String result) {
        int nowX = -1;
        int nowY = -1;
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[0].length; j++) {
                if (boxes[i][j].getisPressed() > 0) {
                    nowX = j;
                    nowY = i;
                }
            }
        }
        if (nowX != -1 && nowY != -1) {
            boxes[nowY][nowX].setWrittenChar(result);
            boxes[nowY][nowX].setIsPressed(0);
            if (coord != null) {
                int nextX = -1;
                int nextY = -1;
                for (int k = 0; k < coord.length - 1; k++) {
                    if (coord[k][0] == nowY && coord[k][1] == nowX) {
                        nextX = coord[k + 1][1];
                        nextY = coord[k + 1][0];
                    }
                }
                if (nextX != -1 && nextY != -1) {
                    boxes[nextY][nextX].setIsPressed(1);
                }
            }
        }
        ((StandartViewFragment) pagerAdapter.getItem(0)).updateView();
        ((ListViewFragment) pagerAdapter.getItem(1)).updateView();
    }

    public void setSelectedWord(int[][] coord) {
        this.coord = coord;
    }

    public void cyrilicTouch(View v) {
        String result = CustomCyrilicKeyboard.getChar(v.getId());
        sendKeyEvent(result);
    }

    public void showKeyboard() {
        LinearLayout keyboard = (LinearLayout) findViewById(R.id.custom_keyboard);
        if (!crossword.getLanguage().equals("bg_BG")) {
            // show cyrilic keyboard
            keyboard.setVisibility(View.GONE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @Override
    protected void onPause() {
        clearSelection();
        LocalPersistence.witeObjectToFile(this, boxes, crosswordFileName);
        super.onPause();
    }

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();
                if (position < -1) {
                    view.setAlpha(0);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                } else {
                    view.setAlpha(0);
                }
            }
        }

}
