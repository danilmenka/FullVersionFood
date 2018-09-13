package com.hfad.fullversionfood;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class FavoriteActivity extends Activity implements FavoriteFragment.CallBack,AddIngrFragment.IngredientListListener {
    //Переменные:
    FavoriteFragment  favoriteFragment;
    AddIngrFragment addIngrFragment;
    FragmentTransaction trans;
    IngredientListFragment ingredientListFragment;
    int [] choosedIngredients= new int[Ingredient.ingredients.length]; //Массив выбранных элементов

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
    //Интерфейс для запуска AddIngrFragment
    public void callingBack(int [] selectedItems1) {
        choosedIngredients = selectedItems1;
        trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.frgmCont, addIngrFragment);
        trans.addToBackStack(null);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        trans.commit();
    }

    //Интерфейс для запуска IngredientListFragment
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