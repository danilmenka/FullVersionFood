package com.hfad.fullversionfood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddKitchenFragment extends Fragment {


    public AddKitchenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView =
                inflater.inflate(R.layout.fragment_add_kitchen, container, false);
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();

    }
}
