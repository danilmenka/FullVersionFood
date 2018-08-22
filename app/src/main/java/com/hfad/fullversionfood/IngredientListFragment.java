package com.hfad.fullversionfood;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientListFragment extends Fragment {
    public static long INGREDIENT_PARENT_ID;
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View myFragmentView = inflater.inflate(R.layout.fragment_ingredient_list,
                    container, false);
            TextView finalText = (TextView) myFragmentView.findViewById(R.id.finalText);

            long ava = 555;
            String avaS = String.valueOf(INGREDIENT_PARENT_ID);
            finalText.setText(avaS);

        // Inflate the layout for this fragment
            return myFragmentView;
    }

}
