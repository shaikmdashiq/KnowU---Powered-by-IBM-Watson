package com.artqueen.knowu;

import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.artqueen.knowu.Analysis.Analysis_startpage;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static ArrayList<String> big5names = new ArrayList<>();
    public static ArrayList<Double> big5percentages = new ArrayList<>();
    public static ArrayList<ArrayList<String>> big5subcats = new ArrayList<>();
    public static ArrayList<ArrayList<Double>> big5subPcnt = new ArrayList<>();
    public static ArrayList<String> valuesnames = new ArrayList<>();
    public static ArrayList<Double> valuespercentages = new ArrayList<>();
    public static ArrayList<String> needsnames = new ArrayList<>();
    public static ArrayList<Double> needspercentages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Analysis_startpage newFragment = new Analysis_startpage();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.startcontainer, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }
}


