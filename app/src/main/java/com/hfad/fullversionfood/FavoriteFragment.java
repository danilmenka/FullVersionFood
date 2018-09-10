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

import java.util.Arrays;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements IngredientListFragment.InterfaceIngredients{
    TextView ingredientText;
    public String str = "";
    int [] ala= new int[Ingredient.ingredients.length];

    @Override
    public void interfaceThree(int [] ingredients) {
        boolean boosy = false;
        for (int k = 0; k < ingredients.length; k++){
            if (ingredients[k] !=0){
                 for (int i = 0; i < ala.length; i++){
                     if (ingredients[k] == ala[i]){
                        boosy = true;
                        break;
                     }
                 }
                        if (boosy == false){
                            for (int i = 0; i < ala.length; i++) {
                                if (ala[i]==0){ ala[i] = ingredients[k]; break;
                                }
                             }
                        }
            }
                 boosy = false;
        }
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
            str = "";
          for (int i = 0; i < ala.length; i++){
              if(ala[i] != 0){
                  str += (ala[i] + "\n");
              }
          }
        ingredientText.setText(str);

        return rootView;
    }

}
