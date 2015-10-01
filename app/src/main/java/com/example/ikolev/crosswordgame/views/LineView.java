package com.example.ikolev.crosswordgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.ikolev.crosswordgame.GamingActivity;
import main.Box;
import main.Word;


public class LineView extends View {
    private Paint paint = new Paint();
    private float boxSize;
    private Word word;
    private Box[][] boxes;
    private int maxlength;


    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void setBoxes(Box[][] boxes) {
        this.boxes = boxes;
    }

    public void setMaxLength(int maxLength) {
        this.maxlength = maxLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.boxSize = Math.min(getHeight(), (int) getWidth() / maxlength);
        int textSize = (int) (0.85 * boxSize);

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, this.word.getLength() * boxSize, boxSize, paint);

        int[][] coord = word.getCoordinates();
        for (int k = 0; k < word.getLength(); k++) {
            // Show the incorrect words
            if (!GamingActivity.getBoxes()[coord[k][0]][coord[k][1]].getIsCorrect()) {
                paint.setColor(Color.RED);
                canvas.drawRect((int) k * boxSize + 1, 1, (int) (k + 1) * boxSize - 1, boxSize - 1, paint);

                // Shows the pressed box
            } else if (GamingActivity.getBoxes()[coord[k][0]][coord[k][1]].getisPressed() != 0) {
                paint.setColor(Color.rgb(219, 142, 0));
                canvas.drawRect((int) k * boxSize + 1, 1, (int) (k + 1) * boxSize - 1, boxSize - 1, paint);

                // shows all active boxes
            } else {
                paint.setColor(Color.WHITE);
                canvas.drawRect((int) k * boxSize + 1, 1, (int) (k + 1) * boxSize - 1, boxSize - 1, paint);
            }
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        for (int k = 0; k < word.getLength(); k++) {
            if (boxes[coord[k][0]][coord[k][1]].getWrittenChar() != null) {
                canvas.drawText(boxes[coord[k][0]][coord[k][1]].getWrittenChar() + "", k * boxSize + boxSize / 2, (float) ((boxSize / 2) + (textSize * 0.28)), paint);
            }
        }
    }

    public void updateCrosswordRowView() {
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int[][] coord = word.getCoordinates();
            int touchedX = (int) event.getX();
            int k = (int) (touchedX / boxSize);

            // chech if touch is over the crossword
            if (k < word.getLength()) {

                // Clean all pressed boxes, because we have new pressed box
                for (int m = 0; m < boxes.length; m++) {
                    for (int n = 0; n < boxes[0].length; n++) {
                        boxes[m][n].setIsPressed(0);
                    }
                }

                // Marks the pressed box
                boxes[coord[k][0]][coord[k][1]].incrementIsPressed();
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }


}
