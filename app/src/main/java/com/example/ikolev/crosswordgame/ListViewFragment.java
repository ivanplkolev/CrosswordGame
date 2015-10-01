package com.example.ikolev.crosswordgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ikolev.crosswordgame.views.ListViewAdapter;

import java.util.List;

import main.Box;
import main.Word;


public class ListViewFragment extends Fragment {
    private ListView listView1;
    private List<Word> words;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment_layout, container, false);
        words = ((GamingActivity) this.getActivity()).getAllWords();
        Box[][] boxes = ((GamingActivity) this.getActivity()).getBoxes();
        int maxLengh = 0;
        for (Word tempWord : words) {
            maxLengh = Math.max(maxLengh, tempWord.getLength());
        }
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), R.layout.word_row_view_layout, words, boxes, maxLengh);
        listView1 = (ListView) view.findViewById(R.id.CrosswordList);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ((ListViewAdapter) listView1.getAdapter()).notifyDataSetChanged();
                setSelectedWordPosition(position);
                showKeyboard();
            }
        });
        return view;
    }

    public void setSelectedWordPosition(int position) {
        Word selctedWord = words.get(position);
        ((GamingActivity) this.getActivity()).setSelectedWord(selctedWord.getCoordinates());
    }

    public void updateView() {
        this.listView1.invalidate();
        ((ListViewAdapter) listView1.getAdapter()).notifyDataSetChanged();
    }

    public void showKeyboard() {
        ((GamingActivity) this.getActivity()).showKeyboard();
    }

}