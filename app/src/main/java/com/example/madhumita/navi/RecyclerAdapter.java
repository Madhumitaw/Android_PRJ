package com.example.madhumita.navi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by madhumita on 7/10/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerviewHolder> {

    String[] ratings,names,vici;
    public RecyclerAdapter(String[] rating, String[] name,String[] vicinity)
    {
        this.ratings=rating;
        this.names=name;
        this.vici=vicinity;
    }
    @Override
    public RecyclerviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
RecyclerviewHolder recyclerviewHolder=new RecyclerviewHolder(v);
        return recyclerviewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerviewHolder holder, int position) {

        holder.txt1.setText(ratings[position]);
        holder.txt2.setText(names[position]);
       // Log.e("vici[position]",""+vici[position]);
        holder.txt3.setText(vici[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }


    public static class RecyclerviewHolder extends RecyclerView.ViewHolder{
        CardView cv;
TextView txt1,txt2,txt3;
        public RecyclerviewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            txt1=(TextView)itemView.findViewById(R.id.txt1);
            txt2=(TextView)itemView.findViewById(R.id.txt2);
            txt3=(TextView)itemView.findViewById(R.id.txt3);
        }
    }
}
