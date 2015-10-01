package com.example.ikolev.crosswordgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import main.Box;

import main.Crossword;
import main.Word;

public class StandartView extends View {
    private Paint paint = new Paint();
    private static Box[][] boxes;
    private Crossword crossword;
    private float boxSize;
    private int size;
    private Word selectedWord;
    private Box pressedBox;
    private int alreadyPressedX;
    private int alreadyPressedY;
    private OnSelectionChangedListener onSelectionChangedListener;
    private int wordDirection = 0;


    public StandartView(Context context) {
        this(context, null);
    }

    public StandartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBoxes(Box[][] boxes) {
        this.boxes = boxes;
    }

    public void setCrossword(Crossword crossword) {
        this.crossword = crossword;
        requestLayout();
    }
    public Word getSelectedWord() {
        return selectedWord;
    }

    public void setSelectedWord(Word selectedWord) {
        this.selectedWord = selectedWord;
    }

    private boolean hasCrossword() {
        return crossword != null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasCrossword()) {
            this.size = Math.min(getWidth(), getHeight());
            this.boxSize = Math.min((int) getWidth() / crossword.getWidth(), (int) getHeight() / crossword.getHeight());
            int textSize = (int) (0.85 * boxSize);

            paint.setColor(Color.BLACK);
            canvas.drawRect(0, 0, crossword.getWidth() * boxSize, crossword.getHeight() * boxSize, paint);
            for (int i = 0; i < crossword.getHeight(); i++) {
                for (int j = 0; j < crossword.getWidth(); j++) {

                    // Show the incorrect words
                    if (!boxes[i][j].getIsCorrect()) {
                        paint.setColor(Color.RED);
                        canvas.drawRect((int) j * boxSize + 1, (int) i * boxSize + 1, (int) (j + 1) * boxSize - 1, (int) (i + 1) * boxSize - 1, paint);

                        // Shows the pressed box
                    } else if (boxes[i][j].getisPressed() != 0) {
                        paint.setColor(Color.rgb(219, 142, 0));
                        canvas.drawRect((int) j * boxSize + 1, (int) i * boxSize + 1, (int) (j + 1) * boxSize - 1, (int) (i + 1) * boxSize - 1, paint);

                        // Shows the selected boxes (the selected word)
                    } else if (boxes[i][j].getIsSelected()) {
                        paint.setColor(Color.rgb(161, 235, 246));
                        canvas.drawRect((int) j * boxSize + 1, (int) i * boxSize + 1, (int) (j + 1) * boxSize - 1, (int) (i + 1) * boxSize - 1, paint);

                        // shows all active boxes
                    } else {
                        paint.setColor(Color.WHITE);
                        canvas.drawRect((int) j * boxSize + 1, (int) i * boxSize + 1, (int) (j + 1) * boxSize - 1, (int) (i + 1) * boxSize - 1, paint);
                    }

                    // shows the inacive boxes
                    if (!boxes[i][j].getIsActive()) {
                        paint.setColor(Color.rgb(0, 51, 0));
                        canvas.drawRect((int) (j * boxSize + boxSize / 8), (int) (i * boxSize + boxSize / 8), (int) ((j + 1) * boxSize - boxSize / 8), (int) ((i + 1) * boxSize - boxSize / 8), paint);
                    }
                }
            }
            paint.setColor(Color.BLACK);
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            for (int i = 0; i < crossword.getHeight(); i++) {
                for (int j = 0; j < crossword.getWidth(); j++) {
                    if (boxes[i][j].getWrittenChar() != null) {
                        canvas.drawText(boxes[i][j].getWrittenChar() + "", j * boxSize + boxSize / 2, (float) ((i * boxSize + boxSize / 2) + (textSize * 0.28)), paint);
                    }
                }
            }
        }
    }

    public void updateCrosswordView() {
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && hasCrossword()) {
            int touchedX = (int) event.getX();
            int touchedY = (int) event.getY();
            int x = (int) (touchedX / boxSize);
            int y = (int) (touchedY / boxSize);

            // chech if touch is over the crossword
            if (x < crossword.getWidth() && y < crossword.getHeight()) {
                if (boxes[y][x].getIsActive()) {

                    // If press different box clean all isPressed
                    if (x != this.alreadyPressedX || y != this.alreadyPressedY) {
                        // Clean all pressed boxes, because we have new pressed box
                        for (int m = 0; m < crossword.getHeight(); m++) {
                            for (int n = 0; n < crossword.getWidth(); n++) {
                                boxes[m][n].setIsPressed(0);
                            }
                        }
                        pressedBox = boxes[y][x];
                        pressedBox.setX(x);
                        pressedBox.setY(y);
                        this.alreadyPressedX = x;
                        this.alreadyPressedY = y;
                    }

                    // Clean selected word
                    for (int m = 0; m < crossword.getHeight(); m++) {
                        for (int n = 0; n < crossword.getWidth(); n++) {
                            boxes[m][n].setIsSelected(false);
                        }
                    }
                    // Marks the pressed box
                    boxes[y][x].incrementIsPressed();

                    // Finds the selected word
                    if (boxes[y][x].getWordsAtBox().size() > 0) {
                        if (boxes[y][x].getWordsAtBox().size() == 1) {
                            this.selectedWord = boxes[y][x].getWordsAtBox().get(0);
                        } else {
                            if (boxes[y][x].getWordsAtBox().size() == 2) {
                                if (boxes[y][x].getisPressed() % 2 == 1) {
                                    for (Word tempWord : boxes[y][x].getWordsAtBox()) {
                                        if (tempWord.getDirection() == wordDirection) {
                                            selectedWord = tempWord;
                                        }
                                    }
                                } else {
                                    for (Word tempWord : boxes[y][x].getWordsAtBox()) {
                                        if (tempWord.getDirection() != wordDirection) {
                                            selectedWord = tempWord;
                                        }
                                    }
                                }
                            }
                        }
                        if (onSelectionChangedListener != null) {
                            onSelectionChangedListener.onSelectionChanged(this.selectedWord);
                        }
                        this.wordDirection = this.selectedWord.getDirection();
                    }

                    // Mark boxes from selected word as isSelected=true
                    if (this.selectedWord != null) {
                        if (this.selectedWord.getDirection() == Word.HORIZONTAL) {
                            for (int i = 0; i < this.selectedWord.getLength(); i++) {
                                boxes[selectedWord.getStartY()][selectedWord.getStartX() + i].setIsSelected(true);
                            }
                        } else {
                            for (int i = 0; i < this.selectedWord.getLength(); i++) {
                                boxes[selectedWord.getStartY() + i][selectedWord.getStartX()].setIsSelected(true);
                            }
                        }
                    }
                    invalidate();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int availWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int availHeight = MeasureSpec.getSize(widthMeasureSpec);

        int cwWidth = crossword == null ? 1 : crossword.getWidth();
        int cwHeight = crossword == null ? 1 : crossword.getHeight();

        int boxSize = Math.min((int) availWidth / cwWidth, (int) availHeight / cwHeight);
        int width = 0;
        switch (widthSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                width = boxSize * cwWidth;
                break;
            case MeasureSpec.EXACTLY:
                width = availWidth;
                break;
        }
        int height = 0;
        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                height = boxSize * cwHeight;
                break;
            case MeasureSpec.EXACTLY:
                height = availHeight;
                break;
        }

        Log.i("Crossword", "Measured width " + width + " // " + height);
        setMeasuredDimension(width, height);
    }

    public void setOnSelectionChangedListener(OnSelectionChangedListener l) {
        this.onSelectionChangedListener = l;
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(Word word);
    }

}
