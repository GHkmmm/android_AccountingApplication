package com.example.accountingapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.accountingapplication.R;
import com.example.accountingapplication.entity.Account;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StaticsFragment extends Fragment {
    private List<PieEntry> entries;
    private PieChart pieChart;
    private List<Account> accounts;
    private TextView Today, LastWeek, LastMonth;
    private int trafficVal, eatVal, clothesVal, shoppingVal,otherVal;
    private double range;
    private double curTime;

    public StaticsFragment(List<Account> accounts) {
        Collections.sort(accounts);
        this.accounts = accounts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statics, container, false);

        initData();
        initView(view);
        initPieChart();
        Today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                range = curTime - 86400000;
                initEntries();
                initPieChart();
            }
        });
        LastWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                range = curTime-86400000*7;
                initEntries();
                initPieChart();
            }
        });
        LastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                range = curTime-(86400000d*30d);
                initEntries();
                initPieChart();
            }
        });
        return view;
    }

    private void initView(View view) {
        pieChart = view.findViewById(R.id.chart);
        Today = view.findViewById(R.id.today);
        LastWeek = view.findViewById(R.id.lastweek);
        LastMonth = view.findViewById(R.id.lastmonth);
    }

    public void initData(){
        curTime = Calendar.getInstance().getTimeInMillis();
        range = curTime-(86400000d);

        initEntries();
    }

    public void initEntries(){
        entries = new ArrayList<>();
        trafficVal = 0;
        eatVal = 0;
        clothesVal = 0;
        shoppingVal = 0;
        otherVal = 0;
        for (int i=0;i<accounts.size();i++){
            if("收入".equals(accounts.get(i).getCategory())){

            }else {
                double time = new SimpleDateFormat("yyyy-MM-dd").parse(accounts.get(i).getDate(), new ParsePosition(0)).getTime();
                if (time > range){
                    switch (accounts.get(i).getType()){
                        case "交通":
                            trafficVal = trafficVal+Integer.parseInt(accounts.get(i).getSum());
                            break;
                        case "餐饮":
                            eatVal = eatVal+Integer.parseInt(accounts.get(i).getSum());
                            break;
                        case "衣服":
                            clothesVal = clothesVal+Integer.parseInt(accounts.get(i).getSum());
                            break;
                        case "购物":
                            shoppingVal = shoppingVal+Integer.parseInt(accounts.get(i).getSum());
                            break;
                        default:
                            otherVal = otherVal+Integer.parseInt(accounts.get(i).getSum());
                    }
                }
            }
        }
        entries.clear();//清除数据
        //添加数据
        if (trafficVal!=0){
            entries.add(new PieEntry(trafficVal, "交通"));
        }
        if (eatVal!=0){
            entries.add(new PieEntry(eatVal, "餐饮"));
        }
        if (clothesVal!=0){
            entries.add(new PieEntry(clothesVal, "衣服"));
        }
        if (clothesVal!=0){
            entries.add(new PieEntry(shoppingVal, "购物"));
        }
        if (clothesVal!=0){
            entries.add(new PieEntry(otherVal, "其他"));
        }
    }

    public void initPieChart(){
        pieChart.setUsePercentValues(true); //设置是否显示数据实体(百分比，true:以下属性才有意义)
        pieChart.getDescription().setEnabled(false);//设置pieChart图表的描述
        pieChart.setExtraOffsets(5, 5, 5, 5);//饼形图上下左右边距

        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]

//        pieChart.setCenterTextTypeface(mTfLight);//设置所有DataSet内数据实体（百分比）的文本字体样式
        pieChart.setCenterText("饼状图");//设置PieChart内部圆文字的内容

        pieChart.setDrawHoleEnabled(true);//是否显示PieChart内部圆环(true:下面属性才有意义)
        pieChart.setHoleColor(Color.WHITE);//设置PieChart内部圆的颜色

        pieChart.setTransparentCircleColor(Color.WHITE);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
        pieChart.setTransparentCircleAlpha(0);//设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
        pieChart.setHoleRadius(0f);//设置PieChart内部圆的半径(这里设置0f,即不要内部圆)
        pieChart.setTransparentCircleRadius(31f);//设置PieChart内部透明圆的半径(这里设置31.0f)

        pieChart.setDrawCenterText(true);//是否绘制PieChart内部中心文本（true：下面属性才有意义）

        pieChart.setRotationAngle(270);//设置pieChart图表起始角度

        pieChart.setRotationEnabled(false);//设置pieChart图表是否可以手动旋转
        pieChart.setHighlightPerTapEnabled(true);//设置piecahrt图表点击Item高亮是否可用

        pieChart.animateY(600, Easing.EaseInOutQuad);// 设置pieChart图表展示动画效果
//         pieChart.spin(2000, 0, 360);//旋转

        // 获取pieCahrt图列(图列的位置、是水平还是垂直显示)
        Legend l = pieChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);//线性
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//上边
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//右边（跟着TOP既是右上角，根据自己需求设置左上角、左下角……）
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f); //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        l.setYEntrySpace(0f); //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        l.setYOffset(0f);//设置比例块Y轴偏移量


        pieChart.setEntryLabelColor(Color.BLACK);//设置pieChart图表文本字体颜色
//        pieChart.setEntryLabelTypeface(mTfRegular);//设置pieChart图表文本字体样式
        pieChart.setEntryLabelTextSize(12f);//设置pieChart图表文本字体大小

        PieDataSet dataSet = new PieDataSet(entries, "数据说明");//右上角，依次排列

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(0f);//设置饼状Item之间的间隙
        dataSet.setIconsOffset(new MPPointF(0, 40));

        dataSet.setSelectionShift(5f);//设置饼状Item被选中时变化的距离(为0f时，选中的不会弹起来)

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
//            colors.add(c);
//        }

//        for (int c : ColorTemplate.JOYFUL_COLORS) {
//            colors.add(c);
//        }

        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }

//        for (int c : ColorTemplate.PASTEL_COLORS) {
//            colors.add(c);
//        }

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);//设置饼图里面的百分比（eg: 20.8%）
        data.setDrawValues(true);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
        data.setValueTextColor(Color.BLACK);  //设置所有DataSet内数据实体（百分比）的文本颜色
        data.setValueTextSize(11f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
//        data.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
        data.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式

        pieChart.setData(data);// //为图表添加 数据

        pieChart.highlightValues(null);//设置高亮显示
        pieChart.setDrawEntryLabels(true);// 设置pieChart是否只显示饼图上百分比不显示文字
        pieChart.invalidate();//将图表重绘以显示设置的属性和数据

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {//点击事件
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // e.getX()方法得到x数据
                PieEntry pieEntry = (PieEntry) e;
                Toast.makeText(getActivity(),"当前选中"+pieEntry.getLabel(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
}
