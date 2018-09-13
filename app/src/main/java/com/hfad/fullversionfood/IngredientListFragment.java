package com.hfad.fullversionfood;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.SparseBooleanArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientListFragment extends ListFragment {
    //Переменные
    int [] prompt;
    int [] idIngredient;
    String[] names;
    int [] SELECTED_IN_ID;
    public static  int [] SELECTED_INGREDIENTS  = new int[Ingredient.ingredients.length];
    public static long INGREDIENT_PARENT_ID;
    int test;

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
                             Bundle savedInstanceState) {
            final View rootView =
                    inflater.inflate(R.layout.fragment_list_ingredient, container, false);
            return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        int inkrement=0;
        test = (int) INGREDIENT_PARENT_ID;
        for (int i=0; i<Ingredient.ingredients.length; i++){
            if(Ingredient.parents[test] == Ingredient.ingredients[i].getParent()){
                inkrement++;}
        }
        int k = 0;

        names = new String[inkrement];
        idIngredient = new int[inkrement];
        prompt = new int[inkrement];
        int [] clicked =new int [inkrement];
        //Заполнение, выделелние выбранных ранее
        for (int i=0; i<Ingredient.ingredients.length; i++){
            if(Ingredient.parents[test] == Ingredient.ingredients[i].getParent()){
                names[k] = Ingredient.ingredients[i].getName();
                idIngredient[k] = Ingredient.ingredients[i].getId();
                for (int z = 0; z < SELECTED_INGREDIENTS.length; z++){
                    if (idIngredient[k] == SELECTED_INGREDIENTS[z]){
                    clicked[k] = 99;
                    break;
                    }
                }
                k++;
            }
        }
        //Размерность для следующего цикла
        int inkrement2 = 0;
        for (int i=0; i < inkrement; i++){
            for (int n=0; n < SELECTED_INGREDIENTS.length; n++){
                if (idIngredient[i] == SELECTED_INGREDIENTS[n]){
                    inkrement2 ++; break;
                }
            }
        }
        int p = 0;
        SELECTED_IN_ID = new int [inkrement2];
        //Снятые с выбора пункты
        for (int i=0; i < inkrement; i++){
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
