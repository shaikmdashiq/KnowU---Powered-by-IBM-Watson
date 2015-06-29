package com.artqueen.knowu.EssentialFragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artqueen.knowu.R;
import com.artqueen.knowu.Results_Activity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.Arrays;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class Needs_Fragment extends BlurDialogFragment {

    private static final String ARG_SECTION_NUMBER = "3";
    private PieChart mChart;

    ArrayList<String> arrayLista = new ArrayList<>();
    TextView ed1;

    public Needs_Fragment() {
        // Required empty public constructor
    }


    public static Needs_Fragment newInstance(int sectionNumber) {
        Needs_Fragment fragment = new Needs_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooty = inflater.inflate(R.layout.fragment_needs_, container, false);
        ed1 = (TextView) rooty.findViewById(R.id.textView5);
        arrayLista.addAll(Arrays.asList(getResources().getStringArray(R.array.Needs_group)));


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        final Drawable d = new ColorDrawable(Color.TRANSPARENT);
        d.setAlpha(130);
        getDialog().getWindow().setBackgroundDrawable(d);


        RelativeLayout mainLayout = (RelativeLayout) rooty.findViewById(R.id.needsChart);
        mChart = new PieChart(getActivity());
        mainLayout.addView(mChart);
        mainLayout.setBackgroundColor(Color.TRANSPARENT);

        mChart.setUsePercentValues(true);
        mChart.setDescription("Needs");
        mChart.setDescriptionTextSize(12f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(7);
        mChart.setNoDataText("Sorry, but you have not yet submitted your data.");
        mChart.setNoDataTextDescription("You can go to Historical Records to see previous data.");

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                ed1.setText(arrayLista.get(e.getXIndex()));
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setTextSize(8f);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        mChart.animateXY(1000, 1000, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        return rooty;

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels - 30;
        int height = getResources().getDisplayMetrics().heightPixels - 80;
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //Blur factor
    @Override
    protected float getDownScaleFactor() {
        return 6.0f;
    }

    @Override
    protected int getBlurRadius() {
        return 10;
    }

    @Override
    protected boolean isActionBarBlurred() {
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        return false;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        return true;
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        yVals1.clear();

        for (int i = 0; i < Results_Activity.needspercentages.size(); i++)
            yVals1.add(new Entry(Float.valueOf(String.valueOf(Results_Activity.needspercentages.get(i))), i));

        ArrayList<String> xVals = new ArrayList<>();
        xVals.clear();

        for (int i = 0; i < Results_Activity.needsnames.size(); i++)
            xVals.add(Results_Activity.needsnames.get(i));

        PieDataSet dataSet = new PieDataSet(yVals1, "");

        dataSet.setSliceSpace(1);
        dataSet.setSelectionShift(7);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }
}
