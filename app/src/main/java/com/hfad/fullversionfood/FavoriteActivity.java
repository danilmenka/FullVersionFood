package com.hfad.fullversionfood;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FavoriteActivity extends Activity implements FavoriteFragment.CallBack,AddIngrFragment.IngredientListListener
       {
    FavoriteFragment  favoriteFragment;
    AddIngrFragment addIngrFragment;
    FragmentTransaction trans;
    IngredientListFragment ingredientListFragment;
    int [] choosedIngredients= new int[Ingredient.ingredients.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favoriteFragment = new FavoriteFragment();
        addIngrFragment = new AddIngrFragment();
        ingredientListFragment = new IngredientListFragment();
        trans = getFragmentManager().beginTransaction();
        trans.add(R.id.frgmCont, favoriteFragment);
        trans.commit();
        favoriteFragment.setCallBack(this);
        addIngrFragment.setListener(this);
        ingredientListFragment.setInterfaceIngredients(favoriteFragment);
      }

    @Override
    public void callingBack(int [] selectedItems1) {
        choosedIngredients = selectedItems1;
        trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.frgmCont, addIngrFragment);
        trans.addToBackStack(null);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        trans.commit();

            }

    @Override
    public void itemClicked(long id) {
        IngredientListFragment.SELECTED_INGREDIENTS = choosedIngredients;
        IngredientListFragment.INGREDIENT_PARENT_ID = id;
        trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.frgmCont, ingredientListFragment);
        trans.addToBackStack(null);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        trans.commit();
    }

}

