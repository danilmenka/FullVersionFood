package com.hfad.fullversionfood;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.IOException;

public class FavoriteActivity extends Activity implements FavoriteFragment.CallBack,AddIngrFragment.IngredientListListener {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    //Переменные:
    FavoriteFragment  favoriteFragment;
    AddIngrFragment addIngrFragment;
    public static int nani;
    FragmentTransaction trans;
    IngredientListFragment ingredientListFragment;
    public static int [] choosedIngredients= new int[Ingredient.ingredients.length]; //Массив выбранных элементов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite);
        mDBHelper = new DatabaseHelper(getApplicationContext());

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        long rowCount = DatabaseUtils.longForQuery(mDb, "SELECT COUNT(*) FROM ingredient", null);
        nani = (int) rowCount;
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