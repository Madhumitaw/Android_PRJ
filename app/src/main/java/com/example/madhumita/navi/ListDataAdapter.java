package com.example.madhumita.navi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madhumita on 7/18/2016.
 */
public class ListDataAdapter extends ArrayAdapter {

    List list=new ArrayList();
    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }
    static class Layouthandler{
        TextView cust,name,item,qty;

    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        Layouthandler layouthandler;
        if(row==null)
        {LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row, parent, false);
            layouthandler = new Layouthandler();
            layouthandler.cust= (TextView) row.findViewById(R.id.disp1);
            layouthandler.name= (TextView) row.findViewById(R.id.disp2);

            row.setTag(layouthandler);
        }
        else {
            layouthandler = (Layouthandler) row.getTag();

        }
        Dataprovider dp=(Dataprovider)this.getItem(position);
        layouthandler.name.setText(dp.getname1());
        layouthandler.cust.setText(dp.getcust());


        return row;
    }
}


