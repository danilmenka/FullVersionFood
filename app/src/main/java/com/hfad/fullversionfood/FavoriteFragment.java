package com.hfad.fullversionfood;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements IngredientListFragment.InterfaceIngredients{
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    //Переменные
    TextView ingredientText;
    private String str = ""; //Строка выбранных элементов
    private String idList = "";
    private int [] ala = new int[FavoriteActivity.nani]; //Массив выбранных элементов
    String [] names = new String[FavoriteActivity.nani];
    //Интерфейс для запуска AddIngrFragment
    interface CallBack{
        void callingBack(int [] selectedIngredients);
    }
    CallBack callBack;
    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    // Метод интерфейса из IngredientListFragment с массивом id выбранных и удаленных из выбранных пунктов
    @Override
    public void interfaceThree(int [] ingredients, int [] removeFromSelected) {
        //Добавляем в массив выбранные элементы
        ala = toFillingAnArrayOfSelectedItems(ingredients,ala);
        //Удаляем из массива элементы, удаленные из выбранных
        ala = toRemoveFromFullArray(ala,removeFromSelected);
        FavoriteActivity.choosedIngredients = ala;
    }

      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        final View rootView =
                inflater.inflate(R.layout.fragment_favorite, container, false);
        //Подключение бд
        onDataBase();
        Button button = (Button) rootView.findViewById(R.id.button3);
        ingredientText = (TextView)rootView.findViewById(R.id.IngredientsInFavourite);
        //Заполнение текста выбранными ингридиентами
        setTextIngredients();
        Button buttonAddKitchen = (Button) rootView.findViewById(R.id.buttonAddKitchen);
        TextView textKitchen = (TextView) rootView.findViewById(R.id.textKitchen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Запускаем AddIngrFragment
                callBack.callingBack(ala);

            }
        });
        buttonAddKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }
    private SQLiteDatabase onDataBase(){
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
        return mDb;
    }
    private String [] setTextIngredients(){
        idList = null;
        int alaNotNull = 0;
        for (int i = 0; i < ala.length;i++){
            if (ala[i]!=0) alaNotNull++;
        }
        for (int i = 0; i < alaNotNull;i++){
            if (idList == null){idList = Integer.toString(ala[i]);} else {idList += ", "+ Integer.toString(ala[i]);}
        }
        String query = "SELECT * FROM ingredient WHERE _id IN ("+idList+")";

        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        int o =0;
        while (!cursor.isAfterLast()) {
            names [o] = cursor.getString(1);
            cursor.moveToNext();
            o++;
        }
        cursor.close();
        str = "";

        //Преобразуем массив в строку
        for (int i = 0; i < names.length; i++){
            if (names[i]!=null){
                str=str+" "+names[i];}}
        ingredientText.setText(str);
        for (int i = 0; i < names.length; i++){names[i] = null;}
        return names;
    }

    private int [] toFillingAnArrayOfSelectedItems (int [] newIngredients,int [] fullArray){
        boolean toggle = false;
        for (int k = 0; k < newIngredients.length; k++){
            if (newIngredients[k] !=0){
                for (int i = 0; i < fullArray.length; i++){
                    if (newIngredients[k] == fullArray[i]){
                        toggle = true;
                        break;
                    }
                }
                if (toggle == false){
                    for (int i = 0; i < fullArray.length; i++) {
                        if (fullArray[i]==0){ fullArray[i] = newIngredients[k]; break;
                        }
                    }
                }
            }
            toggle = false;
        }

        return fullArray;
    }

    private int [] toRemoveFromFullArray (int [] fullArray, int [] remotePoints ){
        int [] finishFullArray = new int[fullArray.length];
        for (int i=0; i < fullArray.length; i++){
            for (int k=0;k< remotePoints.length;k++){
                if ((fullArray[i]==remotePoints[k])&(ala[i]!=0)){
                    fullArray[i]=0; break;
                }
            }
        }
        int z = 0;
        for (int i = 0; i < fullArray.length; i++){
                if(fullArray[i]!=0){
                    finishFullArray[z] = fullArray[i];
                 z++;
                }
        }

        return finishFullArray;
    }

    public void onStop() {
        super.onStop();
    }

}
