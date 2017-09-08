package com.algorepublic.communicator.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.algorepublic.communicator.Communicator;
import com.algorepublic.communicator.R;
import com.algorepublic.communicator.helper.HourAxisValueFormatter;
import com.algorepublic.communicator.model.ActListModel;
import com.algorepublic.communicator.model.ActSchedule2Model;
import com.algorepublic.communicator.model.ActTypeListModel;
import com.algorepublic.communicator.model.BarDataValue;
import com.algorepublic.communicator.utils.Constants;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by adilnazir on 18/08/2017.
 */

public class ActSchedule2Fragment extends BaseFragment {

    public static ActSchedule2Fragment instance;
    public View view;

    @BindView(R.id.chart)
    LinearLayout chart;

    @BindView(R.id.mChart)
    HorizontalBarChart mChart;


    public Unbinder unbinder;
    float groupSpace = 0.06f;

    float barSpace = 0.02f;
    Handler handler = new Handler();
    long refernceTime = 0;
    int barColorSize = 0;

    String[] splitString;
    private ActListModel actListModel;
    private ActTypeListModel actTypeListModel;
    private ActSchedule2Model actScheduleListModel;
    public ArrayList<BarDataValue> valueList;
    public Map<ActListModel.Value, ArrayList<BarDataValue>> mapValue;
    public ArrayList<Long> mListF = new ArrayList<>();
    public ArrayList<Integer> colorList = new ArrayList<>();


    public static ActSchedule2Fragment newInstance() {
        instance = new ActSchedule2Fragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_act_schedule, container, false);
        ButterKnife.bind(this, view);
        Gson gson = new Gson();

        actTypeListModel = gson.fromJson(Communicator.db.getString(Constants.RESPONSE_GSON_ACT_TYPE_LIST), ActTypeListModel.class);
        actListModel = gson.fromJson(Communicator.db.getString(Constants.RESPONSE_GSON_ACT_LIST), ActListModel.class);
        actScheduleListModel = gson.fromJson(Communicator.db.getString(Constants.RESPONSE_GSON_ACT_SCHEDULE_LIST), ActSchedule2Model.class);
        refernceTime = getStartTime();
        getChartDataFromList();
        popUpChart();
        return view;
    }

    public void popUpChart() {
        BarData data = new BarData(getDataSet());
        mChart.setData(data);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
//        mChart.getLegend().setEnabled(false);
        mChart.getLegend().setWordWrapEnabled(true);
        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value < actTypeListModel.valueList.size())
                    return actTypeListModel.valueList.get((int) Double.parseDouble(String.valueOf(value))).description;
                else
                    return "";
            }
        });
        xl.setGranularity(1);
//        mChart.groupBars(refernceTime, groupSpace, barSpace); // perform the "explicit" grouping

        YAxis yl = mChart.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);

        YAxis yr = mChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);
        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(refernceTime);
        yr.setValueFormatter(xAxisFormatter);
        yl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
//
        });
        mChart.animateXY(2000, 2000);
        mChart.zoomToCenter(100, 0);
        mChart.invalidate();

    }

    public void getChartDataFromList() {
        mapValue = new HashMap<>();
        for (int i = 0; i < actListModel.valueList.size(); i++) {
            valueList = new ArrayList<>();
            int activityId = Math.abs(actListModel.valueList.get(i).id);
            for (int j = 0; j < actScheduleListModel.valueList.size(); j++) {
                if (activityId == Math.abs(actScheduleListModel.valueList.get(j).activityId)) {

                    valueList.add(new BarDataValue(actScheduleListModel.valueList.get(j).id, actListModel.valueList.get(i).description, getTimeStamp(actScheduleListModel.valueList.get(j).startDate, true), getTimeStamp(actScheduleListModel.valueList.get(j).startDate, true), true));
                }
            }
            Log.e("test", " values list size==>> " + valueList.size());
            mapValue.put(actListModel.valueList.get(i), valueList);
        }
    }

    public long getStartTime() {
        try {
            SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
            Date d = f.parse("00:00:00");
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    private long getTimeStamp(String startDate, boolean checkforRefernce) {
        String[] split = startDate.split("T");
        String str = split[1];
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'Z') {
            str = str.substring(0, str.length() - 1);
        }

        long milliseconds;
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        try {
            Date d = f.parse(str);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return 0;
        }

//        if (!checkforRefernce)
//            return milliseconds;
//        else {
//            if (refernceTime == 0) {
//                refernceTime = milliseconds;
//            } else {
//                if (refernceTime > milliseconds)
//                    refernceTime = milliseconds;
//            }
        return milliseconds;
//        }
    }

    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        mChart.getAxisRight().setStartAtZero(true);

        ArrayList yValues = new ArrayList();


//        for (int i = 0; i < mapValue.size(); i++) {

//            if (mapValue.get(i).size() > 0) {
        for (int j = 0; j < actTypeListModel.valueList.size(); j++) {
            float[] list = getStackBarValue(actTypeListModel.valueList.get(j));
            if (list.length > 0)
                yValues.add(new BarEntry(j, list));


            Log.e("test", "Size ====>> " + list.length);

        }
        Legend l = mChart.getLegend();

//        l.setCustom(colorArray, labelArray);


        BarDataSet barDataSet1 = new BarDataSet(yValues, "Planned");
        BarDataSet barDataSet2 = new BarDataSet(yValues, "Planned");
        barDataSet1.setColors(getColor());
        ArrayList<String> xValuesList = getXAxisValues();
        ColorTemplate.setLabelList(xValuesList);
        String[] label = new String[xValuesList.size()];
        label = xValuesList.toArray(label);
        barDataSet1.setStackLabels(label);
        barDataSet1.setDrawValues(false);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet1);
        return dataSets;
    }

    private float[] getStackBarValue(ActTypeListModel.Value model) {

        ArrayList<Float> list = new ArrayList<>();

        for (Map.Entry<ActListModel.Value, ArrayList<BarDataValue>> entry : mapValue.entrySet()) {
            if (Math.abs(model.id) == Math.abs(entry.getKey().activityTypeId)) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    list.add(Float.parseFloat(String.valueOf(entry.getValue().get(i).startTime)));
                    list.add(Float.parseFloat(String.valueOf(entry.getValue().get(i).endTime)));
                    addColorBar(entry.getKey().id);
                }

            }
        }

        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private void addColorBar(int id) {
        for (int i = 0; i < actListModel.valueList.size(); i++) {
            if (Math.abs(id) == Math.abs(actListModel.valueList.get(i).id)) {
                colorList.add(Color.rgb(255, 255, 255));
                colorList.add(ColorTemplate.CUSTOM_COLORS[i]);
            }
        }


    }

    private int[] getColor() {
        int[] color = new int[colorList.size()];
//        for (int i = 0; i < actListModel.valueList.size(); i++) {
//            if (i % 2 == 0) {
//                color[i] = Color.rgb(255, 255, 255);
//
//            } else {
//                color[i] = Color.rgb(0, 0, 0);
//
//            }
//        }
        for (int i = 0; i < colorList.size(); i++) {
            color[i] = colorList.get(i);
        }
//        color = colorList.toArray(color);

        return color;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < actListModel.valueList.size(); i++) {
            xAxis.add(actListModel.valueList.get(i).description);

        }
        return xAxis;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
