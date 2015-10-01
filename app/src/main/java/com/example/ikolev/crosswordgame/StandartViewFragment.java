package com.example.ikolev.crosswordgame;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ikolev.crosswordgame.views.StandartView;

import main.Box;
import main.Crossword;
import main.Word;


public class StandartViewFragment extends Fragment {

    private Crossword crossword;
    private Box[][] boxes;
    private StandartView standartView;
    private TextView textView;
    private static final String SERIALIZABLE_KEY = "key_boxes";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.standart_view_fragment, container, false);
        this.boxes = ((GamingActivity) this.getActivity()).getBoxes();
        this.crossword = ((GamingActivity) this.getActivity()).getCrossword();
        this.standartView = (StandartView) view.findViewById(R.id.crosswordView);
        standartView.setBoxes(boxes);
        standartView.setCrossword(crossword);
        textView = (TextView) view.findViewById(R.id.textView2);
        standartView.setOnSelectionChangedListener(new StandartView.OnSelectionChangedListener() {

            @Override
            public void onSelectionChanged(Word word) {
                textView.setText(word.getHint());
                setSelectedWord(word.getCoordinates());
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(SERIALIZABLE_KEY, boxes);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateView() {
        this.standartView.updateCrosswordView();
    }

    public void setSelectedWord(int[][] coord) {
        ((GamingActivity) this.getActivity()).setSelectedWord(coord);
    }

    public void setTextView(String text){
        textView.setText(text);
    }
}