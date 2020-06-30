package com.example.accountingapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.accountingapplication.R;
import com.example.accountingapplication.entity.Account;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class StaticsLineChartFragment extends Fragment {
    private LineChart lineChart;
    private LineDataSet dataSet;
    private List<Entry> entries;
    private List<Account> accountList;
    private List<Account> expensesAccounts;
    private List<Account> incomeAccounts;
    private double range;
    private double curTime;

    public StaticsLineChartFragment(List<Account> accountList) {
        Collections.sort(accountList);
        this.accountList = accountList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statics_linechart, container, false);
        initView(view);
        initData();
        showChart();
        return view;
    }

    public void initView(View view){
        lineChart = view.findViewById(R.id.lineChart);
    }

    public void initData() {
        entries = new ArrayList<>();
        incomeAccounts = new ArrayList<>();
        expensesAccounts = new ArrayList<>();
        curTime = Calendar.getInstance().getTimeInMillis();
        range = curTime-(86400000d*7d);
        int total7 = 0;
        int total6 = 0;
        int total5 = 0;
        int total4 = 0;
        int total3 = 0;
        int total2 = 0;
        int total1 = 0;
        Entry entry7 = new Entry(7,0);
        Entry entry6 = new Entry(6,0);
        Entry entry5 = new Entry(5,0);
        Entry entry4 = new Entry(4,0);
        Entry entry3 = new Entry(3,0);
        Entry entry2 = new Entry(2,0);
        Entry entry1 = new Entry(1,0);
        entries.clear();
        for (int i=0;i<accountList.size();i++){
            if ("收入".equals(accountList.get(i).getCategory())){
                incomeAccounts.add(accountList.get(i));
            }else {
                expensesAccounts.add(accountList.get(i));
            }
        }
        for (int i = 0; i < expensesAccounts.size(); i++) {
            double time = new SimpleDateFormat("yyyy-MM-dd").parse(expensesAccounts.get(i).getDate(), new ParsePosition(0)).getTime();
            if (time > range) {
                if(time > curTime - 86400000d){
                    total7 = total7 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry7 = new Entry(7, total7);
                } else if (time > curTime - 86400000d*2d){
                    total6 = total6 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry6 = new Entry(6, total6);
                }else if (time > curTime - 86400000d*3d){
                    total5 = total5 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry5 = new Entry(5, total5);
                } else if (time > curTime - 86400000d*4d){
                    total4 = total4 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry4 = new Entry(4, total4);
                } else if (time > curTime - 86400000d*5d){
                    total3 = total3 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry3 = new Entry(3, total3);
                } else if (time > curTime - 86400000d*6d){
                    total2 = total2 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry2 = new Entry(2, total2);
                }else if (time > curTime - 86400000d*7d){
                    total1 = total1 + Integer.parseInt(expensesAccounts.get(i).getSum());
                    entry1 = new Entry(1, total1);
                }

//                System.out.println(i + "size======" + expensesAccounts.size());
//                total = total + Integer.parseInt(expensesAccounts.get(i - 1).getSum());
//                if ((expensesAccounts.get(i).getDate()).equals(expensesAccounts.get(i - 1).getDate())) {
////                    total = total + Integer.parseInt(expensesAccounts.get(i).getSum());
//                } else {
//                    System.out.println("total======" + total);
//                    entries.add(new Entry(i, total));
//                    total = 0;
//                }
            }
        }
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);
        entries.add(entry5);
        entries.add(entry6);
        entries.add(entry7);
    }

    public void showChart() {
        lineChart.setDrawBorders(false);
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        LineData lineData = new LineData(lineDataSet);
        //线颜色
        lineDataSet.setColor(Color.parseColor("#F15A4A"));
        //线宽度
        lineDataSet.setLineWidth(1.6f);
        //不显示圆点
        lineDataSet.setDrawCircles(false);
        //线条平滑
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置折线图填充
//        lineDataSet.setDrawFilled(true);
        LineData data = new LineData(lineDataSet);
        //无数据时显示的文字
        lineChart.setNoDataText("暂无数据");
        //折线图不显示数值
        data.setDrawValues(false);
        //得到X轴
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴坐标之间的最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(7, false);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) 7);
        //不显示网格线
        xAxis.setDrawGridLines(false);
        // 标签倾斜
        xAxis.setLabelRotationAngle(45);
        //设置X轴值为字符串
//        xAxis.setValueFormatter(new IAxisValueFormatter()
//        {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis)
//            {
//                int IValue = (int) value;
//                CharSequence format = DateFormat.format("MM/dd",
//                        System.currentTimeMillis()-(long)(list.size()-IValue)*24*60*60*1000);
//                return format.toString();
//            }
//        });
        //得到Y轴
        YAxis yAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);
        //设置y轴的刻度数量
        //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
//        yAxis.setLabelCount(Collections.max(list) + 2, false);
        yAxis.setLabelCount(500, false);
        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        //+1:y轴多一个单位长度，为了好看
//        yAxis.setAxisMaximum(Collections.max(list) + 1);

        //y轴
//        yAxis.setValueFormatter(new IAxisValueFormatter()
//        {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis)
//            {
//                int IValue = (int) value;
//                return String.valueOf(IValue);
//            }
//        });
        //图例：得到Lengend
        Legend legend = lineChart.getLegend();
        //隐藏Lengend
        legend.setEnabled(false);
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}
