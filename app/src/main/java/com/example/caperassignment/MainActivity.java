package com.example.caperassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

               Fragment mFragment = null;
               mFragment = new ShopFragment();
               FragmentManager fragmentManager = getSupportFragmentManager();
               fragmentManager.beginTransaction()
                       .replace(R.id.frament1, mFragment).commit();







    }




}