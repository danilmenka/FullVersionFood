package com.hfad.fullversionfood;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.SparseBooleanArray;
import android.widget.Toast;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientListFragment extends ListFragment {
    String prompt = "";
    int [] idIngredient;
    String[] names;
    interface InterfaceIngredients{
        void interfaceThree(String ingredients);
    }

    InterfaceIngredients interfaceIngredients;
    public void setInterfaceIngredients (InterfaceIngredients interfaceIngredients){
        this.interfaceIngredients = interfaceIngredients;
    }

    public static long INGREDIENT_PARENT_ID;
    int test;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_list_ingredient,null);


    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


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
        for (int i=0; i<Ingredient.ingredients.length; i++){
            if(Ingredient.parents[test] == Ingredient.ingredients[i].getParent()){
                names[k] = Ingredient.ingredients[i].getName();
                idIngredient[k] = Ingredient.ingredients[i].getId();
                k++;}
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice,names);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        prompt = "";
        int count = getListView().getCount();
        SparseBooleanArray sparseBooleanArray = getListView()
                .getCheckedItemPositions();
        for (int i = 0; i < count; i++) {
            if (sparseBooleanArray.get(i)) {
                prompt += idIngredient[i] + "\n";

              //  prompt += getListView().getItemAtPosition(i).toString() + "\n";

            }
        }




    }

    @Override
    public void onStop() {
        super.onStop();
        interfaceIngredients.interfaceThree(prompt);
    }
}
