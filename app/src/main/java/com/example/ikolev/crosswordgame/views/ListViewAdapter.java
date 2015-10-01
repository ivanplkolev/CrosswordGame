package com.example.ikolev.crosswordgame.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import main.Box;
import com.example.ikolev.crosswordgame.R;

import java.util.List;

import main.Word;

/**
 * Created by ikolev on 8/3/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Word> {

    Context context;
    int layoutResourceId;
    List<Word> words = null;
    Box[][] boxes;
    int maxLength;

    public ListViewAdapter(Context context, int layoutResourceId, List<Word> words, Box[][] boxes, int maxLength) {
        super(context, 0, words);
        this.boxes=boxes;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.words = words;
        this.maxLength=maxLength;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CrosswordRowHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new CrosswordRowHolder();
            holder.listWritten = (LineView) row.findViewById(R.id.crosswordRowView);
            holder.listHint = (TextView) row.findViewById(R.id.listHint);
            row.setTag(holder);
        } else {
            holder = (CrosswordRowHolder) row.getTag();
        }
        Word tempWord = words.get(position);
        holder.listWritten.setBoxes(boxes);
        holder.listWritten.setWord(tempWord);
        holder.listWritten.setMaxLength(maxLength);
        holder.listWritten.updateCrosswordRowView();
        holder.listHint.setText(tempWord.getHint());
        return row;
    }

    static class CrosswordRowHolder {
        LineView listWritten;
        TextView listHint;
    }


}
