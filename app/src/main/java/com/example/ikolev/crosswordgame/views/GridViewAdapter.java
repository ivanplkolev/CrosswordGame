package com.example.ikolev.crosswordgame.views;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ikolev.crosswordgame.GameChooserActivity;
import com.example.ikolev.crosswordgame.R;

import java.util.List;

import main.Crossword;

public class GridViewAdapter extends BaseAdapter{

    Context context;
    private static LayoutInflater inflater=null;
    List<Crossword> crosswords;


    public GridViewAdapter(GameChooserActivity gameChooserActivity, List<Crossword> crosswords) {
        // TODO Auto-generated constructor stub
        context= gameChooserActivity;
        this.crosswords=crosswords;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return crosswords.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv1;
        ThumbinalView crossThumb;
        TextView tv2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.game_thumbinal_view_layout, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.textView1);
        holder.tv2=(TextView) rowView.findViewById(R.id.textView2);
        holder.crossThumb=(ThumbinalView) rowView.findViewById(R.id.crosswordRowView);

        holder.tv2.setText("Crossword " + (position+1));
        holder.tv1.setText(crosswords.get(position).toString());
        holder.crossThumb.setCrossword(crosswords.get(position));

        return rowView;
    }

}