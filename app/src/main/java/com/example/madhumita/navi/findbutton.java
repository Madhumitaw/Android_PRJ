package com.example.madhumita.navi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * find the cafe list
 */
public class findbutton extends Fragment {

    EditText search_name;
    Button seacrh;
    public findbutton() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
       view= inflater.inflate(R.layout.fragment_findbutton, container, false);
        search_name=(EditText)view.findViewById(R.id.cafe);
        seacrh=(Button)view.findViewById(R.id.searchbutton);

        return view;
    }

}
