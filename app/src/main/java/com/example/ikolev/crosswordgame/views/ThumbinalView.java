package com.example.ikolev.crosswordgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import main.Box;
import main.UtilityBox;

import main.Crossword;

public class ThumbinalView extends View {
    Paint paint = new Paint();
    static Box[][] boxes;
    private Crossword crossword;
    private float boxSize;
    private int size;


    public ThumbinalView(Context context) {
        this(context, null);
    }

    public ThumbinalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCrossword(Crossword crossword) {
        this.crossword = crossword;
        this.boxes = UtilityBox.getArrayOfBoxes(crossword);
        requestLayout();
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
            paint.setColor(Color.rgb(0, 0, 102));
            canvas.drawRect(0, 0, crossword.getWidth() * boxSize, crossword.getHeight() * boxSize, paint);
//            paint.setColor(Color.BLACK);
//            canvas.drawRect(0, 0, crossword.getWidth() * boxSize, crossword.getHeight() * boxSize, paint);
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
                        canvas.drawRect((int) (j * boxSize + boxSize / 6), (int) (i * boxSize + boxSize / 6), (int) ((j + 1) * boxSize - boxSize / 6), (int) ((i + 1) * boxSize - boxSize / 6), paint);
                    }

                }
            }
        }
    }

    public void updateCrosswordView() {
        invalidate();
    }

}