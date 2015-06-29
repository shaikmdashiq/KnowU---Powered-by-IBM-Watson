package com.artqueen.knowu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.artqueen.knowu.EssentialFragments.Big5_Fragment;

import java.util.ArrayList;


public class Results_Activity extends ActionBarActivity {

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
        setContentView(R.layout.results_mainactivity);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            switch (b.getInt("src")) {
                case 0:
                    big5names = MainActivity.big5names;
                    big5percentages = MainActivity.big5percentages;
                    big5subcats = MainActivity.big5subcats;
                    big5subPcnt = MainActivity.big5subPcnt;
                    valuesnames = MainActivity.valuesnames;
                    valuespercentages = MainActivity.valuespercentages;
                    needsnames = MainActivity.needsnames;
                    needspercentages = MainActivity.needspercentages;
                    break;

                default:
                    break;
            }
        }

        Big5_Fragment newFragment = new Big5_Fragment();
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer_results, newFragment);
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


