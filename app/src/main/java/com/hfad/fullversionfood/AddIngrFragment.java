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

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngrFragment extends ListFragment {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    //Интерфейс, запускающий IngredientListFragment
   interface IngredientListListener{
        void itemClicked(long id);
    }

    IngredientListListener listener;
    public void setListener (IngredientListListener ingredientListListener){
        this.listener = ingredientListListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        long rowCount = DatabaseUtils.longForQuery(mDb, "SELECT COUNT(*) FROM parents", null);
        int i = 0;
        int n = (int) rowCount;
        Cursor cursor = mDb.rawQuery("SELECT * FROM parents", null);
        cursor.moveToFirst();
        String[] names = new String[n];
        while (!cursor.isAfterLast()) {
            names [i] = cursor.getString(1);
            cursor.moveToNext();
            i++;
        }
        cursor.close();

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
