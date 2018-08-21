package com.hfad.fullversionfood;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FavoriteActivity extends Activity{
    FavoriteFragment  frag1;
    AddIngrFragment frag2;
    FragmentTransaction trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        frag1 = new FavoriteFragment();
        frag2 = new AddIngrFragment();
        trans = getFragmentManager().beginTransaction();
        trans.add(R.id.frgmCont, frag1);
        trans.commit();
      }
      }

