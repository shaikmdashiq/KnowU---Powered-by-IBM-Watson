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

public class SubChartFragment extends BlurDialogFragment {

    private static final String ARG_SECTION_NUMBER = "1";
    int index = 0;
    Bundle b = null;
    private PieChart mChart;

    ArrayList<String> arrayLista = new ArrayList<>();
    TextView ed1;

    public SubChartFragment() {
    }

    public static SubChartFragment newInstance(int sectionNumber) {
        SubChartFragment fragment = new SubChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = getArguments();
        index = b.getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        final Drawable d = new ColorDrawable(Color.TRANSPARENT);
        d.setAlpha(130);
        getDialog().getWindow().setBackgroundDrawable(d);

        View rooty = inflater.inflate(R.layout.fragment_sub_chart, container, false);

        ed1 = (TextView) rooty.findViewById(R.id.tv_subchart);
        RelativeLayout mainLayout = (RelativeLayout) rooty.findViewById(R.id.subChart);
        mChart = new PieChart(getActivity());
        mainLayout.addView(mChart);
        mainLayout.setBackgroundColor(Color.TRANSPARENT);

        mChart.setUsePercentValues(true);
        mChart.setDescription(b.getString("name"));
        mChart.setDescriptionTextSize(12f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        switch (index) {
            case 0:
                arrayLista.addAll(Arrays.asList(getResources().getStringArray(R.array.Openness)));
            case 1:
                arrayLista.addAll(Arrays.asList(getResources().getStringArray(R.array.Conscientiousness)));
            case 2:
                arrayLista.addAll(Arrays.asList(getResources().getStringArray(R.array.Extraversion)));
            case 3:
                arrayLista.addAll(Arrays.asList(getResources().getStringArray(R.array.Agreeableness)));
            case 4:
                arrayLista.addAll(Arrays.asList(getResources().getStringArray(R.array.Emotional_Range)));
        }

        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.animateXY(1000, 1000, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e.getVal() < 0.5) {
                    ed1.setText(arrayLista.get(e.getXIndex() + 6));
                }
                if (e.getVal() == 0.5) {
                    ed1.setText(arrayLista.get(e.getXIndex()));
                }
                if (e.getVal() > 0.5) {
                    ed1.setText(arrayLista.get(e.getXIndex()));
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTextSize(8f);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        return rooty;
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels - 40;
        int height = getResources().getDisplayMetrics().heightPixels - 120;
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

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 6.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 10;
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        // Enable or disable the dimming effect.
        // Disabled by default.
        return false;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        // Enable or disable the use of RenderScript for blurring effect
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDebugEnable() {
        // Enable or disable debug mode.
        // False by default.
        return false;
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        yVals1.clear();

        for (int i = 0; i < Results_Activity.big5subPcnt.get(index).size(); i++)
            yVals1.add(new Entry(Float.valueOf(String.valueOf(Results_Activity.big5subPcnt.get(index).get(i))), i));

        ArrayList<String> xVals = new ArrayList<>();
        xVals.clear();

        for (int i = 0; i < Results_Activity.big5subcats.get(index).size(); i++)
            xVals.add(Results_Activity.big5subcats.get(index).get(i));

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(1);
        dataSet.setSelectionShift(10);

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
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }
}
