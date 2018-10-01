package com.hfad.fullversionfood;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.SparseBooleanArray;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientListFragment extends ListFragment {
    //Переменные
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    int [] prompt;
    int [] idIngredient;
    String[] names;
    int [] SELECTED_IN_ID;
    public static  int [] SELECTED_INGREDIENTS  = new int[Ingredient.ingredients.length];
    public static long INGREDIENT_PARENT_ID;
    int inkrementInArray;

    //Интерфейс, передающий FavoriteFragment выбранные и снятые с выбора пункты
    interface InterfaceIngredients{
        void interfaceThree(int [] ingredients, int [] removeFromSelected);
    }
    InterfaceIngredients interfaceIngredients;
    public void setInterfaceIngredients (InterfaceIngredients interfaceIngredients){
        this.interfaceIngredients = interfaceIngredients;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {   setRetainInstance(true);
            final View rootView =
                    inflater.inflate(R.layout.fragment_list_ingredient, container, false);
            return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Синхронизация id с базой данных
        INGREDIENT_PARENT_ID++;
        String query1 = "SELECT COUNT(*) FROM ingredient WHERE parentid = '" + INGREDIENT_PARENT_ID + "'";
        String query2 = "SELECT * FROM ingredient WHERE parentid = '" + INGREDIENT_PARENT_ID + "'";
        mDBHelper = new DatabaseHelper(getActivity().getApplicationContext());

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
        //Запрос на получение размерности массива с ingredient
        long rowCount = DatabaseUtils.longForQuery(mDb, query1, null);
        int dimension = (int) rowCount;
        inkrementInArray = (int) INGREDIENT_PARENT_ID;

        names = new String[dimension];
        idIngredient = new int[dimension];
        prompt = new int[dimension];
        int [] clicked =new int [dimension];
        //Заполнение
                int o = 0;
                Cursor cursor = mDb.rawQuery(query2, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    names [o] = cursor.getString(1);
                    idIngredient[o] = cursor.getInt(0);
                    cursor.moveToNext();
                   o++;
                }
                cursor.close();

        //Выделение ранее выделенных
        for (int m = 0; m < idIngredient.length; m ++){
            for (int p = 0; p < SELECTED_INGREDIENTS.length; p++){
                if (idIngredient[m] == SELECTED_INGREDIENTS[p]){
                clicked[m] = 99;
                break;
                }
            }
        }

        //Размерность для следующего цикла
        int inkrement2 = 0;
        for (int i=0; i < dimension; i++){
            for (int n=0; n < SELECTED_INGREDIENTS.length; n++){
                if (idIngredient[i] == SELECTED_INGREDIENTS[n]){
                    inkrement2 ++; break;
                }
            }
        }
        int p = 0;
        SELECTED_IN_ID = new int [inkrement2];
        //Снятые с выбора пункты
        for (int i=0; i < dimension; i++){
            for (int n=0; n < SELECTED_INGREDIENTS.length; n++){
                if (idIngredient[i] == SELECTED_INGREDIENTS[n]){
                    SELECTED_IN_ID [p] = idIngredient[i];
                    p++; break;
                }
            }
        }

           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                   android.R.layout.simple_list_item_multiple_choice,names);
        setListAdapter(arrayAdapter);

        //Заполнение выбранных пунктов
        for (int i =0; i < clicked.length; i++){if (clicked[i]==99) {
            getListView().performItemClick(getListView().getAdapter().getView(i, null, null), i,
                    getListView().getAdapter().getItemId(i));
        } }}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onStop() {
        super.onStop();
        int k = 0;
        int count = getListView().getCount();
        SparseBooleanArray sparseBooleanArray = getListView()
                .getCheckedItemPositions();
        //Запись выбранных в массив
        for (int i = 0; i < count; i++) {
            if (sparseBooleanArray.get(i)) {
                prompt[k]= idIngredient[i];
                k++;
            }
        }

        int [] doNotMatch = new int [SELECTED_IN_ID.length];
        int l = 0;
        //Определение снятых с выбора пунктов
        for (int i = 0; i < SELECTED_IN_ID.length; i++){
            for (int m = 0; m < prompt.length; m++ ){
                if (SELECTED_IN_ID[i] == prompt[m]){break;}
                if ((SELECTED_IN_ID[i]!= prompt[m])&(m == prompt.length-1)){
                    doNotMatch [l] = SELECTED_IN_ID [i];
                    l++;
                }
            }
        }

        //Отправка массива выбранных и снятых с выбора в FavoriteFragment
        interfaceIngredients.interfaceThree(prompt,doNotMatch);
    }
}
