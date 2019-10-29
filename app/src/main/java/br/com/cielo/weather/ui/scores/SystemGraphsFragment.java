package br.com.cielo.weather.ui.scores;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import br.com.cielo.weather.R;
import br.com.cielo.weather.appManagement.WeatherFragment;
import br.com.cielo.weather.model.mongo.ScoreHistory;
import br.com.cielo.weather.model.mongo.ScoreHistoryUnit;
import br.com.cielo.weather.model.mongo.ScoreResults;
import br.com.cielo.weather.ui.customs.BarChartMarkerView;
import br.com.cielo.weather.ui.customs.BarValueFormatter;
import br.com.cielo.weather.ui.customs.LineChartMarkerView;
import br.com.cielo.weather.ui.customs.LineValueFormatter;
import br.com.cielo.weather.util.ColorTemplate;
import br.com.cielo.weather.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class SystemGraphsFragment extends WeatherFragment implements OnChartGestureListener, OnChartValueSelectedListener {

    @BindView(R.id.halfpie_chart)
    PieChart halfPie;

    @BindView(R.id.line_chart)
    LineChart lineChart;

    @BindView(R.id.criticity_issues_barchart)
    BarChart criticityIssuesBarChart;

    @BindView(R.id.type_issues_barchart)
    HorizontalBarChart typeIssuesHorizBarChart;

    @BindView(R.id.nloc_textview_value)
    TextView nlocTv;

    @BindView(R.id.total_issues_textview_value)
    TextView totalIssuesTv;

    @BindView(R.id.open_issues_textview_value)
    TextView openIssuesTv;

    @BindView(R.id.coverage_textview_value)
    TextView coverageTv;

    @BindView(R.id.duplications_textview_value)
    TextView duplicationsTv;

    @BindView(R.id.jira_alm_functional_tests_cases_tv)
    TextView testCasesTv;

    @BindView(R.id.jira_alm_functional_tests_total_executions_tv)
    TextView totalExecutionsTv;

    @BindView(R.id.jira_alm_functional_tests_ok_executions_tv)
    TextView okExecutionsTv;

    @BindView(R.id.jira_alm_functional_tests_success_rate_tv)
    TextView successRateTv;

    @BindView(R.id.jira_alm_functional_tests_defects_density_tv)
    TextView defectsDensityTv;

    @BindView(R.id.jira_alm_defects_opened)
    TextView openDefectsTv;

    @BindView(R.id.jira_alm_defects_average_aging)
    TextView averageAgingTv;

    @BindView(R.id.jira_alm_defects_total)
    TextView totalDefectsTv;

    @BindView(R.id.jira_alm_defects_average_fix_time)
    TextView averageFixTimeTv;

    @BindView(R.id.sdm_operational_efficiency_problems)
    TextView problemsTv;

    @BindView(R.id.sdm_operational_efficiency)
    TextView efficiencyTv;

    private ArrayList<BarEntry> barValues;
    private ArrayList<Entry> values;
    private ScoreResults[] apps;
    private ScoreResults actualApp;
    private ArrayList<BarEntry> horizontalBarValues;
    private String systemName;
    private ScoreHistory sh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.system_graphs_fragment, container, false);
        ButterKnife.bind(this, view);
        getBundles();
        setCodeInspectionNumbers();
        setupCharts();
        setFunctionalTestsNumbers();
        setDefectsNumbers();
        setFunctionalEfficiencyNumbers();

        return view;
    }

    private void setFunctionalEfficiencyNumbers() {

        startCountAnimation(problemsTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getTotalProblems()))),
                validateUnit(actualApp.getTotalProblems()),
                false);

        startCountAnimation(efficiencyTv,
                1000,
                actualApp.getEffectiveness(),
                "%",
                false);

    }

    private void setDefectsNumbers() {

        startCountAnimation(openDefectsTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getOpenDefects()))),
                validateUnit(actualApp.getOpenDefects()),
                false);

        startCountAnimation(averageAgingTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getAverageAge()))),
                validateUnit(actualApp.getAverageAge()),
                false);

        startCountAnimation(totalDefectsTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getTotalDefects()))),
                validateUnit(actualApp.getTotalDefects()),
                false);

        startCountAnimation(averageFixTimeTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getAverageTimeFix()))),
                validateUnit(actualApp.getAverageTimeFix()),
                false);

    }

    private void setFunctionalTestsNumbers() {

        startCountAnimation(testCasesTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getTestsCase().getNumberLong()))),
                validateUnit(actualApp.getTestsCase().getNumberLong()),
                false);

        startCountAnimation(totalExecutionsTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getTotalTestExecutions()))),
                validateUnit(actualApp.getTotalTestExecutions()),
                false);

        startCountAnimation(okExecutionsTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getExecutionsPassed()))),
                validateUnit(actualApp.getExecutionsPassed()),
                false);

        startCountAnimation(successRateTv,
                1000,
                actualApp.getSuccessRate(),
                "%",
                false);

        startCountAnimation(defectsDensityTv,
                1000,
                actualApp.getDefectsDensity(),
                "%",
                false);
    }

    private void setupCharts() {
        setBackgroudChartColors(Color.TRANSPARENT);
        setUsePercentValues();
        setDrawHole(true);
        setRotation();
        configureLineChart();
        configureBarChart(criticityIssuesBarChart);
        configureBarChart(typeIssuesHorizBarChart);
        setData(halfPie, "SCORE"); //Pie, 2 values, 5 max
        setData(lineChart, null); // Line, 10 entries, 5 max
        setData(criticityIssuesBarChart, null); // Bar, 3 entries, 20 max
        setData(typeIssuesHorizBarChart, null); // Horizontal Bar, 3 entries, 20 max
        setLegends();
        animateCharts();
    }

    @SuppressWarnings("unchecked")
    private void getBundles() {

        systemName = (String) getArguments().get(Constants.SharedPreferences.SYSTEM_NAME);
        sh = new ScoreHistory();

        switch (systemName) {
            case Constants.Systems.APP_CIELO:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.APP_HISTORY, Constants.SharedPreferences.APP_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.CENTRALIZADOR_ACC_RANGE:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.CENTRALIZADOR_HISTORY, Constants.SharedPreferences.CENTRALIZADOR_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.CIELO_PAY_PTO_CORE:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.CIELO_PAY_PTO_CORE_HISTORY, Constants.SharedPreferences.CIELO_PAY_PTO_CORE_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.CREDENCIAMENTO:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.CREDENCIAMENTO_HISTORY, Constants.SharedPreferences.CREDENCIAMENTO_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.FAC:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.FAC_HISTORY, Constants.SharedPreferences.FAC_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.FAROL:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.FAROL_HISTORY, Constants.SharedPreferences.FAROL_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.PFI:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.PFI_HISTORY, Constants.SharedPreferences.PFI_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.RECEBA_MAIS:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.RECEBA_MAIS_HISTORY, Constants.SharedPreferences.RECEBA_MAIS_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.SIGO:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.SIGO_HISTORY, Constants.SharedPreferences.SIGO_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.SITE:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.SITE_HISTORY, Constants.SharedPreferences.SITE_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.SKYLINE:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.SKYLINE_HISTORY, Constants.SharedPreferences.SKYLINE_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.STAR:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.STAR_HISTORY, Constants.SharedPreferences.STAR_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.STC:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.STC_HISTORY, Constants.SharedPreferences.STC_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.VSC:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.VSC_HISTORY, Constants.SharedPreferences.VSC_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.HYAKU:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.HYAKU_HISTORY, Constants.SharedPreferences.HYAKU_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.CANCELAMENTO:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.CAN_HISTORY, Constants.SharedPreferences.CAN_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.NGC:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.NGC_HISTORY, Constants.SharedPreferences.NGC_HISTORY), ScoreHistory.class);

                break;
            case Constants.Systems.POS:

                sh = new Gson().fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.POS_HISTORY, Constants.SharedPreferences.POS_HISTORY), ScoreHistory.class);

                break;
        }

        apps = new Gson()
                .fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.LAST_OK_APPLICATIONS, Constants.SharedPreferences.LAST_OK_APPLICATIONS), ScoreResults[].class);

        for (ScoreResults sr : apps) {
            if(sr.getApplicationName().equals(systemName)){
                actualApp = sr;
                break;
            }
        }

    }

    private void setCodeInspectionNumbers() {

        startCountAnimation(nlocTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getNcloc()))),
                validateUnit(actualApp.getNcloc()),
                false);

        startCountAnimation(coverageTv,
                1000,
                actualApp.getCoverage(),
                "%",
                false);

        startCountAnimation(duplicationsTv,
                1000,
                actualApp.getDuplicationDensity(),
                "%",
                false);

        startCountAnimation(openIssuesTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getOpenIssues()))),
                validateUnit(actualApp.getOpenIssues()),
                false);

        startCountAnimation(totalIssuesTv,
                1000,
                Float.valueOf(removeLastChar(compressNumbers(actualApp.getTotalIssues()))),
                validateUnit(actualApp.getTotalIssues()),
                false);
    }

    private void configureBarChart(Chart<BarData> c) {

        if (c instanceof HorizontalBarChart){

            typeIssuesHorizBarChart.setOnChartValueSelectedListener(this);
            typeIssuesHorizBarChart.setDrawBarShadow(false);
            typeIssuesHorizBarChart.setDrawValueAboveBar(true);
            typeIssuesHorizBarChart.getDescription().setEnabled(false);
            typeIssuesHorizBarChart.setMaxVisibleValueCount(60);
            typeIssuesHorizBarChart.setPinchZoom(false);
            typeIssuesHorizBarChart.setDrawGridBackground(false);

            XAxis xAxis = typeIssuesHorizBarChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(7);

            YAxis leftAxis = typeIssuesHorizBarChart.getAxisLeft();
            leftAxis.setLabelCount(8, false);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setAxisMinimum(0f);

            YAxis rightAxis = typeIssuesHorizBarChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setLabelCount(8, false);
            rightAxis.setSpaceTop(15f);
            rightAxis.setAxisMinimum(0f);

            Legend l = typeIssuesHorizBarChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setDrawInside(true);
            l.setYOffset(0f);
            l.setXOffset(0f);
            l.setYEntrySpace(0f);
            l.setTextSize(12f);

        } else if (c instanceof BarChart){

            criticityIssuesBarChart.setOnChartValueSelectedListener(this);
            criticityIssuesBarChart.setDrawBarShadow(false);
            criticityIssuesBarChart.setDrawValueAboveBar(true);
            criticityIssuesBarChart.getDescription().setEnabled(false);
            criticityIssuesBarChart.setMaxVisibleValueCount(60);
            criticityIssuesBarChart.setPinchZoom(false);
            criticityIssuesBarChart.setDrawGridBackground(false);

            XAxis xAxis = criticityIssuesBarChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(7);

            YAxis leftAxis = criticityIssuesBarChart.getAxisLeft();
            leftAxis.setLabelCount(8, false);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setAxisMinimum(0f);

            YAxis rightAxis = criticityIssuesBarChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setLabelCount(8, false);
            rightAxis.setSpaceTop(15f);
            rightAxis.setAxisMinimum(0f);

            Legend l = criticityIssuesBarChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setDrawInside(true);
            l.setYOffset(0f);
            l.setXOffset(0f);
            l.setYEntrySpace(0f);
            l.setTextSize(12f);
        }

    }

    private void configureLineChart() {

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(10f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGridLineWidth(1f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMaximum(6f);
        leftAxis.setAxisMinimum(-1);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setGridColor(R.color.black_cielo);
        leftAxis.setGridLineWidth(1f);

        lineChart.getAxisRight().setEnabled(false);

        LineChartMarkerView mv = new LineChartMarkerView(getContext(), R.layout.custom_marker_view);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
    }

    private void setLegends() {
        halfPie.getLegend().setEnabled(false);
        halfPie.setDescription(null);

        Description d = new Description();
        d.setText("Histórico do Sistema");
        d.setTextSize(15f);
        lineChart.setDescription(d);

        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
    }

    private void animateCharts() {
        halfPie.animateY(1400);
        lineChart.animateX(1800);
        criticityIssuesBarChart.animateX(2000);
        typeIssuesHorizBarChart.animateX(3000);
    }

    private void setData(Chart chart, String label) {

        if(chart instanceof LineChart){
            values = new ArrayList<>();

            int sysCount = 0;
            for (ScoreHistoryUnit shu : sh.getScoreHistoryList()) {
                values.add(new Entry(sysCount, shu.getScore(), shu.getDate()));
                sysCount++;
            }

            finishLineGraph();

        } else if (chart instanceof HorizontalBarChart){

            horizontalBarValues = new ArrayList<>();

            horizontalBarValues.add(new BarEntry(0, actualApp.getIssuesCodeSmell(), "Code Smells"));
            horizontalBarValues.add(new BarEntry(1, actualApp.getIssuesVulnerabilities(), "Vulnerabilities"));
            horizontalBarValues.add(new BarEntry(2, actualApp.getIssuesBug(), "Bugs"));

            BarChartMarkerView mv = new BarChartMarkerView(getContext(), R.layout.custom_marker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);

            finishBarGraph(chart);

        } else if (chart instanceof PieChart){

            ArrayList<PieEntry> pieValues = new ArrayList<>();

            pieValues.add(new PieEntry(actualApp.getScore()));
            pieValues.add(new PieEntry(5-actualApp.getScore()));

            PieDataSet dataSet = new PieDataSet(pieValues, "Election Results");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            dataSet.setColors(ColorTemplate.CIELO_PASSED_FAILED_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueTextSize(17f);
            data.setValueTextColor(Color.WHITE);
            halfPie.setData(data);

            DecimalFormat df = new DecimalFormat("#.##");
            halfPie.setCenterText(df.format(actualApp.getScore())+"\n"+label);

            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/museosans_700.otf");
            halfPie.setCenterTextTypeface(font);
            halfPie.setCenterTextColor(Color.BLACK);

            if(label.toLowerCase().contains("score")){
                halfPie.setCenterTextSize(25f);
            } else {
                halfPie.setCenterTextSize(12f);
            }

            halfPie.invalidate();
        } else if (chart instanceof BarChart){
            barValues = new ArrayList<>();

            barValues.add(new BarEntry(0, actualApp.getIssuesBlocker(), "Blockers"));
            barValues.add(new BarEntry(1, actualApp.getIssuesCritical(), "Criticals"));
            barValues.add(new BarEntry(2, actualApp.getIssuesMajor(), "Majors"));

            BarChartMarkerView mv = new BarChartMarkerView(getContext(), R.layout.custom_marker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);

            finishBarGraph(chart);
        }
    }

    private void finishBarGraph(Chart c) {

        BarDataSet set1 = null;
        BarDataSet set2 = null;
        BarDataSet set3 = null;
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<BarEntry> entries3 = new ArrayList<>();

        if(c instanceof HorizontalBarChart){
            entries1.add(horizontalBarValues.get(0));
            set1 = new BarDataSet(entries1, "Code Smell");
            entries2.add(horizontalBarValues.get(1));
            set2 = new BarDataSet(entries2, "Vulnerabilities");
            entries3.add(horizontalBarValues.get(2));
            set3 = new BarDataSet(entries3, "Bugs");

        } else if(c instanceof BarChart) {
            entries1.add(barValues.get(0));
            set1 = new BarDataSet(entries1, "Blockers");
            entries2.add(barValues.get(1));
            set2 = new BarDataSet(entries2, "Criticals");
            entries3.add(barValues.get(2));
            set3 = new BarDataSet(entries3, "Majors");
        }

        set1.setValueFormatter(new BarValueFormatter());
        set2.setValueFormatter(new BarValueFormatter());
        set3.setValueFormatter(new BarValueFormatter());
        set1.setColor(ColorTemplate.CIELO_GRAY);
        set2.setColor(ColorTemplate.CIELO_BLACK);
        set3.setColor(ColorTemplate.CIELO_BLUE);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        c.getXAxis().setDrawLabels(false);

        if(c instanceof HorizontalBarChart){
            typeIssuesHorizBarChart.getAxisRight().setDrawAxisLine(true);
            typeIssuesHorizBarChart.getAxisRight().setDrawLabels(false);
            typeIssuesHorizBarChart.setData(data);
            typeIssuesHorizBarChart.invalidate();
        } else if (c instanceof BarChart){
            criticityIssuesBarChart.setData(data);
            criticityIssuesBarChart.invalidate();
        }
    }

    private void finishLineGraph() {
        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "------------");
            set1.setValueFormatter(new LineValueFormatter());

            // set the line to be drawn like this "- - - - - -"
            set1.setColor(Color.parseColor("#00adef"));
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1.5f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.background_gradient_white_and_cielo_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            lineChart.setData(data);
        }

    }

    private void setRotation() {
        halfPie.setMaxAngle(180f); // HALF CHART
        halfPie.setRotationAngle(180f);
        halfPie.setCenterTextOffset(0, -20);
        halfPie.setRotationEnabled(false);
        halfPie.setHighlightPerTapEnabled(true);
        halfPie.setMaxHighlightDistance(5F);

        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
    }

    private void setDrawHole(boolean b) {
        halfPie.setDrawHoleEnabled(true);
        halfPie.setHoleColor(Color.TRANSPARENT);
        halfPie.setHoleRadius(58f);
        halfPie.setTransparentCircleRadius(0);

    }

    private void setUsePercentValues() {
        halfPie.setUsePercentValues(false);
    }

    private void setBackgroudChartColors(int transparent) {
        halfPie.setBackgroundColor(transparent);
    }

    @Override
    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
    }

    @Override
    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {
    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {
    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {
    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
    }

    @Override
    public void onChartScale(MotionEvent motionEvent, float v, float v1) {
    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {
    }

    @Override
    public void onNothingSelected() {
    }
}
