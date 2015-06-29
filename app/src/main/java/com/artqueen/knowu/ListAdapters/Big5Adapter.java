package com.artqueen.knowu.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artqueen.knowu.R;

import java.util.ArrayList;

/**
 * Created by shaikmdashiq on 14/6/15.
 */

public class Big5Adapter extends BaseAdapter{
    private ArrayList<String> big5names = null;
    private Context context;
    TextView name;

    public Big5Adapter(Context context, int layoutResourceId, ArrayList<String> big5names) {
        this.context = context;
        this.big5names = big5names;
    }

    @Override
    public int getCount() {
        return big5names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_big5, null);
        }

        name = (TextView) view.findViewById(R.id.big5RowTV);
        name.setText(big5names.get(position));

        if (position == 0) {
            name.setBackgroundResource(R.drawable.openness_list);
        }
        if (position == 1) {
            name.setBackgroundResource(R.drawable.conscientiousness_list);
        }
        if (position == 2) {
            name.setBackgroundResource(R.drawable.extraversion_list);
        }
        if (position == 3) {
            name.setBackgroundResource(R.drawable.agreeableness_list);
        }
        if (position == 4) {
            name.setBackgroundResource(R.drawable.emotioanl_list);
        }
        if (position == 5) {
            name.setBackgroundResource(R.drawable.ls_values);
        }
        if (position == 6) {
            name.setBackgroundResource(R.drawable.ls_needs);
        }

        return view;
    }

}
