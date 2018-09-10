package com.hfad.fullversionfood;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements IngredientListFragment.InterfaceIngredients{
    IngredientListFragment ingredientListFragment;
    TextView ingredientText;
    String ala = "";

    @Override
    public void interfaceThree(String ingredients) {
        ala += ingredients;
    }


    interface CallBack{
        void callingBack();
    }
    CallBack callBack;
    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =
                inflater.inflate(R.layout.fragment_favorite, container, false);
        Button button = (Button) rootView.findViewById(R.id.button3);
        ingredientText = (TextView)rootView.findViewById(R.id.IngredientsInFavourite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.callingBack();
            }
        });
        ingredientText.setText(ala);
        return rootView;
    }

}
