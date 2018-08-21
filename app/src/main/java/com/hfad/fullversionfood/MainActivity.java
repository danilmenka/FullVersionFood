package com.hfad.fullversionfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //"Объявление переменных"
        Button buttonOnFavouriteActivity = (Button)findViewById(R.id.buttonRecipesOfRefrigerator);
        Button buttonOnReadyRecipe = (Button) findViewById(R.id.buttonReadyRecipes);

        //Отработчик нажатия на кнопку "Рецепты из имеющихся ингредиентов"
        buttonOnFavouriteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(intent);
            }
        });

        //Отработчик нажатия на кнопку "Готовые рецепты"
        buttonOnReadyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ReadyRecipe.class);
                startActivity(intent);
            }
        });

    }

}
