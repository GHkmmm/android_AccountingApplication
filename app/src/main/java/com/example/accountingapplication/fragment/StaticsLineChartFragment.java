package com.example.accountingapplication.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.example.accountingapplication.utils.DateUtil;
import com.example.accountingapplication.utils.LineChartMarkView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StaticsLineChartFragment extends Fragment {
    private List<Account> accountList;
    private List<Account> expensesAccounts;
    private List<Account> incomeAccounts;
    private List<Account> expensesAccountsByDate;
    private List<Account> incomeAccountsByDate;
    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;
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
        lineChart = view.findViewById(R.id.lineChart);
        initChart(lineChart);
//        LineChartBean lineChartBean = LocalJsonAnalyzeUtil.JsonToObject(this,
//                "chart.json", LineChartBean.class);
//        List<IncomeBean> list = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();
        dividedAccount();
        showLineChart(expensesAccountsByDate, "收入", getResources().getColor(R.color.blue));
        addLine(incomeAccountsByDate, "支出", getResources().getColor(R.color.orange));
        Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
        setChartFillDrawable(drawable);
        setMarkerView();
        return view;
    }

    private void dividedAccount(){
        incomeAccounts = new ArrayList<>();
        expensesAccounts = new ArrayList<>();
        expensesAccountsByDate = new ArrayList<>();
        incomeAccountsByDate = new ArrayList<>();
        curTime = Calendar.getInstance().getTimeInMillis();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").toString();
        range = curTime-(86400000d*7d);

        for (int i=0;i<accountList.size();i++){
            if ("收入".equals(accountList.get(i).getCategory())){
                incomeAccounts.add(accountList.get(i));
            }else {
                expensesAccounts.add(accountList.get(i));
            }
        }
        for (double c=curTime-86400000d;c>=range;c=c-86400000d){
            Account account = new Account();
            int total = 0;
            Boolean isFind = false;
            for (int a=0;a<expensesAccounts.size();a++) {
                double time = new SimpleDateFormat("yyyy-MM-dd").parse(expensesAccounts.get(a).getDate(), new ParsePosition(0)).getTime();
                if (time>c && time<c+86400000d){
                    isFind = true;
                    total = total + Integer.parseInt(expensesAccounts.get(a).getSum());
                    account.setSum(total+"");
                } else if (!isFind){
                    account.setSum("0");
                }
                account.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date((long) c)));
            }
            expensesAccountsByDate.add(0,account);
        }
        for (double c=curTime-86400000d;c>=range;c=c-86400000d){
            Account account = new Account();
            int total = 0;
            Boolean isFind = false;
            for (int a=0;a<incomeAccounts.size();a++) {
                double time = new SimpleDateFormat("yyyy-MM-dd").parse(incomeAccounts.get(a).getDate(), new ParsePosition(0)).getTime();
                if (time>c && time<c+86400000d){
                    isFind = true;
                    total = total + Integer.parseInt(incomeAccounts.get(a).getSum());
                    account.setSum(total+"");
                } else if (!isFind){
                    account.setSum("0");
                }
                account.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date((long) c)));
            }
            incomeAccountsByDate.add(0,account);
        }
    }

    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置XY轴动画效果
        lineChart.animateY(1000);
        lineChart.animateX(600);
        lineChart.setBackgroundColor(Color.WHITE);

        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);
        //显示了网格线，而且不是我们想要的 虚线 。其实那是 X Y轴自己的网格线，禁掉即可
        xAxis.setDrawGridLines(false);
        rightYaxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(true);
        //设置X Y轴网格线为虚线（实体线长度、间隔距离、偏移量：通常使用 0）
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);

        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        lineDataSet.setDrawCircles(false);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(final List<Account> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Account data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, Float.parseFloat(data.getSum()));
            entries.add(entry);
        }
        xAxis.setGranularity(1);
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            LineDataSet lineDataSet2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineDataSet2.setDrawFilled(true);
            lineDataSet2.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView() {
        LineChartMarkView mv = new LineChartMarkView(getContext(), xAxis.getValueFormatter());
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    private void addLine(List<Account> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Account data = dataList.get(i);
            Entry entry = new Entry(i, Float.parseFloat(data.getSum()));
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        lineChart.getLineData().addDataSet(lineDataSet);
        lineChart.invalidate();
    }
}
