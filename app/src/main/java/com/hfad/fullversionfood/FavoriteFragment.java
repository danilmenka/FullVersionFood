package com.hfad.fullversionfood;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements IngredientListFragment.InterfaceIngredients{





    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;











    //Переменные
    TextView ingredientText;
    public String str = ""; //Строка выбранных элементов
    int [] ala= new int[Ingredient.ingredients.length]; //Массив выбранных элементов

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


          mDBHelper = new DatabaseHelper(rootView.getContext());

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

        Button button = (Button) rootView.findViewById(R.id.button3);
        ingredientText = (TextView)rootView.findViewById(R.id.IngredientsInFavourite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                String product = "";

                Cursor cursor = mDb.rawQuery("SELECT * FROM parents", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    product += cursor.getString(1) + " | ";
                    cursor.moveToNext();
                }
                cursor.close();

                ingredientText.setText(product);
*/
                // Запускаем AddIngrFragment
                callBack.callingBack(ala);
            }
        });

        str = "";
        //Преобразуем массив в строку
        str = getStringFromArray(ala,str);
        ingredientText.setText(str);

        return rootView;
    }
    public int [] toFillingAnArrayOfSelectedItems (int [] newIngredients,int [] fullArray){
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

    public int [] toRemoveFromFullArray (int [] fullArray, int [] remotePoints ){
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

    public String getStringFromArray (int [] array,String string){
        for (int i = 0; i < array.length; i++){
            if(array[i] != 0){
                string += (array[i] + "\n");
            }
        }
        return string;
    }

}
