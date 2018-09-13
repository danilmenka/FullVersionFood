package com.hfad.fullversionfood;

import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngrFragment extends ListFragment {
    //Интерфейс, запускающий IngredientListFragment
   interface IngredientListListener{
        void itemClicked(long id);
    }
    String[] names;
    IngredientListListener listener;
    public void setListener (IngredientListListener ingredientListListener){
        this.listener = ingredientListListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        names = new String[Ingredient.parents.length];
        for (int i=0; i<names.length; i++){
            names[i] = Ingredient.parents[i];
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1,names);
        setListAdapter(arrayAdapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        if (listener != null){
            listener.itemClicked(id);
        }
    }


}
